/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.sequence;

import it.uniroma3.wcb.course.Corpus;
import it.uniroma3.wcb.course.graph.SequencingGraphService;
import it.uniroma3.wcb.course.ranking.InformationGainProcessor;
import it.uniroma3.wcb.course.ranking.LSIProcessor_Jblas;
import it.uniroma3.wcb.course.ranking.RankType;
import it.uniroma3.wcb.course.ranking.TeachingStyleComparator;
import it.uniroma3.wcb.course.ranking.TfIdfProcessor;
import it.uniroma3.wcb.course.scan.PageLinksDTO;
import it.uniroma3.wcb.course.scan.ScanService;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.model.UserTeachingStyle;
import it.uniroma3.wcb.persistence.model.Page;
import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.security.RequestUserService;
import it.uniroma3.wcb.spring.AppConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Sequence process base on hypothesis one. Starting from initial page, is
 * selected most important outgoing link, based on higher rank process
 * calculated score; then it will be used as new starting page (iterative
 * routine - until max depth scan configuration).
 * <p>
 * @author J-HaRd Andrea Giardi
 */
@Service("sequenceService")
public class TopNSequenceServiceImpl implements SequenceService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ExtraConfig configs = ExtraConfig.getIstance();

    @Autowired
    private AppConfig config;

    @Autowired
    private ScanService scanService;

    @Autowired
    private SequencingGraphService graphService;

    @Autowired
    private RequestUserService userDetailsService;

    @Autowired
    private TeachingStyleComparator tsComparator;

    /*
    @Autowired
    private CustomWiki wikipedia;
*/
    @Override
    public JSONObject generateSequence(String startingPageTitle, String searchConstraints, int depth, String language) throws Exception {

        try {
            //depth scan - recupero tutte le pagine tra loro linkate
            //PageLinksDTO pageLinksDTO = this.scanService.scanPageLinks(startingPageTitle, depth);
            PageLinksDTO pageLinksDTO = null;

            if (configs.getLevelGroupQueryBuild() > 1) {
                pageLinksDTO = this.scanService.scanPageLinksNoSqlLevel(startingPageTitle, depth, language);
            } else if (configs.getLevelGroupQueryBuild() > 0) {
                pageLinksDTO = this.scanService.scanPageLinksNoSql(startingPageTitle, depth, language);
            } else {
                pageLinksDTO = this.scanService.scanPageLinks(startingPageTitle, depth, language);
            }

            Map<String, PageLinksDTO> allPages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            getAllPages(pageLinksDTO, allPages);

            //recupero il teachingStyle dell'utente loggato
            UserTeachingStyle userTS = getUserTeachingStyle();
            String lng=language;
            if(configs.getStemmerType()==0&&language.equalsIgnoreCase("en")){
                lng="wikiporter";
            }
            else if(configs.getStemmerType()>1&&language.equalsIgnoreCase("en")){
                lng+="2";
            }
            //inizializzazione del corpus
            Corpus stemmedCorpus = new Corpus(lng,configs.getStopWords(language), configs.getSectionsToSkip(language), configs.getTextTypeRank());

            Map<String, Integer> reverseIndex = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            int docCounter = 0;

            for (Map.Entry<String, PageLinksDTO> retrievedPage : allPages.entrySet()) {
                String text;
                    if(this.configs.getTextTypeRank()==0||this.configs.getTextTypeRank()==2){
                    //String text = page.getOnlyText();
                        text = retrievedPage.getValue().getPage().getText();
                    }
                    else {
                        text = retrievedPage.getValue().getPage().getOnlyText();
                    }
                
                //stemmedCorpus.addDocumentToCollection(String.valueOf(docCounter), retrievedPage.getValue().getPage().getOnlyText());
                stemmedCorpus.addDocumentToCollection(String.valueOf(docCounter), text);
                reverseIndex.put(retrievedPage.getKey(), new Integer(docCounter));
                docCounter++;
            }

            //limiting corpus terms size
            stemmedCorpus.limitCorpusTerms(this.config.getLimitedCorpusTermsMaxSize(), this.config.getSkipTermsSmallerThanSize(), searchConstraints);

            //TF-IDF elaborationconfig
            TfIdfProcessor tfIdfProcessor = new TfIdfProcessor(stemmedCorpus, this.config.getDecimalPrecision(), true);
            //map containing document key/cosine similarity score
            Map<String, Double> TFIDF_Scores = tfIdfProcessor.scoreDocuments(searchConstraints);

            //LSI elaboration
            //LSIProcessor_Jama lsiProcessor = new LSIProcessor_Jama(stemmedCorpus, this.config.getLsiMaxMatrixReductionRatio(), this.configurations.getDecimalPrecision(), tfIdfProcessor);
            LSIProcessor_Jblas lsiProcessor = new LSIProcessor_Jblas(stemmedCorpus, this.config.getLsiMaxMatrixReductionRatio(), this.config.getDecimalPrecision(), tfIdfProcessor);
            //map containing document key/cosine similarity score
            Map<String, Double> LSI_Scores = lsiProcessor.scoreDocuments(searchConstraints);

            //Information Gain elaboration
            InformationGainProcessor igProcessor = new InformationGainProcessor(stemmedCorpus, this.config.getDecimalPrecision());
            Map<String, Double> IG_Scores = igProcessor.scoreDocuments(searchConstraints);

            for (Map.Entry<String, PageLinksDTO> retrievedPage : allPages.entrySet()) {
                retrievedPage.getValue().setRankingScore(RankType.TF_IDF, TFIDF_Scores.get(reverseIndex.get(retrievedPage.getKey()).toString()));
                retrievedPage.getValue().setRankingScore(RankType.LSI, LSI_Scores.get(reverseIndex.get(retrievedPage.getKey()).toString()));
                retrievedPage.getValue().setRankingScore(RankType.IG, IG_Scores.get(reverseIndex.get(retrievedPage.getKey()).toString()));

                //calcolo del teaching style score
                Double tsScore = tsComparator.getTSOrderingScore(userTS, retrievedPage.getValue().getPage().getTeachingStyle());
                retrievedPage.getValue().setRankingScore(RankType.TEACHING_STYLE, tsScore);
            }

            //la pagina iniziale è selezionata da tutti gli algoritmi perchè è quella di partenza
            pageLinksDTO.setSelectedFromSequenceAlgorithm(RankType.TEACHING_STYLE, true);
            pageLinksDTO.setSelectedFromSequenceAlgorithm(RankType.TF_IDF, true);
            pageLinksDTO.setSelectedFromSequenceAlgorithm(RankType.LSI, true);
            pageLinksDTO.setSelectedFromSequenceAlgorithm(RankType.IG, true);

            //calcolo di tutti i sequencing
            Set<PageLinksDTO> allPages1 = new HashSet<>();
            Set<PageLinksDTO> allPages2 = new HashSet<>();
            Set<PageLinksDTO> allPages3 = new HashSet<>();
            Set<PageLinksDTO> allPages4 = new HashSet<>();
            allPages1.addAll(allPages.values());
            allPages2.addAll(allPages.values());
            allPages3.addAll(allPages.values());
            allPages4.addAll(allPages.values());
            JSONArray tsSequencedItems = this.getSequencedItems(RankType.TEACHING_STYLE, pageLinksDTO, allPages1);
            JSONArray tfIdfSequencedItems = this.getSequencedItems(RankType.TF_IDF, pageLinksDTO, allPages2);
            JSONArray lsiSequencedItems = this.getSequencedItems(RankType.LSI, pageLinksDTO, allPages3);
            JSONArray igSequencedItems = this.getSequencedItems(RankType.IG, pageLinksDTO, allPages4);

            //add general - unione dei rank
            JSONArray generalSequencedItems = new JSONArray();
            List<String> verDupl = new ArrayList<>();

            //object.put("buildPosition", selectedPageDTO.getKey());
            int newBuild=0;
            
            for (int x = 0; x < tsSequencedItems.length(); x++) {
                JSONObject p = tsSequencedItems.getJSONObject(x);
                JSONObject page1=new JSONObject(p.toString());
                page1.put("buildPosition", newBuild);
                String title1 = page1.getString("title");
                if (!verDupl.contains(title1)) {
                    verDupl.add(title1);
                    generalSequencedItems.put(page1);
                    newBuild++;
                }

            }

            for (int x = 0; x < tfIdfSequencedItems.length(); x++) {
                JSONObject p = tfIdfSequencedItems.getJSONObject(x);
                JSONObject page1=new JSONObject(p.toString());
                page1.put("buildPosition", newBuild);
                String title1 = page1.getString("title");
                if (!verDupl.contains(title1)) {
                    verDupl.add(title1);
                    generalSequencedItems.put(page1);
                    newBuild++;
                }
            }
            for (int x = 0; x < lsiSequencedItems.length(); x++) {
                JSONObject p = lsiSequencedItems.getJSONObject(x);
                JSONObject page1=new JSONObject(p.toString());
                page1.put("buildPosition", newBuild);
                String title1 = page1.getString("title");
                if (!verDupl.contains(title1)) {
                    verDupl.add(title1);
                    generalSequencedItems.put(page1);
                    newBuild++;
                }
            }
            for (int x = 0; x < igSequencedItems.length(); x++) {
                JSONObject p = igSequencedItems.getJSONObject(x);
                JSONObject page1=new JSONObject(p.toString());
                page1.put("buildPosition", newBuild);
                String title1 = page1.getString("title");
                if (!verDupl.contains(title1)) {
                    verDupl.add(title1);
                    generalSequencedItems.put(page1);
                    newBuild++;
                }
            }
            /*
            for(int x=0;x<tsSequencedItems.length();x++){
                boolean flag = false;
                JSONObject pageTS=tsSequencedItems.getJSONObject(x);
                String title=pageTS.getString("title");
                for(int y=0;y<tfIdfSequencedItems.length();y++){
                    JSONObject page1=tfIdfSequencedItems.getJSONObject(x);
                    String title1=page1.getString("title");
                    if(title1.equalsIgnoreCase(title)){
                        flag=true;
                        break;
                    }
                    else{
                        flag=false;
                    }
                }
                for(int y=0;y<lsiSequencedItems.length();y++){
                    JSONObject page1=lsiSequencedItems.getJSONObject(x);
                    String title1=page1.getString("title");
                    if(title1.equalsIgnoreCase(title)){
                        flag=true;
                        break;
                    }
                    else{
                        flag=false;
                    }
                }
                for(int y=0;y<igSequencedItems.length();y++){
                    JSONObject page1=igSequencedItems.getJSONObject(x);
                    String title1=page1.getString("title");
                    if(title1.equalsIgnoreCase(title)){
                        flag=true;
                        break;
                    }
                    else{
                        flag=false;
                    }
                }
                
                if(flag){
                    generalSequencedItems.put(pageTS);
                }
                
            }
            */
            //wrapping dei risultati
            JSONObject data = new JSONObject();
            data.put(RankType.TEACHING_STYLE.getLabel(), tsSequencedItems);
            data.put(RankType.TF_IDF.getLabel(), tfIdfSequencedItems);
            data.put(RankType.LSI.getLabel(), lsiSequencedItems);
            data.put(RankType.IG.getLabel(), igSequencedItems);
            data.put("general", generalSequencedItems);

            //generazione del grafo
            JSONObject graph = this.graphService.buildGraph(pageLinksDTO);
            JSONArray selectionInfos = graph.getJSONArray("selectionInfos");
            graph.remove("selectionInfos");

            JSONObject result = new JSONObject();
            result.put("data", data);
            result.put("graph", graph);
            result.put("selectionInfos", selectionInfos);

            return result;

        } catch (Exception e) {
            logger.error("Error while generating page \"" + startingPageTitle + "\" sequence", e);
            throw new Exception("Error while generating page \"" + startingPageTitle + "\" sequence", e);
        }
    }

    private JSONArray getSequencedItems(RankType rankType, PageLinksDTO pageLinksDTO, Set<PageLinksDTO> allPages) throws Exception {
        Map<Integer, PageLinksDTO> selectedPages = new TreeMap<>();

        selectedPages.put(0, pageLinksDTO);

        if (rankType == RankType.TEACHING_STYLE) {
            sequencePages(pageLinksDTO, selectedPages, rankType, RankType.TF_IDF, 1, allPages);
        } else {
            sequencePages(pageLinksDTO, selectedPages, rankType, null, 1, allPages);
        }

        //wrap dei risultati
        JSONArray sequencedItems = new JSONArray();
        int positionCounter = 1;
        for (Map.Entry<Integer, PageLinksDTO> selectedPageDTO : selectedPages.entrySet()) {
            try {
                Page page = selectedPageDTO.getValue().getPage();

                JSONObject object = page.getBasicJSON();//wikipedia.getPageBaseObject(page.getTitle());
                /*System.out.println();
                        System.out.println(object.toString());*/
                try {
                    object.put("pageId", page.getId().longValue());
                } catch (Exception e) {
                    //object.put("pageId", positionCounter);
                    object.put("pageId", page.getTitle());
                }

                object.put("buildPosition", selectedPageDTO.getKey());
                object.put("score", selectedPageDTO.getValue().getRankingScore(rankType).doubleValue());

                for (Map.Entry<RankType, Double> rankScore : selectedPageDTO.getValue().getRankingScores().entrySet()) {
                    object.put(rankScore.getKey().getLabel(), rankScore.getValue().doubleValue());
                }

                positionCounter++;
                sequencedItems.put(object);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        return sequencedItems;
    }

    private void getAllPages(PageLinksDTO pageLinksDTO, Map<String, PageLinksDTO> retrievedPages) {

        if (!retrievedPages.containsKey(pageLinksDTO.getTitle())) {
            retrievedPages.put(pageLinksDTO.getTitle(), pageLinksDTO);

            for (PageLinksDTO link : pageLinksDTO.getLinkedPages()) {
                getAllPages(link, retrievedPages);
            }
        }
    }

    private void sequencePages(PageLinksDTO pageLinksDTO, Map<Integer, PageLinksDTO> selectedPages, RankType firstRankType, RankType secondRankType, int actualLevel, Set<PageLinksDTO> allPages) {

        PageLinksDTO bestPage = null;
        double minLiv = 0;
        //for(PageLinksDTO linkDTO : pageLinksDTO.getLinkedPages()){
        Set<PageLinksDTO> values = new HashSet<>();
        values.addAll(allPages);
        for (PageLinksDTO linkDTO : allPages) {

            //if(linkDTO.getLevel()>=(actualLevel) && !linkDTO.isSelectedFromSequenceAlgorithm(firstRankType)){
            if (linkDTO.getRankingScore(firstRankType) == null) {
                logger.warn("Strange.... invalid " + firstRankType.getDescription() + " score for page " + linkDTO.getTitle());
                continue;
            }

            if (bestPage == null) {
                bestPage = linkDTO;

            } else {

                if (firstRankType.getComparator().compare(linkDTO.getRankingScore(firstRankType), bestPage.getRankingScore(firstRankType)) > 0) {
                    bestPage = linkDTO;
                } else if (firstRankType.getComparator().compare(linkDTO.getRankingScore(firstRankType), bestPage.getRankingScore(firstRankType)) == 0 && secondRankType != null) {
                    if (secondRankType.getComparator().compare(linkDTO.getRankingScore(secondRankType), bestPage.getRankingScore(secondRankType)) > 0) {
                        bestPage = linkDTO;
                    }
                }
            }
            //}
        }

        if (bestPage != null) {
            Collection<PageLinksDTO> values1 = selectedPages.values();
            boolean flag=true;
            for(PageLinksDTO p:values1){
                if(p.getTitle().equalsIgnoreCase(bestPage.getTitle())){
                    flag=false;
                    break;
                }
            }
            values.remove(bestPage);
            int dim = selectedPages.size();
            if (flag) {
                bestPage.setSelectedFromSequenceAlgorithm(firstRankType, true);                
                selectedPages.put(dim, bestPage);
                dim++;
            }
            //sequencePages(bestPage, selectedPages, firstRankType, secondRankType, actualLevel+1);
            if (dim < this.config.getScanNumberResults()) {
                sequencePages(bestPage, selectedPages, firstRankType, secondRankType, actualLevel, values);
            }

        }
    }

    private UserTeachingStyle getUserTeachingStyle() {
        try {
            User user = this.userDetailsService.getRequestUser();

            return user.getTeachingStyle();

        } catch (Exception e) {
            logger.error("Error while retrieving user teaching style ", e);
            return null;
        }
    }
}

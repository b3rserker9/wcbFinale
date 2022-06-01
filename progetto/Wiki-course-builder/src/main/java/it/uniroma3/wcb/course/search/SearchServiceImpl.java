package it.uniroma3.wcb.course.search;

import it.uniroma3.wcb.course.Corpus;
import it.uniroma3.wcb.course.ranking.InformationGainProcessor;
import it.uniroma3.wcb.course.ranking.LSIProcessor_Jblas;
import it.uniroma3.wcb.course.ranking.RankType;
import it.uniroma3.wcb.course.ranking.TeachingStyleComparator;
import it.uniroma3.wcb.course.ranking.TfIdfProcessor;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.dao.ContextTermsRepository;
import it.uniroma3.wcb.persistence.dao.QueryRepository;
import it.uniroma3.wcb.persistence.model.ContextTerms;
import it.uniroma3.wcb.persistence.model.Page;
import it.uniroma3.wcb.persistence.model.Query;
import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.persistence.model.UserTeachingStyle;
import it.uniroma3.wcb.security.RequestUserService;
import it.uniroma3.wcb.spring.AppConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wikipedia.Wiki;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private ContextTermsRepository contextTermsRepository;

    //@Autowired
    private CustomWiki wikipedia;

    @Autowired
    private PageService pageService;

    @Autowired
    private TeachingStyleComparator tsComparator;

    @Autowired
    private RequestUserService userDetailsService;

    @Autowired
    private AppConfig config;

    private final ExtraConfig configs = ExtraConfig.getIstance();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public SearchServiceImpl() {
    }

    @Override
    public JSONObject executeSearch(String queryString, String terms, RankType defaultOrder, String language) throws Exception {
        return this.executeSearch(queryString, terms, defaultOrder, this.configs.wikiDefaultMaxResult(), language);
    }

    @Override
    public JSONObject executeSearch(String queryString, String terms, RankType defaultOrder, int maxResults, String language) throws Exception {

        long start = System.currentTimeMillis();
        try {

            User user = userDetailsService.getRequestUser();

            Query query = this.queryRepository.getByQueryString(queryString);

            boolean queryIsNew = false;
            Date now = new Date();

            if (query == null) {
                queryIsNew = true;
                query = new Query();
                query.setCreated(now);
                query.setLastModify(now);
                query.setUserId(user.getId());
                query.setQueryString(queryString);
                query = this.queryRepository.save(query);
            }

            if (query.getId() > 0 && terms != null && !"".equals(terms)) {
                if (queryIsNew || this.contextTermsRepository.getByQueryIdAndTerms(query.getId(), terms) == null) {

                    ContextTerms qt = new ContextTerms();
                    qt.setCreated(now);
                    qt.setLastModify(now);
                    qt.setUserId(user.getId());
                    qt.setTerms(terms);
                    qt.setQueryId(query.getId());

                    this.contextTermsRepository.save(qt);
                }
            }
        } catch (Exception e) {
            logger.error("Error while saving a new query item", e);
        }

        List<String> words = this.getAllQueryWords(queryString, terms);
        String searchConstraints = this.getConstraintsFromWordList(words);

        //invoke wiki api
        JSONObject results = null;
        String trim = language.trim();
        if(!trim.isEmpty()){
            wikipedia=new CustomWiki(trim+".wikipedia.org");
        }
        else{
            wikipedia=new CustomWiki();
        }
        if (maxResults > 0) {
            results = wikipedia.limitedSearch(maxResults, searchConstraints, Wiki.MAIN_NAMESPACE);
        } else {
            results = wikipedia.limitedSearch(searchConstraints, Wiki.MAIN_NAMESPACE);
        }
        //logger.info("num pages resut from search => "+results.getJSONArray("data").length());
        JSONArray orderedArray = rankResults(results, words, defaultOrder, language);

        results.remove("data");
        results.put("data", orderedArray);

        long time = (System.currentTimeMillis() - start);
        results.put("time", String.valueOf(time) + " ms.");

        return results;
    }

    private List<String> getAllQueryWords(String queryString, String terms) {

        List<String> list = new ArrayList<>();

        String[] qSplits = queryString.split(" "); //TODO rimuovere eventuali caratteri di punteggiatura
        for (String qElement : qSplits) {
            if (!list.contains(qElement.trim())) {
                list.add(qElement.trim());
            }
        }

        String[] tSplits = terms.split(" "); //TODO rimuovere eventuali caratteri di punteggiatura
        for (String tElement : tSplits) {
            if (!list.contains(tElement.trim())) {
                list.add(tElement.trim());
            }
        }

        return list;
    }

    private String getConstraintsFromWordList(List<String> words) {
        String constraints = "";
        for (String word : words) {
            constraints += word + " ";
        }

        return constraints.trim();
    }

    private JSONArray rankResults(JSONObject results, List<String> queryWords, RankType defaultOrder, String language) throws Exception {
        JSONArray items = results.getJSONArray("data");
        //List<String> disambiguationsWords = this.configs.getDisambiguationsWords(language);
        String lng=language;
        if(configs.getStemmerType()==0&&language.equalsIgnoreCase("en")){
            lng="wikiporter";
        }
        else if(configs.getStemmerType()>1&&language.equalsIgnoreCase("en")){
            lng+="2";
        }
        int numResults=(configs.getNumberGroupQuerySearch()>1)?configs.getNumberGroupQuerySearch():configs.wikiDefaultMaxResult();
        
        if (items != null) {
            //search result map - <document key, document object>
            Map<String, JSONObject> resultsMap = new LinkedHashMap<>();

            Corpus stemmedCorpus = new Corpus(lng,configs.getStopWords(language), configs.getSectionsToSkip(language), configs.getTextTypeRank());

            //logged user teaching style
            UserTeachingStyle userTS = getUserTeachingStyle();

            Map<String, Double> TS_Scores = new HashMap<>();

            int decPrecision = this.config.getDecimalPrecision();
            Set<String> pagess = new HashSet<>();
            int len=items.length();
            Set<Page> pages = new HashSet<>();
            
            int volte = (len/numResults)+1;
            int delta=0;
            Map<String, JSONObject> jo = new HashMap<>();
            for (int x = 0; x < volte; x++) {

                
                //for (int i = 0; i < len; i++) {
                
                for (int i = 0; i < numResults; i++) {
                    int ind= i+delta;
                    if(ind<len){
                        JSONObject obj = items.getJSONObject(ind);
                        String title = obj.getString("title");
                        if (title != null && !"".equals(title)) {
                            pagess.add(title);
                            jo.put(title.toLowerCase(), obj);
                        } else {
                            logger.warn("Page with no title. Item will be skipped.");
                            continue;
                        }
                    }
                    else{
                        break;
                    }

                }
                delta+=numResults;
                try {
                    //retrieves page from title...
                    int sz = config.getMaxLoopSize();

                    pages.addAll(this.pageService.getPages(pagess, configs.isMongoDbSearchOn(language), config.cachePagesOnDatabase(), sz, language));
                } catch (IOException e) {
                    logger.warn("Error while retrieving text of pages", e);

                }

            }
            int i = 0;
            for (int j = 0; j < numResults; j++) {
                try {
                    JSONObject objj = items.getJSONObject(j);

                    String title = objj.getString("title").toLowerCase().replaceAll("_", " ");
                    if (title != null && !"".equals(title)) {
                        for (Page page : pages) {
                            String title1 = page.getTitle().toLowerCase().replaceAll("_", " ");
                            if (title.equalsIgnoreCase(title1)) {
                                //String type = page.getType(disambiguationsWords);
                                //if(type==null){
                                String key = page.getTitle().toLowerCase();
                                JSONObject obj = jo.get(key);
                                if (obj != null) {
                                    //JSONObject obj = jo.get(page.getTitle());
                                    String text;
                                    if (this.configs.getTextTypeRank() != 0 && this.configs.getTextTypeRank() != 2) {
                                        //String text = page.getOnlyText();

                                        text = page.getOnlyText();
                                    } else {
                                        text = page.getText();
                                    }
                                    if (text != null) {
                                        //... and adds it to corpora 
                                        stemmedCorpus.addDocumentToCollection(String.valueOf(i), text);

                                        //eucledian distance between page teaching style and user teaching style scores
                                        Double tsScore = tsComparator.getTSOrderingScore(userTS, page.getTeachingStyle());
                                        TS_Scores.put(String.valueOf(i), tsScore);

                                        resultsMap.put(String.valueOf(i), obj);
                                        i++;
                                    }
                                }
                                break;
                            }

                            //}
                        }

                    }
                } catch (Exception e) {
                }
            }
            String qw = getConstraintsFromWordList(queryWords);
            //String qw = stemmedCorpus.getStemmer().stemString(qw1);
            //limiting corpus terms size
            stemmedCorpus.limitCorpusTerms(this.config.getLimitedCorpusTermsMaxSize(), this.config.getSkipTermsSmallerThanSize(), qw);

            //TF-IDF elaboration
            TfIdfProcessor tfIdfProcessor = new TfIdfProcessor(stemmedCorpus, decPrecision, true);
            //map containing document key/cosine similarity score
            Map<String, Double> TFIDF_Scores = tfIdfProcessor.scoreDocuments(qw);

            //LSI elaboration
            //LSIProcessor_Jama lsiProcessor = new LSIProcessor_Jama(stemmedCorpus, decPrecision, tfIdfProcessor);
            LSIProcessor_Jblas lsiProcessor = new LSIProcessor_Jblas(stemmedCorpus, this.config.getLsiMaxMatrixReductionRatio(), decPrecision, tfIdfProcessor);
            //map containing document key/cosine similarity score
            Map<String, Double> LSI_Scores = lsiProcessor.scoreDocuments(qw);

            //Information Gain elaboration
            InformationGainProcessor igProcessor = new InformationGainProcessor(stemmedCorpus, decPrecision);
            Map<String, Double> IG_Scores = igProcessor.scoreDocuments(qw);

            SortedMap<Double, List<JSONObject>> orderedMap = null;
            if (defaultOrder == null || RankType.TEACHING_STYLE == defaultOrder) {
                orderedMap = new TreeMap<>();
            } else {
                orderedMap = new TreeMap<>(Collections.reverseOrder());
            }

            for (String documentKey : resultsMap.keySet()) {
                double tsRank = getDocumentResult(documentKey, TS_Scores, TeachingStyleComparator.MAX_DISTANCE);
                double tfIdfRank = getDocumentResult(documentKey, TFIDF_Scores, 0.0d);
                double lsiRank = getDocumentResult(documentKey, LSI_Scores, 0.0d);
                double igRank = getDocumentResult(documentKey, IG_Scores, 0.0d);

                JSONObject obj = resultsMap.get(documentKey);
                obj.put(RankType.TEACHING_STYLE.getLabel(), tsRank);
                obj.put(RankType.TF_IDF.getLabel(), tfIdfRank);
                obj.put(RankType.LSI.getLabel(), lsiRank);
                obj.put(RankType.IG.getLabel(), igRank);

                Double pointer = null;
                switch (defaultOrder) {
                    case TEACHING_STYLE:
                        pointer = tsRank;
                        break;
                    case TF_IDF:
                        pointer = tfIdfRank;
                        break;
                    case LSI:
                        pointer = lsiRank;
                        break;
                    case IG:
                        pointer = igRank;
                        break;
                    default:
                        pointer = tsRank;
                        break;
                }

                List<JSONObject> list = orderedMap.get(pointer);
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(obj);
                orderedMap.put(pointer, list);
            }

            //wrapping of results
            JSONArray orderedItems = new JSONArray();
            for (Map.Entry<Double, List<JSONObject>> orderedElement : orderedMap.entrySet()) {
                for (JSONObject obj : orderedElement.getValue()) {
                    orderedItems.put(obj);
                }
            }

            return orderedItems;
        } else {
            return items;
        }
    }
    
    private double getDocumentResult(String documentKey, Map<String, Double> resultMap, double maxValue) {
        if (resultMap != null && documentKey != null && resultMap.containsKey(documentKey) && resultMap.get(documentKey) != null) {
            return resultMap.get(documentKey);
        } else {
            return maxValue;
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

package it.uniroma3.wcb.course.scan;

import it.uniroma3.wcb.course.search.PageService;
import it.uniroma3.wcb.persistence.model.Page;
import it.uniroma3.wcb.spring.AppConfig;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import java.util.TreeMap;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi (j-hard)
 *
 */
@Service("scanService")
public class ScanServiceImpl implements ScanService {

    @Autowired
    private AppConfig configs;

    private final ExtraConfig extraConfigs = ExtraConfig.getIstance();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PageService pageService;

    //list of section to be skipped during scan process. Setted on configuration file
    //private Set<String> sectionToSkip;

    @Override
    public PageLinksDTO scanPageLinksNoSql(String startingPageTitle, int scanDepthLevel, String language) throws Exception {

        //map of all the retrieved scanPages, useful to avoid making multiples of a same page scan
        Map<String, PageLinksDTO> uniquePages = new HashMap<>();

        //map with all elaborated scanPages
        Map<String, PageLinksDTO> scannedPages = new HashMap<>();

        //map with all scanPages per scan depth level
        Map<Integer, Set<PageLinksDTO>> linksPerLevel = new HashMap<>();

        //starting page retrieving
        Page firstPage = null;
        try {
            firstPage = getPage(startingPageTitle, language);
        } catch (Exception e) {
            logger.error("Cannot retrieve starting page: " + startingPageTitle, e);
            throw new Exception("Cannot retrieve starting page: " + startingPageTitle, e);
        }

        if (firstPage == null) {
            throw new Exception("Page " + startingPageTitle + " cannot be retrieved!");
        }
        if (firstPage.getText() == null || firstPage.getText().isEmpty()) {
            throw new Exception("Page " + startingPageTitle + " doesn't have a valid text!");
        }

        PageLinksDTO firstPageDTO = new PageLinksDTO(firstPage, 0, "0");
        Set<PageLinksDTO> level0 = new HashSet<>();
        level0.add(firstPageDTO);
        linksPerLevel.put(new Integer(0), level0);

        int counter = 1;
        Map<String, Page> visited = new TreeMap<>();
        visited.put(firstPage.getTitle().toLowerCase(), firstPage);
        Set<String> removed = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        removed.add(firstPage.getTitle());
        //for each level, starting from first one....
        for (int i = 1; i <= scanDepthLevel; i++) {
            Set<PageLinksDTO> actualLevelLinks = new HashSet<>();
            //... and for each retrieved page from previous scan process step...
            for (PageLinksDTO dto : linksPerLevel.get(i - 1)) {

                //... if page wasn't analized before...
                if (!scannedPages.containsKey(dto.getTitle().toLowerCase())) {

                    try {
                        //...all page external link are retrieved...

                        Set<String> pageLinks = dto.getPage().getPageLinks(language,configs.getNumberSectionLinks(),extraConfigs.getSectionsToSkip(language));
                        Set<String> pl2 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
                        pl2.addAll(pageLinks);
                        //pl2.retainAll(visited.keySet());
                        pl2.retainAll(removed);
                        pageLinks.removeAll(pl2);
                        Set<Page> lp = getPages(pageLinks, language);

                        for (String s1 : pl2) {
                            Page pa = visited.get(s1.toLowerCase());
                            if (pa != null) {
                                lp.add(pa);
                            }
                        }
                        for (Page page : lp) {

                            if (page != null) {
                                if (page.getTitle() != null) {
                                    if (!visited.containsKey(page.getTitle())) {
                                        visited.put(page.getTitle().toLowerCase(), page);
                                        removed.add(page.getTitle());
                                    }
                                    PageLinksDTO pageLinksDto = null;
                                    if (scannedPages.containsKey(page.getTitle().toLowerCase())) {
                                        pageLinksDto = scannedPages.get(page.getTitle().toLowerCase());
                                    } else if (uniquePages.containsKey(page.getTitle().toLowerCase())) {
                                        pageLinksDto = uniquePages.get(page.getTitle().toLowerCase());
                                    } else {

                                        pageLinksDto = new PageLinksDTO(page, i, String.valueOf(counter));
                                        uniquePages.put(pageLinksDto.getTitle().toLowerCase(), pageLinksDto);
                                        counter++;
                                    }

                                    //... adding new link to actual dto...
                                    dto.addLinkedPage(pageLinksDto);

                                    actualLevelLinks.add(pageLinksDto);
                                }
                            }

                        }

                    } catch (Exception e) {
                        logger.trace("Cannot retrieve page: ");
                        continue;
                    }

                    linksPerLevel.put(new Integer(i), actualLevelLinks);
                }

                //...actual dto scan done... adding it to elaborated ones..
                scannedPages.put(dto.getTitle().toLowerCase(), dto);
            }

        }

        return firstPageDTO;
    }

    //@Override
    public PageLinksDTO scanPageLinksNoSqlLevelReverse(String startingPageTitle, int scanDepthLevel, String language) throws Exception {

        //map of all the retrieved scanPages, useful to avoid making multiples of a same page scan
        Map<String, PageLinksDTO> uniquePages = new HashMap<>();

        //map with all elaborated scanPages
        Map<String, PageLinksDTO> scanPages = new HashMap<>();

        //map with all scanPages per scan depth level
        Map<Integer, Set<PageLinksDTO>> linksPerLevel = new HashMap<>();

        //starting page retrieving
        Page firstPage = null;
        try {
            firstPage = getPage(startingPageTitle, language);
        } catch (Exception e) {
            logger.error("Cannot retrieve starting page: " + startingPageTitle, e);
            throw new Exception("Cannot retrieve starting page: " + startingPageTitle, e);
        }

        if (firstPage == null) {
            throw new Exception("Page " + startingPageTitle + " cannot be retrieved!");
        }
        if (firstPage.getText() == null || firstPage.getText().isEmpty()) {
            throw new Exception("Page " + startingPageTitle + " doesn't have a valid text!");
        }

        PageLinksDTO firstPageDTO = new PageLinksDTO(firstPage, 0, "0");
        Set<PageLinksDTO> level0 = new HashSet<>();
        level0.add(firstPageDTO);
        linksPerLevel.put(new Integer(0), level0);

        int counter = 1;

        //for each level, starting from first one....
        for (int i = 1; i <= scanDepthLevel; i++) {
            Set<PageLinksDTO> actualLevelLinks = new HashSet<>();
            Map<String, Set<String>> pageLinksMap = new HashMap<>();
            Map<String, Set<String>> pageMapLinks = new HashMap<>();
            Set<String> pageLinks = new HashSet<>();

            //... and for each retrieved page from previous scan process step...
            for (PageLinksDTO dto : linksPerLevel.get(i - 1)) {
                if (!scanPages.containsKey(dto.getTitle().toLowerCase())) {
                    Set<String> pageLinksNoSql = dto.getPage().getPageLinks(language,configs.getNumberSectionLinks(),extraConfigs.getSectionsToSkip(language));
                    pageLinksMap.put(dto.getTitle().toLowerCase(), pageLinksNoSql);//.addAll(this.getPageLinksNoSql(dto.getPage()));
                    pageLinks.addAll(pageLinksNoSql);
                    scanPages.put(dto.getTitle().toLowerCase(), dto);
                    for (String s1 : pageLinksNoSql) {
                        Set<String> get = pageMapLinks.get(s1.toLowerCase());
                        if (get == null) {
                            get = new HashSet<>();
                        }
                        get.add(dto.getTitle().toLowerCase());
                        pageMapLinks.put(s1.toLowerCase(), get);
                    }
                }
            }
            if (pageLinks.size() > 0) {

                try {
                    Set<Page> lp = getPages(pageLinks, language);
                    //...all page external link are retrieved...

                    for (Page page : lp) {

                        if (page != null) {
                            if (page.getTitle() != null) {
                                PageLinksDTO pageLinksDto = null;
                                if (scanPages.containsKey(page.getTitle().toLowerCase())) {
                                    pageLinksDto = scanPages.get(page.getTitle().toLowerCase());
                                } else if (uniquePages.containsKey(page.getTitle().toLowerCase())) {
                                    pageLinksDto = uniquePages.get(page.getTitle().toLowerCase());
                                } else {
                                    pageLinksDto = new PageLinksDTO(page, i, String.valueOf(counter));
                                    uniquePages.put(pageLinksDto.getTitle().toLowerCase(), pageLinksDto);
                                    counter++;
                                }

                                for (String etr : pageMapLinks.get(page.getTitle().toLowerCase())) {
                                    //... adding new link to dto...
                                    scanPages.get(etr).addLinkedPage(pageLinksDto);
                                }

                                actualLevelLinks.add(pageLinksDto);
                            }
                        }

                    }

                } catch (Exception e) {
                    logger.trace("Cannot retrieve page: ");
                    continue;
                }

                linksPerLevel.put(new Integer(i), actualLevelLinks);

            }
        }

        /* prova linkaggio alla fine
                for(Map.Entry<String , PageLinksDTO> ep: scanPages.entrySet()){
                    PageLinksDTO dto = ep.getValue();
                    for(String l:pageLinksMap.get(ep.getKey())){
                        dto.addLinkedPage(scanPages.get(l.toLowerCase()));
                    }
                           
                }
         */
        return firstPageDTO;
    }

    @Override
    public PageLinksDTO scanPageLinksNoSqlLevel(String startingPageTitle, int scanDepthLevel, String language) throws Exception {

        //map of all the retrieved scanPages, useful to avoid making multiples of a same page scan
        Map<String, PageLinksDTO> uniquePages = new HashMap<>();

        //map with all elaborated scanPages
        Map<String, PageLinksDTO> scannedPages = new HashMap<>();

        //map with all scanPages per scan depth level
        Map<Integer, Set<PageLinksDTO>> linksPerLevel = new HashMap<>();

        //starting page retrieving
        Page firstPage = null;
        try {
            firstPage = getPage(startingPageTitle, language);
        } catch (Exception e) {
            logger.error("Cannot retrieve starting page: " + startingPageTitle, e);
            throw new Exception("Cannot retrieve starting page: " + startingPageTitle, e);
        }

        if (firstPage == null) {
            throw new Exception("Page " + startingPageTitle + " cannot be retrieved!");
        }
        if (firstPage.getText() == null || firstPage.getText().isEmpty()) {
            throw new Exception("Page " + startingPageTitle + " doesn't have a valid text!");
        }

        PageLinksDTO firstPageDTO = new PageLinksDTO(firstPage, 0, "0");
        Set<PageLinksDTO> level0 = new HashSet<>();
        level0.add(firstPageDTO);
        linksPerLevel.put(new Integer(0), level0);

        int counter = 1;

        ///previsited scanPages
        Map<String, PageLinksDTO> pages = new HashMap<>();
        Map<String, Page> visited = new TreeMap<>();
        visited.put(firstPage.getTitle().toLowerCase(), firstPage);
        //for each level, starting from first one....
        for (int i = 1; i <= scanDepthLevel; i++) {

            Map<String, Set<String>> pageLinksMap = new TreeMap<>();
            Set<String> pageLinks = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            for (PageLinksDTO dto : linksPerLevel.get(i - 1)) {
                if (!pages.containsKey(dto.getTitle().toLowerCase())) {
                    String tlt=dto.getTitle();
                    Set<String> pageLinksNoSql = dto.getPage().getPageLinks(language,configs.getNumberSectionLinks(),extraConfigs.getSectionsToSkip(language));
                    pageLinksMap.put(tlt.toLowerCase(), pageLinksNoSql);//.addAll(this.getPageLinksNoSql(dto.getPage()));
                    pageLinks.addAll(pageLinksNoSql);
                    pages.put(tlt.toLowerCase(), dto);
                    /*for(String s1: pageLinksNoSql){
                                    Set<String> get = pageMapLinks.get(s1.toLowerCase());
                                    if(get==null){
                                        get=new HashSet<>();
                                    }
                                    get.add(dto.getTitle().toLowerCase());
                                    pageMapLinks.put(s1.toLowerCase(), get);
                                }*/
                }
            }
            //try {

            Set<String> pl2 = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            pl2.addAll(pageLinks);
            pl2.retainAll(visited.keySet());
            pageLinks.removeAll(pl2);
            visited.putAll(getMapPages(pageLinks, language));
            Set<PageLinksDTO> actualLevelLinks = new HashSet<>();
            //... and for each retrieved page from previous scan process step...
            for (PageLinksDTO dto : linksPerLevel.get(i - 1)) {

                //... if page wasn't analized before...
                if (!scannedPages.containsKey(dto.getTitle().toLowerCase())) {

                    //...all page external link are retrieved...
                    //for(Map.Entry<String,Page> page1 : lp.entrySet()){
                    for (String paget : pageLinksMap.get(dto.getTitle().toLowerCase())) {

                        Page page = visited.get(paget.toLowerCase());
                        if (page != null) {
                            if (page.getTitle() != null) {
                                
                                PageLinksDTO pageLinksDto = null;
                                if (scannedPages.containsKey(page.getTitle().toLowerCase())) {
                                    pageLinksDto = scannedPages.get(page.getTitle().toLowerCase());
                                } else if (uniquePages.containsKey(page.getTitle().toLowerCase())) {
                                    pageLinksDto = uniquePages.get(page.getTitle().toLowerCase());
                                } else {

                                    pageLinksDto = new PageLinksDTO(page, i, String.valueOf(counter));
                                    uniquePages.put(pageLinksDto.getTitle().toLowerCase(), pageLinksDto);
                                    counter++;
                                }

                                //... adding new link to actual dto...
                                dto.addLinkedPage(pageLinksDto);

                                actualLevelLinks.add(pageLinksDto);
                            }
                        }

                    }

                    linksPerLevel.put(new Integer(i), actualLevelLinks);
                }

                //...actual dto scan done... adding it to elaborated ones..
                scannedPages.put(dto.getTitle().toLowerCase(), dto);
            }
            /* } catch (Exception e) {
                logger.trace("Cannot retrieve page: ");
                return firstPageDTO;

            }*/

        }

        return firstPageDTO;
    }

    @Override
    public PageLinksDTO scanPageLinks(String startingPageTitle, int scanDepthLevel, String language) throws Exception {

        //map of all the retrieved scanPages, useful to avoid making multiples of a same page scan
        Map<String, PageLinksDTO> uniquePages = new HashMap<>();

        //map with all elaborated scanPages
        Map<String, PageLinksDTO> scannedPages = new HashMap<>();

        //map with all scanPages per scan depth level
        Map<Integer, Set<PageLinksDTO>> linksPerLevel = new HashMap<>();

        //starting page retrieving
        Page firstPage = null;
        try {
            firstPage = getPage(startingPageTitle, language);
        } catch (Exception e) {
            logger.error("Cannot retrieve starting page: " + startingPageTitle, e);
            throw new Exception("Cannot retrieve starting page: " + startingPageTitle, e);
        }

        if (firstPage == null) {
            throw new Exception("Page " + startingPageTitle + " cannot be retrieved!");
        }
        if (firstPage.getText() == null || firstPage.getText().isEmpty()) {
            throw new Exception("Page " + startingPageTitle + " doesn't have a valid text!");
        }

        PageLinksDTO firstPageDTO = new PageLinksDTO(firstPage, 0, "0");
        Set<PageLinksDTO> level0 = new HashSet<>();
        level0.add(firstPageDTO);
        linksPerLevel.put(new Integer(0), level0);

        int counter = 1;

        //for each level, starting from first one....
        for (int i = 1; i <= scanDepthLevel; i++) {
            Set<PageLinksDTO> actualLevelLinks = new HashSet<>();
            //... and for each retrieved page from previous scan process step...
            for (PageLinksDTO dto : linksPerLevel.get(i - 1)) {

                //... if page wasn't analized before...
                if (!scannedPages.containsKey(dto.getTitle().toLowerCase())) {

                    //...all page external link are retrieved...
                    Set<String> pageLinks = dto.getPage().getPageLinks(language,configs.getNumberSectionLinks(),extraConfigs.getSectionsToSkip(language));

                    for (String pageTitle : pageLinks) {

                        try {
                            //... loading page....

                            /*
							Page page = getPage(pageTitle);
						
							if(page!=null){
								PageLinksDTO pageLinksDto = null;
								if(scannedPages.containsKey(page.getTitle().toLowerCase())){
									pageLinksDto = scannedPages.get(page.getTitle().toLowerCase());
								}
								else if(uniquePages.containsKey(page.getTitle().toLowerCase())){
									pageLinksDto = uniquePages.get(page.getTitle().toLowerCase());
								}
								else{
									pageLinksDto = new PageLinksDTO(page, i, String.valueOf(counter));
									uniquePages.put(pageLinksDto.getTitle().toLowerCase(), pageLinksDto);
									counter++;
								}
								
								//... adding new link to actual dto...
								dto.addLinkedPage(pageLinksDto);
								
								actualLevelLinks.add(pageLinksDto);
							}
                             */
                            PageLinksDTO pageLinksDto = null;
                            if (scannedPages.containsKey(pageTitle.toLowerCase())) {
                                pageLinksDto = scannedPages.get(pageTitle.toLowerCase());
                            } else if (uniquePages.containsKey(pageTitle.toLowerCase())) {
                                pageLinksDto = uniquePages.get(pageTitle.toLowerCase());
                            } else {
                                Page page = getPage(pageTitle, language);
                                if (page != null) {
                                    pageLinksDto = new PageLinksDTO(page, i, String.valueOf(counter));
                                    uniquePages.put(pageLinksDto.getTitle().toLowerCase(), pageLinksDto);
                                    counter++;
                                }
                            }
                            if (pageLinksDto != null) {
                                dto.addLinkedPage(pageLinksDto);

                                actualLevelLinks.add(pageLinksDto);
                            }

                        } catch (Exception e) {
                            logger.trace("Cannot retrieve page: " + pageTitle);
                            continue;
                        }
                    }

                    linksPerLevel.put(new Integer(i), actualLevelLinks);
                }

                //...actual dto scan done... adding it to elaborated ones..
                scannedPages.put(dto.getTitle().toLowerCase(), dto);
            }

        }

        return firstPageDTO;
    }

    private Page getPage(String title, String language) throws Exception {
        Page page = null;
        try {
            int sz=configs.getMaxLoopSize();
            page = this.pageService.getPage(title, extraConfigs.isMongoDbBuildOn(language), configs.cachePagesOnDatabase(),sz, language);
        } catch (Exception e) {
            throw new Exception("Error while retrieving page: " + title, e);
        }

        return page;
    }

    private Set<Page> getPages(Set<String> titles, String language) throws Exception {
        Set<Page> pages = new TreeSet<>();
        try {
            int sz=configs.getMaxLoopSize();
            pages = this.pageService.getPages(titles, extraConfigs.isMongoDbBuildOn(language), configs.cachePagesOnDatabase(),sz, language);
        } catch (Exception e) {
            logger.error("Scan getPages " + e);
        }

        return pages;
    }

    private Map<String, Page> getMapPages(Set<String> titles, String language) throws Exception {
        Map<String, Page> pages = new TreeMap<>();
        try {
            int sz = configs.getMaxLoopSize();
            Set<Page> pgs = this.pageService.getPages(titles, extraConfigs.isMongoDbBuildOn(language), configs.cachePagesOnDatabase(), sz, language);
            for (Page p : pgs) {
                if(p!=null){
                    if (p.getTitle() != null) {
                        try {
                            pages.put(p.getTitle().toLowerCase(), p);
                        } catch (Exception e) {
                            logger.error("Scan MapPage Errore title in map" + e);
                        }

                    }
                }
            }
        } catch (Exception e) {
            logger.error("Scan MapPage " + e);
        }

        return pages;
    }

/*
    @PostConstruct
    public void init() {
        this.sectionToSkip = this.configs.getSectionsToSkip();
    }
*/
}

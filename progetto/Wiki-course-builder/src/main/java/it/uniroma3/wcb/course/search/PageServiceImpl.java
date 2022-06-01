package it.uniroma3.wcb.course.search;

import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.dao.PageRepository;
import it.uniroma3.wcb.persistence.dao.nosql.MongoDbDaoImp;
import it.uniroma3.wcb.persistence.dao.nosql.NoSqlDbDao;
import it.uniroma3.wcb.persistence.model.Page;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import org.openide.util.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi (j-hard)
 *
 */
@Service("pageService")
public class PageServiceImpl implements PageService {

    @Autowired
    private PageRepository pageRepository;
/*
    @Autowired
    private AppConfig configs;
*/
    private final ExtraConfig extraConfigs = ExtraConfig.getIstance();
    //@Autowired
    //private NoSqlDbDao nosql;
    private NoSqlDbDao nosql;// = new MongoDbDaoImp("en");

    public void initNoSqlLanguage(String language){
        nosql = new MongoDbDaoImp(language);
    }
    //@Autowired
    private CustomWiki wikipedia;
    
    private List<String> disambiguationsWords;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Page getPage(String title, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language){
        disambiguationsWords = this.extraConfigs.getDisambiguationsWords(language);
        if(this.extraConfigs.getDBOrder(language)==1&&isMongoOn){
            return getPageNoSqlFirst(title, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
        }
        else{
            return getPageMySqlFirst(title, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
        }
    }
    public Page getPageNoSqlFirst(String title, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language){
        
        if (maxLoopSize > 0) {
            maxLoopSize--;
            //Page page = this.pageRepository.getByTitle(title.toLowerCase());
            Page page=null;
            try {
                if(nosql==null){
                    initNoSqlLanguage(language);
                }
                else if(!nosql.getLanguage().equalsIgnoreCase(language)){
                    nosql.closeConnection();
                    initNoSqlLanguage(language);
                }
                page = this.nosql.getWikipediaPage(title.toLowerCase());//this.nosql.getWikipediaPage(title);
                
                if (page != null) {
                    //if(page.getLanguage()==null){
                    //page.setLanguage(language);
                    //}
                    String type = page.getType(disambiguationsWords);
                    if (type != null) {
                        if(!type.toLowerCase().equalsIgnoreCase("dis")){
                            return getPage(type, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                        }
                    }
                }
            } catch (Exception e) {
                page = null;
                logger.warn("getPageNoSql Error while retrieving text of page: " + title + " from mongoDb.");
                //throw e;
            }
            
            if (page == null) {
                
                logger.info("getPageNoSql page not in mysql & mongo ON - "+title);
                page = this.pageRepository.getByTitle(title.toLowerCase(),language);
                if (page != null) {
                    //if(page.getLanguage()==null){
                    page.setLang(language);
                    //}
                    
                    String type = page.getType(disambiguationsWords);
                    boolean isave=true;
                    if (type != null) {
                        if (page.getTitle().trim().toLowerCase().equalsIgnoreCase(type.trim().toLowerCase())) {
                            isave = false;
                        }
                        if(!type.toLowerCase().equalsIgnoreCase("dis")){
                            return getPage(type, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                        }
                    }
                    
                    if (saveIfNotExistsInDB&&isave) {
                        page=saveInNoSql(page);
                    }
                }
            }
            if (page == null) {
                logger.info("getPageNoSql page not in mysql & mongo ON && NOT in mongo ...try get from wikipedia - "+title);
                page = getPageFromWikipedia(title, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                if(page!=null){
                    String type = page.getType(disambiguationsWords);
                    if (type != null) {
                        if(!type.toLowerCase().equalsIgnoreCase("dis")){
                            return getPage(type, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                        }
                    }
                }
            }
            else{
                logger.info("Categories => "+page.getCategories());
            }
            return page;
        }
        return null;
    }
    public Page getPageMySqlFirst(String title, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language){
        
        if (maxLoopSize > 0) {
            maxLoopSize--;
            Page page = this.pageRepository.getByTitle(title.toLowerCase(),language);
            if(page!=null){
                //if(page.getLanguage()==null){
                    page.setLang(language);
                //}
                String type = page.getType(disambiguationsWords);
                if (type != null) {
                    if(!type.toLowerCase().equalsIgnoreCase("dis")){
                        return getPage(type, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                    }
                }
            }
            if (page == null && isMongoOn) {
                if(nosql==null){
                    initNoSqlLanguage(language);
                }
                else if(!nosql.getLanguage().equalsIgnoreCase(language)){
                    nosql.closeConnection();
                    initNoSqlLanguage(language);
                }
                logger.info("getPagesMysql page not in mysql & mongo ON - "+title);
                try {
                    page = this.nosql.getWikipediaPage(title.toLowerCase()); //this.nosql.getWikipediaPage(title);
                    
                    if (page != null) {
                        
                        String type = page.getType(disambiguationsWords);
                        boolean isave = true;
                        if (type != null) {
                            if (page.getTitle().trim().toLowerCase().equalsIgnoreCase(type.trim().toLowerCase())) {
                                isave = false;
                            }
                            if(!type.toLowerCase().equalsIgnoreCase("dis")){
                                return getPage(type, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                            }
                        }
                        if (saveIfNotExistsInDB && isave) {
                            page = saveInMySql(page);
                        }
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    page = null;
                    logger.warn("getPagesMysql Error while retrieving text of page: " + title + " from mongoDb.");
                    //throw e;
                }
            }
            if (page == null) {
                logger.info("getPagesMysql page not in mysql & mongo ON && NOT in mongo ...try get from wikipedia - " + title);
                page = getPageFromWikipedia(title, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                if(page!=null){
                    String type = page.getType(disambiguationsWords);
                    if (type != null) {
                        if (!type.toLowerCase().equalsIgnoreCase("dis")) {
                            return getPage(type, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                        }
                    }
                }
            }
            return page;
        }
        return null;
    }

    @Override
    public Set<Page> getPages(Set<String> titles, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language) {
        disambiguationsWords = this.extraConfigs.getDisambiguationsWords(language);
        if (this.extraConfigs.getDBOrder(language) == 1 && isMongoOn) {
            return getPagesNoSqlFirst(titles, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
        } else {
            return getPagesMySqlFirst(titles, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
        }
    }

    public Set<Page> getPagesNoSqlFirst(Set<String> titles, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language) {
        Set<Page> pages = new HashSet<>();
        if (titles.size() < 2) {
            for (String title : titles) {

                Page page = getPage(title, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                if (page != null) {
                    pages.add(page);
                }

            }
            return pages;
        }
        if (maxLoopSize > 0) {
            maxLoopSize--;
            Set<String> tts = new TreeSet<>();
            Set<String> total = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            int cont = 0;
            for (String tl : titles) {
                if (tl != null) {
                    tts.add(tl.toLowerCase());
                    total.add(tl);
                    cont++;

                }
            }
            String warn = "";
            if (cont < 2) {
                warn = " | " + titles + "";
            }
            logger.info("getPagesNoSql() total pageRequest => " + cont + " " + warn);

            //List<Page> pgs = this.pageRepository.findAllByTitles(tts);
            Set<String> present = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            Set<String> redirect = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            if (isMongoOn) {
                if (nosql == null) {
                    initNoSqlLanguage(language);
                } else if (!nosql.getLanguage().equalsIgnoreCase(language)) {
                    nosql.closeConnection();
                    initNoSqlLanguage(language);
                }
                cont=0;
                Map<String, Page> wikipediaMapPages = nosql.getWikipediaMapPages(tts); //(total);
                for (Entry<String, Page> pe : wikipediaMapPages.entrySet()) {

                    Page p = pe.getValue();
                    if (p.getTitle() != null) {
                        String type = p.getType(disambiguationsWords);
                        if (type != null) {
                            if (!type.toLowerCase().equalsIgnoreCase("dis")) {
                                redirect.add(type);
                            }
                        } else {
                            p.setLang(language);
                            pages.add(p);
                            cont++;
                        }

                        total.remove(p.getTitle());
                        tts.remove(p.getTitle().toLowerCase());

                    }

                }
            }
            //total.removeAll(present);

            logger.info("getPagesNoSql() present in NoSql => " + cont);

            cont = 0;
            List<String> tts1 = new LinkedList<>();
            for (String t1 : tts) {
                tts1.add(t1);
            }
            if (tts1.size() > 0) {
                List<Page> pgs = this.pageRepository.findAllByTitles(tts1, language);
                cont = 0;
                for (Page pg : pgs) {
                    if (pg.getTitle() != null) {
                        //present.add(pg.getTitle());

                        String type = pg.getType(disambiguationsWords);

                        boolean isave = true;

                        if (type != null) {
                            if (pg.getTitle().trim().toLowerCase().equalsIgnoreCase(type.trim().toLowerCase())) {
                                isave = false;
                            }
                            if (!type.toLowerCase().equalsIgnoreCase("dis")) {
                                redirect.add(type);
                            }
                        }

                        if (saveIfNotExistsInDB && isave) {
                            pg = saveInNoSql(pg);
                        }

                        if (type == null) {
                            pg.setLang(language);
                            pages.add(pg);

                            cont++;
                        }

                        total.remove(pg.getTitle());
                        tts.remove(pg.getTitle().toLowerCase());
                        tts1.remove(pg.getTitle().toLowerCase());
                    }
                }
            }
            logger.info("getPagesNoSql() present in MySql => " + cont);
            //total.removeAll(present);
            cont = 0;
            if (redirect.size() > 0) {
                Set<Page> pages1 = getPages(redirect, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                for (Page pg : pages1) {
                    if (pg.getTitle() != null) {
                        total.remove(pg.getTitle());
                    }
                }
            }
            int size = total.size();
            if (size > 1) {

                //logger.info("getPagesNoSql() request to Wikipedia pages=> " + total);
                pages.addAll(getPagesFromWikipedia(total, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language));
            } else if (size > 0) {
                //logger.info("getPagesNoSql() request to Wikipedia page=> " + total);
                for (String tl : total) {
                    Page pageFromWikipedia = getPageFromWikipedia(tl, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                    if (pageFromWikipedia != null) {
                        pages.add(pageFromWikipedia);
                    }
                }

            }
            logger.info("getPagesNoSql() present in Wikipedia => " + cont);
            return pages;
        }
        return pages;
    }

    public Set<Page> getPagesMySqlFirst(Set<String> titles, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language) {
        Set<Page> pages = new HashSet<>();
        if (titles.size() < 2) {
            for (String title : titles) {

                Page page = getPage(title, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                if (page != null) {
                    pages.add(page);
                }

            }
            return pages;
        }
        if (maxLoopSize > 0) {
            maxLoopSize--;
            List<String> tts = new LinkedList<>();
            Set<String> total = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            int cont = 0;
            for (String tl : titles) {
                if (tl != null) {
                    tts.add(tl.toLowerCase());
                    total.add(tl);
                    cont++;

                }
            }
            String warn = "";
            if (cont < 2) {
                warn = " | " + titles + "";
            }
            logger.info("getPages() total pageRequest => " + cont + " " + warn);
            List<Page> pgs = this.pageRepository.findAllByTitles(tts, language);
            Set<String> present = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            Set<String> redirect = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            
            String type;
            cont = 0;
            for (Page pg : pgs) {
                if (pg.getTitle() != null) {
                    //present.add(pg.getTitle());

                    type = pg.getType(disambiguationsWords);
                    if (type != null) {
                        
                        if (!type.toLowerCase().equalsIgnoreCase("dis")) {
                            redirect.add(type);
                        }
                    } else {
                        pg.setLang(language);
                        pages.add(pg);

                        cont++;
                    }
                    total.remove(pg.getTitle());
                    //tts.remove(pg.getTitle().toLowerCase());
                }
            }
            logger.info("getPages() present in MySql => " + cont);
            //logger.info("getPages() remaning to MongoDb => "+total);
            //total.removeAll(present);
            cont = 0;
            Set<String> conv = new HashSet<>();
            for (String tt : total) {
                conv.add(tt.toLowerCase());
            }
            if (total.size() > 0) {
                if (isMongoOn) {
                    if (nosql == null) {
                        initNoSqlLanguage(language);
                    } else if (!nosql.getLanguage().equalsIgnoreCase(language)) {
                        nosql.closeConnection();
                        initNoSqlLanguage(language);
                    }
                    Map<String, Page> wikipediaMapPages = nosql.getWikipediaMapPages(conv); //(total);
                    for (Entry<String, Page> pe : wikipediaMapPages.entrySet()) {

                        Page p = pe.getValue();
                        if (p.getTitle() != null) {
                            type = p.getType(disambiguationsWords);
                            boolean isave = true;
                            if (type != null) {
                                if (p.getTitle().trim().toLowerCase().equalsIgnoreCase(type.trim().toLowerCase())) {
                                    isave = false;
                                }
                                if (!type.toLowerCase().equalsIgnoreCase("dis")) {
                                    redirect.add(type);
                                }
                            } else {
                                p.setLang(language);
                                pages.add(p);
                                cont++;
                            }
                           
                            if (saveIfNotExistsInDB && isave) {
                                p = saveInMySql(p);
                            }
                            total.remove(p.getTitle());
                            tts.remove(p.getTitle().toLowerCase());

                        }

                    }
                }

            }
            logger.info("getPages() present in NoSql => " + cont);
            total.removeAll(present);
            cont = 0;
            if (redirect.size() > 0) {
                Set<Page> pages1 = getPages(redirect, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                for (Page pg : pages1) {
                    if (pg.getTitle() != null) {
                        total.remove(pg.getTitle());
                    }
                }
            }
            int size = total.size();
            if (size > 1) {
                
                pages.addAll(getPagesFromWikipedia(total, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language));
            } else if (size > 0) {
                for (String tl : total) {
                    Page pageFromWikipedia = getPageFromWikipedia(tl, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
                    if (pageFromWikipedia != null) {
                        pages.add(pageFromWikipedia);
                    }
                }

            }
            logger.info("getPages() present in Wikipedia => " + cont);
            return pages;
        }
        return pages;
    }
    public Page getPageFromWikipedia(String titl, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language) {
        String trim = language.trim();
        String title=titl;
        logger.info("titleOriginal => "+titl);
        /*
        try {
            //title = new String(titl.getBytes("UTF-8"));
            //title=new String(titl.getBytes("ASCII"),"UTF-8");
            
            //title=URLEncoder.encode( titl, "ISO-8859-1");  
           logger.info("title => "+title);
        } catch (UnsupportedEncodingException ex) {
            Exceptions.printStackTrace(ex);
        }*/
        if(!trim.isEmpty()){
            
            wikipedia=new CustomWiki(trim+".wikipedia.org");
        }
        else{
            wikipedia=new CustomWiki();
        }
        logger.info("WikiPages from Domain => "+wikipedia.getDomain());
        Page page = null;
        boolean flg = false;
        try {
            String text = this.wikipedia.getPageText(title);
            // redirect :#REDIRECT [[Instruction set architecture]]

            page = new Page();
            page.setTitle(title);
            page.setText(text);
            page.setLang(language);
            String ptr = page.getType(disambiguationsWords);
            
            
            
            boolean isave=true;
                
                          
            
            
            if (ptr != null) {
                if (title.trim().toLowerCase().equalsIgnoreCase(ptr.trim().toLowerCase())) {
                    isave = false;
                }
                if(!ptr.equalsIgnoreCase("dis")){
                    
                    title = ptr;
                    flg = true;
                }
                
            }
            if (saveIfNotExistsInDB&&isave) {
                if (this.extraConfigs.getDBOrder(language) == 1 && isMongoOn) {
                    page=saveInNoSql(page);
                } else {
                    page=saveInMySql(page);
                }
                
            }
            
            if (flg) {
                return getPage(title, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language);
            }
        } catch (Exception e) {
            logger.warn("getPageFromWikipedia Error while retrieving text of page: " + title + " from wikipedia.");

        }

        return page;
    }

    public Set<Page> getPagesFromWikipedia(Set<String> titles, boolean isMongoOn, boolean saveIfNotExistsInDB, int maxLoopSize, String language) {
        String trim = language.trim();
        if(!trim.isEmpty()){
            wikipedia=new CustomWiki(trim+".wikipedia.org");            
        }
        else{
            wikipedia=new CustomWiki();
        }
        logger.info("WikiPages from Domain => "+wikipedia.getDomain());
        Page page = null;
        Set<Page> pages = new HashSet<>();
        int size = 0;
        Set<String> redirect = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        for (String title : titles) {
            try {
                String titl=title;
                //logger.info("titleOriginal => "+title);
                /*
                try {
                    //titl = new String(title.getBytes("UTF-8"));
                    //titl=new String(title.getBytes("ASCII"),"UTF-8");
                    titl=URLEncoder.encode( title, "ISO-8859-1");
                    logger.info("title => "+titl);
                } catch (UnsupportedEncodingException ex) {
                    Exceptions.printStackTrace(ex);
                }
*/
                String text = this.wikipedia.getPageText(titl);
                /*byte[] bytes = text.getBytes("UTF-8");
                text=new String(bytes, "UTF-8");*/
                // redirect :#REDIRECT [[Instruction set architecture]]
                
                //    else {
                page = new Page();
                page.setTitle(title);
                page.setText(text);
                page.setLang(language);
                String ptr = page.getType(disambiguationsWords);
                
                boolean isave=true;
                
                if (ptr != null) {
                    if (title.trim().toLowerCase().equalsIgnoreCase(ptr.trim().toLowerCase())) {
                        isave = false;
                    }
                    if(!ptr.toLowerCase().equalsIgnoreCase("dis")){
                        
                        title = ptr;
                        redirect.add(ptr);
                        size++;
                    }
                } 

                if (saveIfNotExistsInDB&&isave) {
                    if (this.extraConfigs.getDBOrder(language) == 1 && isMongoOn) {
                        page=saveInNoSql(page);
                    } else {
                        page=saveInMySql(page);
                    }
                }
                if(ptr==null){
                    pages.add(page);
               
                }
                //   }
            } catch (Exception e) {
                
                logger.warn("getPagesFromWikipedia Error while retrieving text of page: " + title + " from wikipedia.");

            }
        }
        if (size > 1) {
            try {
                pages.addAll(getPages(redirect, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language));
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        else if (size > 0) {
            try {
                for(String s:redirect){
                    pages.add(getPage(s, isMongoOn, saveIfNotExistsInDB, maxLoopSize, language));
                }
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return pages;
    }
    
    public Page saveInMySql(Page page) {
        try {
            page.setInsertDate(new Date());
            this.pageRepository.save(page);
        } catch (Exception e) {
            //logger.warn("saveMySql Error while saving page: " + page.getTitle() + " into database mysql.", e);
            Page byTitle = this.pageRepository.getByTitle(page.getTitle().toLowerCase(), page.getLang());
            String rod = byTitle.getType(disambiguationsWords);
            if (rod != null) {
                this.pageRepository.delete(byTitle);
                try {
                    page.setInsertDate(new Date());
                    this.pageRepository.save(page);
                } catch (Exception e1) {
                    logger.warn("saveMySql Error while saving page: " + page.getTitle() + " into database mysql. second time", e1);
                }
            }
        }
        return page;

    }
  
    public Page saveInNoSql(Page page) {
        Page p=page;
        try {
            page.setInsertDate(new Date());
            this.nosql.saveWikipediaPage(page);            
            return p;
        } catch (Exception e) {
            //logger.warn("saveNoSql Error while saving page: " + page.getTitle()+ " into database nosql.", e);
            Page byTitle = this.nosql.getWikipediaPage(page.getTitle().toLowerCase());
            String rod = byTitle.getType(disambiguationsWords);
            if (rod != null) {
                this.nosql.removePage(byTitle.getTitle().toLowerCase());
                try {
                    page.setInsertDate(new Date());
                    this.nosql.saveWikipediaPage(page);
                } catch (Exception e1) {
                    logger.warn("saveNoSql Error while saving page: " + page.getTitle() + " into database nosql. second time", e1);
                }
            }
                       
        }
        return p;


    }

    @Override
    public void updatePage(String title, String language, boolean isMongoOn) {
        
        Page pageFromWikipedia = getPageFromWikipedia(title, false, false, 5, language);   
        
        Page mysql = this.pageRepository.getByTitle(title.toLowerCase(), language);
        
        if (mysql != null) {
            mysql.setText(pageFromWikipedia.getText());
            this.pageRepository.save(mysql);
        }
        if (isMongoOn) {

            if (nosql == null) {
                initNoSqlLanguage(language);
            } else if (!nosql.getLanguage().equalsIgnoreCase(language)) {
                nosql.closeConnection();
                initNoSqlLanguage(language);
            }
            Page nosqll = this.nosql.getWikipediaPage(title.toLowerCase());

            if (nosqll != null) {
                nosqll.setText(pageFromWikipedia.getText());
                this.nosql.saveWikipediaPageText(nosqll.getTitle().toLowerCase(), pageFromWikipedia.getSerializedText());
            }
        }
    }

    @Override
    public void updatePages(Set<String> titles, String language, boolean isMongoOn) {
        Set<Page> pagesFromWikipedia = getPagesFromWikipedia(titles, false, false, 5, language);   
        
        List<String> ts= new LinkedList<>();
        Set<String> ss= new TreeSet<>();
        for(String ti:titles){
            ts.add(ti.toLowerCase());
            ss.add(ti.toLowerCase());
        }
        
        List<Page> mysqls = this.pageRepository.findAllByTitles(ts, language);
        
        if (mysqls != null) {
            for (Page mysql : mysqls) {
                for (Page pageFromWikipedia : pagesFromWikipedia) {                
                    if (mysql.getTitle().toLowerCase().equalsIgnoreCase(pageFromWikipedia.getTitle().toLowerCase())) {
                        mysql.setText(pageFromWikipedia.getText());
                        this.pageRepository.save(mysql);
                        break;
                    }
                }
            }
        }
        if (isMongoOn) {

            if (nosql == null) {
                initNoSqlLanguage(language);
            } else if (!nosql.getLanguage().equalsIgnoreCase(language)) {
                nosql.closeConnection();
                initNoSqlLanguage(language);
            }
           
            List<Page> nosqls = this.nosql.getWikipediaPages(ss);

            if (nosqls != null) {
                for (Page nosqll : nosqls) {
                    for (Page pageFromWikipedia : pagesFromWikipedia) {                    
                        if (nosqll.getTitle().toLowerCase().equalsIgnoreCase(pageFromWikipedia.getTitle().toLowerCase())) {
                            nosqll.setText(pageFromWikipedia.getText());
                            this.nosql.saveWikipediaPageText(nosqll.getTitle().toLowerCase(), pageFromWikipedia.getSerializedText());
                            break;
                        }
                    }
                }
            }
        }
    }
}

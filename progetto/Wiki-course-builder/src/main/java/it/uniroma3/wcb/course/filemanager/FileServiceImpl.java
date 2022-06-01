/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.filemanager;

import it.uniroma3.wcb.course.search.PageService;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.dao.CourseRepository;
import it.uniroma3.wcb.persistence.dao.CourseTopicRepository;
import it.uniroma3.wcb.persistence.dao.PageRepository;
import it.uniroma3.wcb.persistence.model.Course;
import it.uniroma3.wcb.persistence.model.CourseTopic;
import it.uniroma3.wcb.persistence.model.Page;
import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.security.RequestUserService;
import java.util.ArrayList;
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
 *
 * @author J-HaRd
 */
@Service("fileService")
public class FileServiceImpl implements FileService{

    
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseTopicRepository courseTopicRepository;
/*
    @Autowired
    private PageRepository pageRepository;
  */  
    @Autowired
    private PageService pageService;
    
    @Autowired
    private RequestUserService userDetailsService;
    
    private final ExtraConfig config=ExtraConfig.getIstance();
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public String createCoursePDF(Long courseId) {
        String ret="";
        try {
            Map<String, Page> pagesm=new TreeMap<>();
            User user = this.userDetailsService.getRequestUser();
            Course course = this.courseRepository.findOne(courseId);
            List<CourseTopic> topics = this.courseTopicRepository.getByCourseId(courseId);
            JSONObject coursej=new JSONObject();
            JSONObject metadata=new JSONObject();
            JSONArray topicsj = new JSONArray();
            String keyword="";
            for(CourseTopic t:topics){
                JSONObject topicj=new JSONObject();
                
                keyword+=" "+t.getQueryConstraints();
                JSONArray pagesj = t.getResultItems();
                topicj.put("title", t.getTopicName());
                topicj.put("lang", t.getLang());
                
                List<String> lp=new ArrayList<>();
                Set<String> sp=new HashSet<>();
                for (int x = 0; x < pagesj.length(); x++) {
                    JSONObject item = pagesj.getJSONObject(x);
                    if (item.has("title")) {
                        lp.add(item.getString("title"));      
                        sp.add(item.getString("title"));
                    }
                }
                pagesj=new JSONArray();
                Set<Page> pages = pageService.getPages(sp, config.isMongoDbCourseOn(t.getLang()), false, 5, t.getLang());//this.pageRepository.findAllByTitles(lp, t.getLang());
                for(Page p:pages){
                    JSONObject pa = new JSONObject();
                    pa.put("id", p.getTitle().toLowerCase());
                    pagesm.put(p.getTitle().toLowerCase(),p);
                    pagesj.put(pa);
                    
                }
                topicj.put("pages", pagesj);
                topicsj.put(topicj);
                        
            }
            
            
            coursej.put("title", course.getName());
            coursej.put("topics", topicsj);
            
            metadata.put("title", course.getName());
            metadata.put("subject", course.getDescription());
            metadata.put("keywords", keyword);
            metadata.put("author", user.getFirstName()+" "+user.getLastName());
            metadata.put("creator", user.getFirstName()+" "+user.getLastName());
            metadata.put("levelDocument", "Course");
                    
            String pathFile = "docs/pdf/"+user.getFirstName()+"-"+user.getLastName()+"_"+course.getId()+"_"+"ALL"+""+".pdf";
            coursej.put("pathFile", pathFile);
            coursej.put("metadata", metadata);
            PdfMakeriText pdfmaker= new PdfMakeriText();
            pdfmaker.createPdf(pathFile, coursej, pagesm);
            ret=coursej.toString();
            
        } catch (Exception ex) {
            logger.error("createCoursePDF course service => "+ex.getMessage());
        }
        return ret;
    }

    @Override
    public String createTopicPDF(Long topicId) {
        String ret="";
        try {
            
            User user = this.userDetailsService.getRequestUser();
            Map<String, Page> pagesm=new TreeMap<>();
            CourseTopic topic = this.courseTopicRepository.findOne(topicId);
            Course course = this.courseRepository.findOne(topic.getCourseId());
            JSONObject coursej=new JSONObject();
            JSONObject metadata=new JSONObject();
            JSONArray topicsj = new JSONArray();
            String keyword="";
            
            JSONObject topicj = new JSONObject();

            keyword += " " + topic.getQueryConstraints();
            JSONArray pagesj = topic.getResultItems();
            topicj.put("title", topic.getTopicName());
            topicj.put("lang", topic.getLang());
            if(topic.getPosition()!=null){
                topicj.put("position", topic.getPosition()+1);
            }
            else{
                topicj.put("position", -1);
            }

            List<String> lp=new ArrayList<>();
                Set<String> sp=new HashSet<>();
                for (int x = 0; x < pagesj.length(); x++) {
                    JSONObject item = pagesj.getJSONObject(x);
                    if (item.has("title")) {
                        lp.add(item.getString("title"));      
                        sp.add(item.getString("title"));
                    }
                }
                pagesj=new JSONArray();
                Set<Page> pages = pageService.getPages(sp, config.isMongoDbCourseOn(topic.getLang()), false, 5, topic.getLang());//this.pageRepository.findAllByTitles(lp, t.getLang());
            for (Page p : pages) {
                JSONObject pa = new JSONObject();
                pa.put("id", p.getTitle().toLowerCase());
                pagesm.put(p.getTitle().toLowerCase(),p);
                pagesj.put(pa);
            }
            topicj.put("pages", pagesj);
            //topicj.put("sectionsToSkip", new JSONArray(this.appConfig.getSectionsToSkip(topic.getLang())));
            topicsj.put(topicj);


            
            coursej.put("title", course.getName());
            coursej.put("topics", topicsj);
            
            metadata.put("title", course.getName());
            metadata.put("subject", course.getDescription());
            metadata.put("keywords", keyword);
            metadata.put("author", user.getFirstName()+" "+user.getLastName());
            metadata.put("creator", user.getFirstName()+" "+user.getLastName());
            metadata.put("levelDocument", "Topic");
            
            String pathFile = "docs/pdf/"+user.getFirstName()+"-"+user.getLastName()+"_"+course.getId()+"_"+topic.getId()+""+".pdf";
            coursej.put("pathFile", pathFile);
            coursej.put("metadata", metadata);
            PdfMakeriText pdfmaker= new PdfMakeriText();
            pdfmaker.createPdf(pathFile, coursej, pagesm);
            ret=coursej.toString();
            
        } catch (Exception ex) {
            logger.error("createCoursePDF course service => "+ex.getMessage());
        }
        return ret;
    }

    
    
    
    
    @Override
    public String createCourseTxt(Long courseId, boolean clean) {
        String ret="";
        try {
            Map<String, Page> pagesm=new TreeMap<>();
            User user = this.userDetailsService.getRequestUser();
            Course course = this.courseRepository.findOne(courseId);
            List<CourseTopic> topics = this.courseTopicRepository.getByCourseId(courseId);
            JSONObject coursej=new JSONObject();
            JSONObject metadata=new JSONObject();
            JSONArray topicsj = new JSONArray();
            String keyword="";
            for(CourseTopic t:topics){
                JSONObject topicj=new JSONObject();
                
                keyword+=" "+t.getQueryConstraints();
                JSONArray pagesj = t.getResultItems();
                topicj.put("title", t.getTopicName());
                topicj.put("lang", t.getLang());
                
                List<String> lp=new ArrayList<>();
                Set<String> sp=new HashSet<>();
                for (int x = 0; x < pagesj.length(); x++) {
                    JSONObject item = pagesj.getJSONObject(x);
                    if (item.has("title")) {
                        lp.add(item.getString("title"));      
                        sp.add(item.getString("title"));
                    }
                }
                pagesj=new JSONArray();
                Set<Page> pages = pageService.getPages(sp, config.isMongoDbCourseOn(t.getLang()), false, 5, t.getLang());//this.pageRepository.findAllByTitles(lp, t.getLang());
                for(Page p:pages){
                    JSONObject pa = new JSONObject();
                    pa.put("id", p.getTitle().toLowerCase());
                    pagesm.put(p.getTitle().toLowerCase(),p);
                    pagesj.put(pa);
                    
                }
                topicj.put("pages", pagesj);
                topicsj.put(topicj);
                        
            }
            
            
            coursej.put("title", course.getName());
            coursej.put("topics", topicsj);
            
            metadata.put("title", course.getName());
            metadata.put("subject", course.getDescription());
            metadata.put("keywords", keyword);
            metadata.put("author", user.getFirstName()+" "+user.getLastName());
            metadata.put("creator", user.getFirstName()+" "+user.getLastName());
            metadata.put("levelDocument", "Course");
                    
            String add="RAW";
            if(clean){
                add="CLEAN";
            }
            
            String pathFile = "docs/txt/"+user.getFirstName()+"-"+user.getLastName()+"_"+course.getId()+"_"+"ALL"+""+"_"+add+".txt";
            coursej.put("pathFile", pathFile);
            coursej.put("metadata", metadata);
            TxtMaker txtmaker= new TxtMaker();
            txtmaker.createTxt(pathFile, coursej, pagesm, clean);
            ret=coursej.toString();
            
        } catch (Exception ex) {
            logger.error("createCoursePDF course service => "+ex.getMessage());
        }
        return ret;
    }

    @Override
    public String createTopicTxt(Long topicId, boolean clean) {
        String ret="";
        try {
            
            User user = this.userDetailsService.getRequestUser();
            Map<String, Page> pagesm=new TreeMap<>();
            CourseTopic topic = this.courseTopicRepository.findOne(topicId);
            Course course = this.courseRepository.findOne(topic.getCourseId());
            JSONObject coursej=new JSONObject();
            JSONObject metadata=new JSONObject();
            JSONArray topicsj = new JSONArray();
            String keyword="";
            
            JSONObject topicj = new JSONObject();

            keyword += " " + topic.getQueryConstraints();
            JSONArray pagesj = topic.getResultItems();
            topicj.put("title", topic.getTopicName());
            topicj.put("lang", topic.getLang());
            if(topic.getPosition()!=null){
                topicj.put("position", topic.getPosition()+1);
            }
            else{
                topicj.put("position", -1);
            }

            List<String> lp=new ArrayList<>();
                Set<String> sp=new HashSet<>();
                for (int x = 0; x < pagesj.length(); x++) {
                    JSONObject item = pagesj.getJSONObject(x);
                    if (item.has("title")) {
                        lp.add(item.getString("title"));      
                        sp.add(item.getString("title"));
                    }
                }
                pagesj=new JSONArray();
                Set<Page> pages = pageService.getPages(sp, config.isMongoDbCourseOn(topic.getLang()), false, 5, topic.getLang());//this.pageRepository.findAllByTitles(lp, t.getLang());
            for (Page p : pages) {
                JSONObject pa = new JSONObject();
                pa.put("id", p.getTitle().toLowerCase());
                pagesm.put(p.getTitle().toLowerCase(),p);
                pagesj.put(pa);
            }
            topicj.put("pages", pagesj);
            //topicj.put("sectionsToSkip", new JSONArray(this.appConfig.getSectionsToSkip(topic.getLang())));
            topicsj.put(topicj);


            
            coursej.put("title", course.getName());
            coursej.put("topics", topicsj);
            
            metadata.put("title", course.getName());
            metadata.put("subject", course.getDescription());
            metadata.put("keywords", keyword);
            metadata.put("author", user.getFirstName()+" "+user.getLastName());
            metadata.put("creator", user.getFirstName()+" "+user.getLastName());
            metadata.put("levelDocument", "Topic");
            
            String add="RAW";
            if(clean){
                add="CLEAN";
            }
            
            String pathFile = "docs/txt/"+user.getFirstName()+"-"+user.getLastName()+"_"+course.getId()+"_"+topic.getId()+""+"_"+add+".txt";
            coursej.put("pathFile", pathFile);
            coursej.put("metadata", metadata);
            TxtMaker txtmaker= new TxtMaker();
            txtmaker.createTxt(pathFile, coursej, pagesm, clean);
            ret=coursej.toString();
            
        } catch (Exception ex) {
            logger.error("createCoursePDF course service => "+ex.getMessage());
        }
        return ret;
    }
    

    
}

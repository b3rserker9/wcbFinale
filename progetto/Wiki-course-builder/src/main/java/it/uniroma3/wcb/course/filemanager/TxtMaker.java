/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.filemanager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.model.Page;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author J-HaRd
 */
public class TxtMaker {
    
    private static final Logger LOG = Logger.getLogger(PdfMakeriText.class.getName());
    
    private final ExtraConfig appConfig = ExtraConfig.getIstance();
    
    public void createTxt(String pathFile, JSONObject obj, Map<String,Page> pages, boolean clean){
        try {
            String doc="";
            addFirstPage(doc,obj);
            int cid=1;
            try {
                JSONArray topicsj = obj.getJSONArray("topics");
                for(int x=0;x<topicsj.length();x++){
                    
                    JSONObject topicj = topicsj.getJSONObject(x);
                    String lang=topicj.getString("lang");
                    if(lang==null){
                        lang="en";
                    }
                    //LOG.info("lang => "+lang);
                    //TreeSet<String> sectionsToSkip = this.appConfig.getSectionsToSkip(lang);
                    //String topic = topicj.getString("title");
                    JSONArray pagesj = topicj.getJSONArray("pages");
                    //LOG.info("create pdf level 1 PAGES   "+pages.size()+" KEYS "+pages.keySet());
                    //LOG.info("create pdf level 1 pagej "+pagesj.toString());
                    
                    
                    //    LOG.info("sectionsToSkip => "+sectionsToSkip);
                    for (int y = 0; y < pagesj.length(); y++) {
                        JSONObject pagej = pagesj.getJSONObject(y);
                        Page get = pages.get(pagej.getString("id"));
                        if(clean){
                            doc+="\n\n\n\n\n\n\n\n\n\n"+get.getOnlyText();
                        }
                        else{
                            doc+="\n\n\n\n\n\n\n\n\n\n"+get.getText();
                        }
                        cid++;
                        //break;
                    }
                }
            } catch (Exception e) {
                LOG.warning("create pdf level 1 exception "+e.toString());
                try {

                    JSONObject topicj = obj;
                    //String topic = topicj.getString("title");
                    String lang=topicj.getString("lang");
                    JSONArray pagesj = topicj.getJSONArray("pages");
                    for (int y = 0; y < pagesj.length(); y++) {
                        JSONObject pagej = pagesj.getJSONObject(y);
                        Page get = pages.get(pagej.getString("id"));
                        if(clean){
                            doc+="\n\n\n\n\n\n\n\n\n\n"+get.getOnlyText();
                        }
                        else{
                            doc+="\n\n\n\n\n\n\n\n\n\n"+get.getText();
                        }
                        cid++;
                    }

                } catch (Exception e1) {
                    LOG.warning("create pdf level 2 exception");
                    try {
                        Page get = pages.get(obj.getString("id"));
                        if(clean){
                            doc+="\n\n\n\n\n\n\n\n\n\n"+get.getOnlyText();
                        }
                        else{
                            doc+="\n\n\n\n\n\n\n\n\n\n"+get.getText();
                        }
                        cid++;


                    } catch (Exception e2) {
                        LOG.warning("create pdf level 3 exception");
                    }
                }
            }
            this.writeFile(pathFile, doc);
        } catch (Exception e) {
            LOG.warning("create pdf level 0 exception");
        }
        
    }
    private void writeFile(String pathFile, String content) throws Exception {
        String s =System.getProperty("user.dir");
        File file = new File(s+File.separator+pathFile);
        LOG.info("user-dir => "+file.getAbsolutePath());
        File filev = new File(s+File.separator+"docs");
        if(!filev.exists()||!filev.isDirectory()){
            /*file = new File(s+File.separator+".."+File.separator+".."+File.separator+pathFile);
            
            File filec = new File(s+File.separator+".."+File.separator+".."+File.separator+"docs"+File.separator+"txt");*/
            filev = new File(s);
            filev=filev.getParentFile();
            filev=filev.getParentFile();
            
            File filec = new File(filev.getAbsolutePath()+File.separator+"docs"+File.separator+"txt");
            LOG.info("filec => "+filec.getAbsolutePath());
            if(!filec.exists()||!filec.isDirectory()){
                filec.mkdir();
            }
            file=new File(filev.getAbsolutePath()+File.separator+pathFile);
        }
        else{
            File filec = new File(s+File.separator+"docs"+File.separator+"txt");
            if(!filec.exists()||!filec.isDirectory()){
                filec.mkdir();
            }
            
        }
         try (Writer writer = new BufferedWriter(new FileWriter(file))) {
            String contents = "The quick brown fox" + 
                System.getProperty("line.separator") + "jumps over the lazy dog.";

            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(destinationFile);
            fos.write(content.getBytes());

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                fos.close();
            } catch (Exception inn) {
                fos = null;
            }
        }
        */
    }
    
    
    
    private void addMetaData(Document document, JSONObject metadata) {
        document.addTitle(metadata.getString("title"));
        document.addSubject(metadata.getString("subject"));
        document.addKeywords(metadata.getString("keywords"));
        document.addAuthor(metadata.getString("author"));
        document.addCreator(metadata.getString("creator"));
    }

    private void addFirstPage(String document, JSONObject obj) throws DocumentException {
        document+="\n";
        // Lets write a big header
        String title = obj.getString("title");        
        JSONObject metadata = obj.getJSONObject("metadata");
        String ld = metadata.getString("levelDocument");
        String head="Course";
        if(ld.toLowerCase().equalsIgnoreCase("course")){
            /*
            preface.add(new Paragraph(head, new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL, BaseColor.BLACK)));
            addEmptyLine(preface, 2);
            */
            //Paragraph paragraph = new Paragraph(title.toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLUE));
            document+=title.toUpperCase()+"\n\n";
            
            //Paragraph paragraph1 = new Paragraph(""+metadata.getString("subject"), new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLUE));
            //paragraph1.setAlignment(Element.ALIGN_CENTER);
            document+=metadata.getString("subject");
            for(int x=0;x<24;x++){
                document+="\n";
            }
            
        }
        else if(ld.toLowerCase().equalsIgnoreCase("topic")){
            String add="";
            JSONArray topics = obj.getJSONArray("topics");
            
            String title1="";
            for(int x=0;x<topics.length();x++){
                JSONObject jsn = topics.getJSONObject(x);
                if(jsn.getLong("position")>0){
                    add=""+jsn.getLong("position");
                }
                title1=jsn.getString("title");
            }
            /*
            preface.add(new Paragraph(head, new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL, BaseColor.BLACK)));
            addEmptyLine(preface, 2);
            */
            document+=title.toUpperCase()+"\n\n";
            
            document+=metadata.getString("subject");
            for(int x=0;x<6;x++){
                document+="\n";
            }
            
            
            /*
            preface.add(new Paragraph(ld+" "+add, new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL, BaseColor.BLACK)));
            addEmptyLine(preface, 2);
            */
            
            document+=title1.toUpperCase();
            for(int x=0;x<12;x++){
                document+="\n";
            }
            
        }
        else if(ld.toLowerCase().equalsIgnoreCase("page")){
            /*
            head="Page";
            preface.add(new Paragraph(head, new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL, BaseColor.BLACK)));
            addEmptyLine(preface, 2);
            */
            document+=title.toUpperCase();
            for(int x=0;x<26;x++){
                document+="\n";
            }
        }
        // Will create: Report generated by: _name, _date
        
        document += "Author";
        for (int x = 0; x < 2; x++) {
            document += "\n";
        }
        //preface.add(new Paragraph(metadata.getString("author").toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.GREEN)));
        document += metadata.getString("author").toUpperCase();
        for (int x = 0; x < 12; x++) {
            document += "\n";
        }


        document += "Created by WikiCourseBuilder";
        for (int x = 0; x < 12; x++) {
            document += "\n";
        }
        
    }
    
}

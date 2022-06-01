/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.filemanager;

/**
 *
 * @author J-HaRd
 */
import java.io.FileOutputStream;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.tudarmstadt.ukp.wikipedia.parser.Content;
import de.tudarmstadt.ukp.wikipedia.parser.Link;
import de.tudarmstadt.ukp.wikipedia.parser.NestedList;
import de.tudarmstadt.ukp.wikipedia.parser.NestedListContainer;
import de.tudarmstadt.ukp.wikipedia.parser.ParsedPage;
import de.tudarmstadt.ukp.wikipedia.parser.Table;
import de.tudarmstadt.ukp.wikipedia.parser.TableElement;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParser;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParserFactory;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.model.Page;
import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;


public class PdfMakeriText {

    private static final Logger LOG = Logger.getLogger(PdfMakeriText.class.getName());
    
    private final ExtraConfig appConfig = ExtraConfig.getIstance();
    
    public PdfMakeriText() {
    }


    
    
    public void createPdf(String pathFile, JSONObject obj, Map<String,Page> pages) {
        try {
            //LOG.info("obj => "+obj.toString());
            Document document = new Document();
            String pf = pathFile;
            String s = System.getProperty("user.dir");
            File filev = new File(s + File.separator + "docs");
            if(!filev.exists()||!filev.isDirectory()){
                filev = new File(s);
                filev=filev.getParentFile();
                filev=filev.getParentFile();

                File filec = new File(filev.getAbsolutePath()+File.separator+"docs"+File.separator+"pdf");
                if(!filec.exists()||!filec.isDirectory()){
                    filec.mkdir();
                }
                pf=filev.getAbsolutePath()+File.separator+pathFile;
            } else {
                File filec = new File(s + File.separator + "docs" + File.separator + "pdf");
                if(!filec.exists()||!filec.isDirectory()){
                    filec.mkdir();
                }

            }
            
            PdfWriter.getInstance(document, new FileOutputStream(pf));
            document.open();
            addMetaData(document, obj.getJSONObject("metadata"));
            addFirstPage(document, obj);
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
                    TreeSet<String> sectionsToSkip = this.appConfig.getSectionsToSkip(lang);
                    //String topic = topicj.getString("title");
                    JSONArray pagesj = topicj.getJSONArray("pages");
                    //LOG.info("create pdf level 1 PAGES   "+pages.size()+" KEYS "+pages.keySet());
                    //LOG.info("create pdf level 1 pagej "+pagesj.toString());
                    
                    
                    //    LOG.info("sectionsToSkip => "+sectionsToSkip);
                    for (int y = 0; y < pagesj.length(); y++) {
                        JSONObject pagej = pagesj.getJSONObject(y);
                        
                        addPage(document, pages.get(pagej.getString("id")), cid, sectionsToSkip);
                        
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
                        //LOG.info("pagej => "+pagej.toString());
                        addPage(document, pages.get(pagej.getString("id")), cid, this.appConfig.getSectionsToSkip(lang));
                        cid++;
                    }

                } catch (Exception e1) {
                    LOG.warning("create pdf level 2 exception");
                    try {
                        Page get = pages.get(obj.getString("id"));
                        addPage(document, get, cid, this.appConfig.getSectionsToSkip(get.getLang()));
                        cid++;


                    } catch (Exception e2) {
                        LOG.warning("create pdf level 3 exception");
                    }
                }
            }
            document.close();
        } catch (Exception e) {
            LOG.warning("create pdf level 0 exception");
        }
    }
    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    private void addMetaData(Document document, JSONObject metadata) {
        document.addTitle(metadata.getString("title"));
        document.addSubject(metadata.getString("subject"));
        document.addKeywords(metadata.getString("keywords"));
        document.addAuthor(metadata.getString("author"));
        document.addCreator(metadata.getString("creator"));
    }

    private void addFirstPage(Document document, JSONObject obj) throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
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
            Paragraph paragraph = new Paragraph(title.toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLUE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph);
            
            addEmptyLine(preface, 2);
            Paragraph paragraph1 = new Paragraph(""+metadata.getString("subject"), new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLUE));
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph1);
            addEmptyLine(preface, 24);
            
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
            Paragraph paragraph = new Paragraph(title.toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLUE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph);
            addEmptyLine(preface, 2);
            
            Paragraph paragraph1 = new Paragraph(""+metadata.getString("subject"), new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.BLUE));
            paragraph1.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph1);
            addEmptyLine(preface, 6);
            
            
            /*
            preface.add(new Paragraph(ld+" "+add, new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL, BaseColor.BLACK)));
            addEmptyLine(preface, 2);
            */
            
            preface.add(new Paragraph(title1.toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLUE)));
            addEmptyLine(preface, 12);
            
        }
        else if(ld.toLowerCase().equalsIgnoreCase("page")){
            /*
            head="Page";
            preface.add(new Paragraph(head, new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL, BaseColor.BLACK)));
            addEmptyLine(preface, 2);
            */
            Paragraph paragraph = new Paragraph(title.toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD, BaseColor.BLUE));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            preface.add(paragraph);
            addEmptyLine(preface, 26);
        }
        // Will create: Report generated by: _name, _date
        
        preface.add(new Paragraph("Author ",new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL, BaseColor.BLACK)));
        addEmptyLine(preface, 2);
        preface.add(new Paragraph(metadata.getString("author").toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.GREEN)));

        
        
        addEmptyLine(preface, 12);

        preface.add(new Paragraph("Created by WikiCourseBuilder", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED)));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private void addPage(Document document, Page obj, int numChapter, Set<String> sectionsToSkip) throws DocumentException {
        if (obj != null) {
            Anchor anchor = new Anchor(obj.getTitle(), new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, BaseColor.DARK_GRAY));
            anchor.setName(obj.getTitle());
            WikipediaParserAddon wpa=new WikipediaParserAddon();
            // Second parameter is the number of the chapter
            Paragraph paragraphMain = new Paragraph(anchor);
            paragraphMain.setAlignment(Element.ALIGN_CENTER);
            Chapter catPart = new Chapter(paragraphMain, numChapter);

            MediaWikiParserFactory pf = new MediaWikiParserFactory();
            MediaWikiParser parser = pf.createParser();

            //contains all outgoing page links
            Set<String> linkedPages = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

            //wiki page parser
            ParsedPage pp = parser.parse(obj.getText());

            
            
            
            //retrieving all page sections
            java.util.List<de.tudarmstadt.ukp.wikipedia.parser.Section> sections = pp.getSections();

            //report for debug reasons

            for (de.tudarmstadt.ukp.wikipedia.parser.Section section : sections) {
                String sectionTitle = section.getTitle();

                
               
                
                
                if (sectionTitle != null && sectionsToSkip.contains(sectionTitle.toLowerCase())) {
                    
                } else {

                    if (sectionTitle == null) {
                        sectionTitle = "";
                    }

                    Paragraph subPara = new Paragraph(sectionTitle, new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.GRAY));
                    subPara.setAlignment(Element.ALIGN_CENTER);
                    Section subCatPart = catPart.addSection(subPara);

                    int parCount = 1;
                    int parsN = section.getParagraphs().size();
                    for (de.tudarmstadt.ukp.wikipedia.parser.Paragraph sectionPar : section.getParagraphs()) {
                        
                        for (Link parLink : sectionPar.getLinks(Link.type.INTERNAL)) { //paragraph internal links
                            String linkTarget = parLink.getTarget().replace("_", " ");
                            String[] split = linkTarget.split("\\|");
                            linkTarget = split[0].trim();
                            if (!linkTarget.isEmpty()) {
                                linkedPages.add(linkTarget);
                            }

                        }
                        String text = sectionPar.getText()+"";
                                                
                        String cleanText=wpa.cleanText(text);
                        
                        if (!cleanText.isEmpty()) {
                            /*
                            Paragraph paragraph = new Paragraph(sectionPar.);
                            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                            subCatPart.add(paragraph);
                            */
                            Paragraph paragraph = new Paragraph(cleanText);
                            paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
                            
                            subCatPart.add(paragraph);

                            int offset = 2;
                            if (parCount == parsN) {
                                offset = 4;
                            }
                            addEmptyLine(paragraph, offset);
                        }
                        parCount++;

                    }
                    addEmptyLine(subPara, 2);
                    /*
            sectionLimit--;
            if (sectionLimit <= 0) {
                break;
            }
                     */
                
                
                    /*
                    java.util.List<Content> contentList = section.getContentList();
                    for(Content co:contentList){
                        LOG.info("********************    Content text  "+co.getText());
                    }
                    java.util.List<DefinitionList> definitionLists = section.getDefinitionLists();
                    for(DefinitionList co: definitionLists){
                        LOG.info("********************    DefinitionLists  "+co.getText());
                    }
                     */
                    java.util.List<NestedListContainer> nestedLists = section.getNestedLists();

                    for (NestedListContainer co : nestedLists) {
                        //LOG.info("********************    nestedLists  " + co.getText());
                        java.util.List<String> lista = new LinkedList<>();
                        
                        for (int j = 0; j < co.getNestedLists().size(); j++) {
                            NestedList nestedList = co.getNestedList(j);
                            lista.add(wpa.cleanText(nestedList.getText()));
                        }
                        createList(subCatPart, lista);
                        subCatPart.add(new Paragraph(" "));
                        subCatPart.add(new Paragraph(" "));
                    }
                    java.util.List<Table> tables = section.getTables();
                    for (Table co : tables) {
                        TableElement tableElement = co.getTableElement(0);
                        LOG.info("********************    table  " + co.getText());
                      
                        LOG.info("********************    tableElement  " + tableElement.getText());

                        for (Content con : tableElement.getContentList()) {
                           
                                LOG.info("********************    Content text  " + con.getText());
                            
                        }
                    }


                }
                
                
            }
            // add a list

            // add a table
            // createTable(subCatPart);
            // now add all this to the document
            document.add(catPart);
        }

    }

    private void createTable(Section subCatPart, Map<String, java.util.List<String>> matrix)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);
        for (Entry<String, java.util.List<String>> en : matrix.entrySet()) {
            PdfPCell c1 = new PdfPCell(new Phrase(en.getKey()));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }

        table.setHeaderRows(1);

        for (Entry<String, java.util.List<String>> en : matrix.entrySet()) {
            for (String s1 : en.getValue()) {
                table.addCell(s1);
            }
        }

        subCatPart.add(table);

    }

    private void createList(Section subCatPart, java.util.List<String> lista) {
        List list = new List(List.ORDERED);
        lista.forEach((s) -> {
            list.add(new ListItem(s));
        });
        subCatPart.add(list);
    }

    private void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}
package it.uniroma3.wcb.persistence.model;

import de.tudarmstadt.ukp.wikipedia.parser.Link;
import de.tudarmstadt.ukp.wikipedia.parser.Paragraph;
import de.tudarmstadt.ukp.wikipedia.parser.ParsedPage;
import de.tudarmstadt.ukp.wikipedia.parser.Section;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParser;
import de.tudarmstadt.ukp.wikipedia.parser.mediawiki.MediaWikiParserFactory;
import it.uniroma3.wcb.course.filemanager.WikipediaParserAddon;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Index;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openide.util.Exceptions;

/**
 *
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 *
 */
@Entity
@Table(name = "page", indexes = {
    @Index(name = "indx_title_lang", columnList = "title,lang", unique = true)})
public class Page {

    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @Column(name = "title", nullable = false)
    private String title;

    //
    //@Transient
    @Column(name = "lang", nullable = false)
    private String lang;
    
    private Date insertDate;

    @Lob
    private byte[] serializedText; //byte[] for exclude encoding problems

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = PageTeachingStyle.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "teachingStyleId", nullable = true, updatable = true, insertable = true)
    private PageTeachingStyle teachingStyle;


    public Page() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    
    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public byte[] getSerializedText() {
        return serializedText;
    }

    public void setSerializedText(byte[] serializedText) {
        this.serializedText = serializedText;
    }

    public PageTeachingStyle getTeachingStyle() {
        return teachingStyle;
    }

    public void setTeachingStyle(PageTeachingStyle teachingStyle) {
        this.teachingStyle = teachingStyle;
    }

    @Transient
    public String getOnlyText() {
        if (serializedText != null) {
            String s = null;
            /*
            try {
                s = getTextFromJSON(-1, -1);
            } catch (Exception ex) {
                Exceptions.printStackTrace(ex);
            }
            */
            if (s != null) {
                return s;
            } else {
                //return new String(this.serializedText);
                MediaWikiParserFactory pf = new MediaWikiParserFactory();
                MediaWikiParser parser = pf.createParser();

                

                //wiki page parser
                ParsedPage pp = parser.parse(this.getText());
                String text = pp.getText();
                WikipediaParserAddon wpa= new WikipediaParserAddon();
                return wpa.cleanText(text);
            }
        } else {
            return null;
        }
    }

    @Transient
    public String getText() {
        if (serializedText != null) {
            return new String(this.serializedText);
        } else {
            return null;
        }
    }

    @Transient
    public void setText(String text) {
        this.serializedText = text.getBytes();
    }

    /*
    @Transient
    public String getClenedText() {
        if (serializedText != null) {
            return new String(this.serializedText);
        } else {
            return null;
        }
    }
*/
    

    @Transient
    public JSONObject getBasicJSON() {
        if(lang==null){
            lang="en";
        }
        
        JSONObject j = new JSONObject();
        j.put("visibleUrl", lang+".wikipedia.org");
        j.put("title", title);
        String st = title.replaceAll(" ", "_");
        j.put("titleNoFormatting", st);
        j.put("unescapedUrl", "http://"+lang+".wikipedia.org/wiki/" + st);
        try {
            j.put("url", "http://"+lang+".wikipedia.org/wiki/" + URLEncoder.encode(st, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            j.put("url", "http://"+lang+".wikipedia.org/wiki/" + st);
            Exceptions.printStackTrace(ex);
        }
        return j;
    }
    
    @Transient
    public String getTextFromJSON(int maxSectLink, int maxSectText) throws Exception {
        try {
            JSONObject o = new JSONObject(new String(this.serializedText));
            if (o != null) {

                String lks = "";
                String text = "";

                try {
                    this.title = o.getString("title");
                    JSONArray sects = o.getJSONArray("sections");
                    //JSONArray cats = o.getJSONArray("categories");
                    /*for(int x=0; x<cats.length(); x++){
                    this.categories.add(cats.getString(x));
                }*/

                    for (int x = 0; x < sects.length(); x++) {
                        JSONObject sect = sects.getJSONObject(x);
                        boolean flagT = x < maxSectText || maxSectText < 0;
                        boolean flagL = x < maxSectLink || maxSectLink < 0;
                        try {
                            JSONArray sents = sect.getJSONArray("sentences");

                            for (int y = 0; y < sents.length(); y++) {
                                try {
                                    JSONObject sent = sents.getJSONObject(y);
                                    //System.out.println(sent.toString());

                                    if (flagT) {
                                        try {
                                            String text1 = sent.getString("text");
                                            /*if(x==0){
                                            firstLength+=text1.length();
                                        }*/
                                            text += " " + text1;
                                        } catch (Exception e) {
                                        }

                                    }
                                    if (flagL) {
                                        try {
                                            JSONArray linksl = sent.getJSONArray("links");

                                            for (int z = 0; z < linksl.length(); z++) {
                                                try {
                                                    JSONObject link = linksl.getJSONObject(z);
                                                    String pagest = link.getString("page");
                                                    //this.links.add(pagest);

                                                    String keyy = "0";
                                                    try {
                                                        keyy = sect.getString("title");
                                                    } catch (Exception e) {
                                                    }

                                                    lks += "    [[" + pagest + "]] ";

                                                } catch (Exception e) {
                                                }
                                            }
                                        } catch (Exception e) {
                                        }

                                    }

                                } catch (Exception e) {
                                    //continue;
                                }
                            }
                        } catch (Exception e) {
                        }
                        if (!(flagT || flagL)) {
                            break;
                        }
                    }
                    //this.setText(text+"    "+lks+" ");

                    //this.serializedText=this.getText().getBytes();
                    //SET annotation and Nouns
                } catch (Exception e) {

                }
                return text;
            }
        } catch (Exception e) {
            return null;
        }
        return null;

    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : (title+" - "+lang).hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Page other = (Page) obj;
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        }
        else if (lang == null) {
            if (other.lang != null) {
                return false;
            }
        }
        else if (!title.equalsIgnoreCase(other.title)||!lang.equalsIgnoreCase(other.lang)) {
            return false;
        }        
        return true;
    }

    @Transient
    public String getType(List<String> dist) {
        String rit = null;
        
        boolean dis=false;
        String diss="";
        int xx=0;
        for(String d:dist){
            if(xx>0){
                diss+="|";
            }
            diss+=d;
            xx++;
        }
        //System.out.println("Verify => "+this.getText());
        //System.out.println("Verify => "+diss);
        Pattern p3 = Pattern.compile("\\{{2}"+diss);
        Matcher matcher = p3.matcher(this.getText().toLowerCase());
        
        if(matcher.find()){
            
            dis= true;
            //break;
        }
    
        
        /*
        try {
            JSONObject page = new JSONObject(this.getText());
            if (page.getString("type").trim().equalsIgnoreCase("redirect")) {
                rit = page.getString("redirect");
            } else if (page.getString("type").trim().equalsIgnoreCase("page")) {
                rit = "page mongo";
            }
            else if (page.getString("type").trim().toLowerCase().contains("dis")) {
                rit = "dis";
            }
            else {
                rit = "page mongono";
            }
        } catch (Exception e) {
        */
        if(dis){
                rit="dis";
            }
        else if (this.getText().toUpperCase().startsWith("#REDIRECT")) {
                String replace = this.getText().replace("#REDIRECT [[", "");
                String[] split = replace.split("]]");
                String[] split1 = split[0].split("#");
                String[] split2 = split1[0].split("|");
                //title = split[0];
                //return getPage(title, isMongoOn, saveIfNotExistsInDB);

                rit = split2[0];
            } 
            
            
            /* 
            else if (this.getText().substring(2, 6).toLowerCase().contains("dis")) {
                rit="dis";
            }
            else if (this.getText().substring(2, 6).toLowerCase().contains("dis")) {
                rit="dis";
            }
            else if (this.getText().substring(2, 10).toLowerCase().contains("wiktio")) {
                rit="dis";
            }
            else if (this.title.toLowerCase().contains("(disambiguation)")) {
                rit="dis";
            }
            */
            else {
                rit = "page wiki";
            }
            
        //}
        if (rit.startsWith("page")) {
            rit = null;
        }

        return rit;
    }
    
    
    
    
    
    //
    
    @Transient
    public Set<String> getPageLinks(String language, int sectionLimit, TreeSet<String> sectionsToSkip) throws Exception {
        Set<String> pageLinks = null;//getPageLinksNoSql(language, sectionLimit, sectionsToSkip);
        
        if (pageLinks == null) {
            pageLinks = getPageLinksMySql(language, sectionLimit, sectionsToSkip);
            int s = pageLinks.size();
            //logger.info("Links of MySql Page: " + this.getTitle() + " - " + s);
        }
        //logger.info("Links of Page: " + this.getTitle() + " => " + pageLinks);
        return pageLinks;
    }
    
    @Transient
    private Set<String> getPageLinksMySql(String language, int sectionLimit, TreeSet<String> sectionsToSkip) throws Exception {

        MediaWikiParserFactory pf = new MediaWikiParserFactory();
        MediaWikiParser parser = pf.createParser();

        //contains all outgoing page links
        Set<String> linkedPages = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        //wiki page parser
        ParsedPage pp = parser.parse(this.getText());

        //retrieving all page sections
        List<Section> sections = pp.getSections();

        //report for debug reasons
        StringBuffer report = new StringBuffer("");
        report.append("****** Page title: " + this.getTitle() + " ******\n\n");

        for (Section section : sections) {
            String sectionTitle = section.getTitle();

            if (sectionTitle != null && sectionsToSkip.contains(sectionTitle.toLowerCase())) {
                report.append("- Skipped Section name: " + (sectionTitle != null ? sectionTitle : "") + "\n\n");
                
            } else {
                report.append("- Section name: " + (sectionTitle != null ? sectionTitle : "") + "\n");

                int parCount = 1;

                for (Paragraph sectionPar : section.getParagraphs()) {
                    report.append("\tParagraph : " + parCount + " links:\n");

                    for (Link parLink : sectionPar.getLinks(Link.type.INTERNAL)) { //paragraph internal links
                        String linkTarget = parLink.getTarget().replace("_", " ");
                        String[] split = linkTarget.split("\\|");
                        linkTarget = split[0].trim();
                        if (!linkTarget.isEmpty()) {
                            linkedPages.add(linkTarget);
                            report.append("\t\t" + linkTarget + "\n");
                        }

                    }
                    parCount++;

                    report.append("\n");
                }

                sectionLimit--;
                if (sectionLimit <= 0) {
                    break;
                }
            }
        }

        report.append(report.toString());

        return linkedPages;

    }

    @Transient
    private Set<String> getPageLinksNoSql(String language, int sectionLimit, TreeSet<String> sectionsToSkip) throws Exception {

        //contains all outgoing page links
        Set<String> linkedPages = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        //report for debug reasons
        StringBuffer report = new StringBuffer("");
        report.append("****** Page title: " + this.getTitle() + " ******\n\n");

        try {
            JSONObject o = new JSONObject(this.getText());

            JSONArray sects = o.getJSONArray("sections");
            //JSONArray cats = o.getJSONArray("categories");
            /*for(int x=0; x<cats.length(); x++){
                    this.categories.add(cats.getString(x));
                }*/

            for (int x = 0; x < sects.length(); x++) {
                JSONObject sect = sects.getJSONObject(x);
                String sectionTitle = "0";
                try {
                    sectionTitle = sect.getString("title");
                } catch (Exception e) {
                }
                if (sectionTitle != null && sectionsToSkip.contains(sectionTitle.toLowerCase())) {
                    report.append("- Skipped Section name: " + (sectionTitle != null ? sectionTitle : "") + "\n\n");
                    
                } else {
                    try {
                        JSONArray sents = sect.getJSONArray("sentences");

                        for (int y = 0; y < sents.length(); y++) {
                            try {
                                JSONObject sent = sents.getJSONObject(y);
                                //System.out.println(sent.toString());

                                try {
                                    JSONArray linksl = sent.getJSONArray("links");

                                    for (int z = 0; z < linksl.length(); z++) {
                                        try {
                                            JSONObject link = linksl.getJSONObject(z);
                                            String pagestt = link.getString("page");
                                            String[] split = pagestt.split("\\|");
                                            String pagest = split[0].trim();
                                            if (!pagest.isEmpty()) {
                                                linkedPages.add(pagest);
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                } catch (Exception e) {
                                }

                            } catch (Exception e) {
                                //continue;
                            }
                        }
                    } catch (Exception e) {
                    }

                    sectionLimit--;
                    if (sectionLimit <= 0) {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            return null;
        }
        report.append(report.toString());

        return linkedPages;
    }
    
    public List<String> getCategories(){
        List<String> l= new ArrayList<>();
        
        MediaWikiParserFactory pf = new MediaWikiParserFactory();
        MediaWikiParser parser = pf.createParser();

        //wiki page parser
        ParsedPage pp = parser.parse(this.getText());

        List<Link> categories = pp.getCategories();
        
        for(Link li:categories){
            l.add(li.getText());
        }
        
        return l;
    }
}

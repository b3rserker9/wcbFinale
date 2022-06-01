package it.uniroma3.wcb.nospring.configs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

/**
 *
 * @author Andrea Giard J-Hard
 *
 */
public class ExtraConfig {

    private static ExtraConfig extraConfigs;

    private final Properties env;

    public static ExtraConfig getIstance() {
        if (extraConfigs == null) {
            extraConfigs = new ExtraConfig();
        }
        return extraConfigs;

    }

    public ExtraConfig() {
        env = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("configs"+File.separator+"persistencemongodb.properties");
            env.load(input);
            input = new FileInputStream("configs"+File.separator+"appconfigs.properties");
            env.load(input);
            input = new FileInputStream("configs"+File.separator+"wiki.properties");
            env.load(input);

        } catch (IOException ex) {

            try {

                input = new FileInputStream(".."+File.separator+".."+File.separator+"configs"+File.separator+"persistencemongodb.properties");
                env.load(input);
                input = new FileInputStream(".."+File.separator+".."+File.separator+"configs"+File.separator+"appconfigs.properties");
                env.load(input);
                input = new FileInputStream(".."+File.separator+".."+File.separator+"configs"+File.separator+"wiki.properties");
                env.load(input);
            } catch (IOException ex1) {
                ex1.printStackTrace();
            }

            //ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getNumberSectionLinks() {
        Integer skipSize = Integer.parseInt(env.getProperty("number.sections.links"));
        return skipSize;
    }

    public int getNumberSectionBuildText() {
        Integer skipSize = Integer.parseInt(env.getProperty("number.sections.build.text"));
        return skipSize;
    }

    public int getNumberSectionSearchText() {
        Integer skipSize = Integer.parseInt(env.getProperty("number.sections.search.text"));
        return skipSize;
    }

    public int getLevelGroupQueryBuild() {
        int lev = 1;
        try {
            String trim = env.getProperty("level.group.query.build").trim();
            lev = Integer.parseInt(trim);
        } catch (Exception e) {

        }
        return lev;

    }
    public int getNumberGroupQuerySearch() {
        int lev = 1;
        try {
            String trim = env.getProperty("number.group.query.search").trim();
            lev = Integer.parseInt(trim);
        } catch (Exception e) {

        }
        /*
        if(lev<1){
            lev=1;
        }
        */
        return lev;

    }
    public int getNumberGroupQueryCommunity() {
        int lev = 1;
        try {
            String trim = env.getProperty("number.group.query.community").trim();
            lev = Integer.parseInt(trim);
        } catch (Exception e) {

        }
        /*
        if(lev<1){
            lev=1;
        }*/
        return lev;

    }
    public int wikiDefaultMaxResult() {
        int lev = 1;
        try {
            String trim = env.getProperty("wiki.default.max.results").trim();
            lev = Integer.parseInt(trim);
        } catch (Exception e) {

        }
        return lev;

    }
    public boolean isMongoDbBuildOn(String language) {
        //return this.env.getProperty("mongo.build.on." + language).trim().equalsIgnoreCase("true");
        String property = env.getProperty("mongo.build.on." + language);
        
        if (property == null) {
            property = "false";
        }
        
        return property.equalsIgnoreCase("true");
    }
    public boolean isMongoDbCourseOn(String language) {
        //return this.env.getProperty("mongo.build.on." + language).trim().equalsIgnoreCase("true");
        String property = env.getProperty("mongo.course.on." + language);
        
        if (property == null) {
            property = "false";
        }
        
        return property.equalsIgnoreCase("true");
    }
    public boolean isMongoDbSearchOn(String language) {
        //return this.env.getProperty("mongo.search.on." + language).trim().equalsIgnoreCase("true");
        String property=null;// = env.getProperty("mongo.search.on." + language);
        try{
            property = env.getProperty("mongo.search.on." + language);
        }
        catch(Exception e){}
        
        if (property == null) {
            property = "false";
        }
        return property.equalsIgnoreCase("true");
    }

    public String getMongoURL(String language) {
        String s = null;
        try{
            s = env.getProperty("mongo.url." + language);
        }
        catch(Exception e){}
        
        if (s == null) {
            s = "";
        }

        return s;
    }

    public String getMongoCollectionDataPage(String language) {
        
        String s = null;
        try{
            s = env.getProperty("mongo.collection.data.page." + language);
        }
        catch(Exception e){}
        if (s == null) {
            try {
                s = env.getProperty("mongo.collection.data.page.en");
            } catch (Exception e) {
            }
        }
        if (s == null) {
            s = "";
        }
        return s;
    }

    public String getMongoDB(String language) {
        String s = null;
        try{
            s = env.getProperty("mongo.db." + language);
        }
        catch(Exception e){}
        if (s == null) {
            try {
                s = env.getProperty("mongo.db.en");
            } catch (Exception e) {
            }
        }
        if (s == null) {
            s = "";
        }
        return s;
    }

    public String getMongoUser(String language) {
        String s = env.getProperty("mongo.user." + language);
        if (s == null) {
            try {
                s = env.getProperty("mongo.user.en");
            } catch (Exception e) {
            }
        }
        if (s == null) {
            s = "";
        }

        return s;
    }

    public String getMongoPassword(String language) {
        String s = env.getProperty("mongo.password." + language);
        if (s == null) {
            try {
                s = env.getProperty("mongo.password.en");
            } catch (Exception e) {
            }
        }
        if (s == null) {
            s = "";
        }

        return s;
    }

    public boolean isMongoAuthenticationActive(String language) {
        //String property = env.getProperty("mongo.authentication.active." + language);
        String property=null;// = env.getProperty("mongo.authentication.active." + language);
        try{
            property = env.getProperty("mongo.authentication.active." + language);
        }
        catch(Exception e){}
        if (property == null) {
            property = "false";
        }
        return property.equalsIgnoreCase("true");
    }

    public int getDBOrder(String language) {
        
        String s = null;
        try{
            s = env.getProperty("data.base.order." + language);
        }
        catch(Exception e){}
        
        if (s == null) {
            s = "0";
        }
        int n=0;
        try{
            n=Integer.parseInt(s);
        }
        catch(Exception e){
        }
        return n;
    }
    
    
    public int pluginPageLinkMode() {
        String s = null;
        try{
            s = env.getProperty("plugin.page.link.mode");
        }
        catch(Exception e){}
        
        if (s == null) {
            s = "0";
        }
        int n=0;
        try{
            n=Integer.parseInt(s);
        }
        catch(Exception e){
        }
        return n;
    }

    public String PluginPageLinkURL() {
        String s = env.getProperty("plugin.page.link.url");
        return s;
    }
    
    public TreeSet<String> getSectionsToSkip(String language) {
        TreeSet<String> sectionsToSkip = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        try {
            String prop1 = env.getProperty("wiki.excluded.sections."+language);
           
            String[] split = prop1.split("\\;");
            for(String s:split){
                sectionsToSkip.add(s.trim().toLowerCase());
            }
            //CollectionUtils.addAll(sectionsToSkip, prop1.split(";"));
        } catch (Exception e) {
            //System.out.println("Error sectionsToSkip "+language+"=> "+e.toString());
        }
        return sectionsToSkip;
    }

    public HashSet<String> getStopWords(String language) {
        HashSet<String> sectionsToSkip = new HashSet<>();
        try {
            String prop1 = env.getProperty("stop.word." + language);
            String[] split = prop1.split("\\;");
            for(String s:split){
                sectionsToSkip.add(s.trim().toLowerCase());
            }
            // CollectionUtils.addAll(sectionsToSkip, prop1.split(";"));
        } catch (Exception e) {

        }

        return sectionsToSkip;
    }
    
    public List<String> getDisambiguationsWords(String language) {
        List<String> sectionsToSkip = new ArrayList<>();
        try {
            String prop1 = env.getProperty("disambiguations.word." + language);
            String[] split = prop1.split("\\;");
            for(String s:split){
                sectionsToSkip.add(s.trim().toLowerCase());
            }
            // CollectionUtils.addAll(sectionsToSkip, prop1.split(";"));
        } catch (Exception e) {

        }

        return sectionsToSkip;
    }
    
    public int getTextTypeRank() {
        
        String s = null;
        try{
            s = env.getProperty("text.type.rank");
        }
        catch(Exception e){}
        
        if (s == null) {
            s = "0";
        }
        int n=0;
        try{
            n=Integer.parseInt(s);
        }
        catch(Exception e){
        }
        return n;
    }
    
    public int getStemmerType() {
        
        String s = null;
        try{
            s = env.getProperty("stemmer.type");
        }
        catch(Exception e){}
        
        if (s == null) {
            s = "0";
        }
        int n=0;
        try{
            n=Integer.parseInt(s);
        }
        catch(Exception e){
        }
        return n;
    }
    
    public boolean isPageLinkDoubleLink() {
        //String property = env.getProperty("mongo.authentication.active." + language);
        String property=null;// = env.getProperty("mongo.authentication.active." + language);
        try{
            property = env.getProperty("plugin.page.link.double.link.on");
        }
        catch(Exception e){}
        if (property == null) {
            property = "false";
        }
        return property.equalsIgnoreCase("true");
    }
    
    public boolean isPageLinkReverseLink() {
        //String property = env.getProperty("mongo.authentication.active." + language);
        String property=null;// = env.getProperty("mongo.authentication.active." + language);
        try{
            property = env.getProperty("plugin.page.link.reverse.link");
        }
        catch(Exception e){}
        if (property == null) {
            property = "false";
        }
        return property.equalsIgnoreCase("true");
    }
    
    //data.base.exclude.second
    
    public boolean isExcludeSecondDb(String lang) {
        //String property = env.getProperty("mongo.authentication.active." + language);
        String property=null;// = env.getProperty("mongo.authentication.active." + language);
        try{
            property = env.getProperty("data.base.exclude.second."+lang);
        }
        catch(Exception e){}
        if (property == null) {
            property = "false";
        }
        return property.equalsIgnoreCase("true");
    }

}

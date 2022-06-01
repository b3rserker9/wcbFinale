/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.persistence.dao.nosql;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.model.Page;
import it.uniroma3.wcb.persistence.model.PageTeachingStyle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.bson.Document;
import org.bson.types.Binary;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author J-HaRd
 */
public class MongoDbDaoImp implements NoSqlDbDao {

    private ExtraConfig configs;

    private String MONGODB_URL;
    private String MONGODB_DB;
    private String MONGODB_COLLECTION_DATA;

    private MongoClientURI URI;
    private MongoClient CLIENT;

    private String language;
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    public MongoDbDaoImp(String language) {
        this.language = language;
        configs = ExtraConfig.getIstance();
        MONGODB_URL = configs.getMongoURL(language);
        MONGODB_DB = configs.getMongoDB(language);
        MONGODB_COLLECTION_DATA = configs.getMongoCollectionDataPage(language);

        URI = new MongoClientURI(MONGODB_URL);
        CLIENT = new MongoClient(URI);

    }

    public void close() {
        CLIENT.close();
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public List<Page> getWikipediaPages(Set<String> titles) {
        List<Page> lp=new LinkedList<>();
        try {
            /*
            String patt="";
            for(String s:titles){
                patt+="(^(?i)"+s+"$)|";
                
            }
            
            Pattern pattern = Pattern.compile(patt);
             */
            JSONArray jo;
            
            MongoDatabase db = CLIENT.getDatabase(MONGODB_DB);
            BasicDBObject query = new BasicDBObject();

            query.put("_id", new BasicDBObject("$in", titles));
            //query.put("_id", pattern);
            /*
            Collation.Builder c = Collation.builder();
            c.locale("en");
            
            c.collationStrength(CollationStrength.SECONDARY);
            Collation build = c.build();
            
             */
            MongoCollection<Document> cursor = db.getCollection(MONGODB_COLLECTION_DATA);
            //FindIterable<Document> find = cursor.find(query).collation(build);
            FindIterable<Document> find = cursor.find(query);
            long x = 0;
            jo = new JSONArray();
            //List<DBObject> lobj= new ArrayList<>();
            
            for (Document d : find) {//while
                Page p = new Page();
                //System.out.println("MONGODB getJSONData: "+d.toJson());
                //jo.put(d.getString("_id") , new JSONObject(d.toJson()) );
                if (d != null) {
                    Binary bin = d.get("serializedText", org.bson.types.Binary.class);
                    p.setSerializedText(bin.getData());

                    d.remove("serializedText");

                    this.setFromMongoJSON(p, new JSONObject(d.toJson()));
                    lp.add(p);
                }

            }


            
        } catch (Exception e) {
            logger.error("MongoDB get: NULL Exception");
            //return null;
        }
        return lp;
    }

    @Override
    public Map<String,Page> getWikipediaMapPages(Set<String> titles) {
        Map<String, Page> mp=new TreeMap<>();
        try {
            /*
            String patt="";
            for(String s:titles){
                patt+="(^(?i)"+s+"$)|";
               
            }
            Pattern pattern = Pattern.compile(patt);
             */
            //logger.info("lista di titoli => "+titles);
            JSONObject jo;
            
            MongoDatabase db = CLIENT.getDatabase(MONGODB_DB);
            BasicDBObject query = new BasicDBObject();

            query.put("_id", new BasicDBObject("$in", titles));
            //query.put("_id", pattern);

            MongoCollection<Document> cursor = db.getCollection(MONGODB_COLLECTION_DATA);
            //FindIterable<Document> find = cursor.find(query).collation(build);
            FindIterable<Document> find = cursor.find(query);
            long x = 0;
            jo = new JSONObject();
            //List<DBObject> lobj= new ArrayList<>();
            
            for (Document d : find) {//while
                Page p = new Page();
                //System.out.println("MONGODB getJSONData: "+d.toJson());
                //jo.put(d.getString("_id") , new JSONObject(d.toJson()) );
                if (d != null) {

                    Binary bin = d.get("serializedText", org.bson.types.Binary.class);
                    p.setSerializedText(bin.getData());

                    d.remove("serializedText");

                    this.setFromMongoJSON(p, new JSONObject(d.toJson()));

                    mp.put(p.getTitle().toLowerCase(), p);
                    x++;
                }

            }


            //logger.info("numero pagine => "+x);
            
        } catch (Exception e) {
            logger.error("MongoDB get: NULL Exception "+e.toString());
            //return null;
        }
        return mp;
    }

    @Override
    public Page getWikipediaPage(String title) {

        try {
            //System.out.println("------------");
            //System.out.println("------------"+configs.getMongoURL());
            //MongoClientURI URI = new MongoClientURI(MONGODB_URL);
            JSONObject jo;
            Page p=new Page();
            //MongoDatabase db = CLIENT.getDatabase(configs.getMongoDB());
            //try (MongoClient CLIENT = new MongoClient(URI)) {
            //MongoDatabase db = CLIENT.getDatabase(configs.getMongoDB());
            MongoDatabase db = CLIENT.getDatabase(MONGODB_DB);
            BasicDBObject query = new BasicDBObject();
            //query.put("_id", "/^"+title+"$/i");

            //Pattern pattern = Pattern.compile(patt);
            query.put("_id", title);//Pattern.compile("(^(?i)"+title+"$)"));
            //Pattern compile = Pattern.compile(title, Pattern.CASE_INSENSITIVE);compile.pattern();
            /*
            Collation.Builder c = Collation.builder();
            c.locale("en");
            
            c.collationStrength(CollationStrength.SECONDARY);
            Collation build = c.build();
             */

            MongoCollection<Document> cursor = db.getCollection(MONGODB_COLLECTION_DATA);
            //FindIterable<Document> find = cursor.find(query).collation(build);
            FindIterable<Document> find = cursor.find(query);
            jo = null;
            //List<DBObject> lobj= new ArrayList<>();
            
            for (Document d : find) {//while

                Binary bin = d.get("serializedText", org.bson.types.Binary.class);
                p.setSerializedText(bin.getData());

                d.remove("serializedText");

                this.setFromMongoJSON(p, new JSONObject(d.toJson()));
            }

            

            return p;
        } catch (Exception e) {
            logger.error("MongoDB get: NULL Exception");
            return null;
        }
    }

    @Override
    public void saveWikipediaPage(Page page) {
        try {
            JSONObject toMongoJSON = toMongoJSON(page);
            MongoDatabase db = CLIENT.getDatabase(MONGODB_DB);
            BasicDBObject query = new BasicDBObject();
            String title = toMongoJSON.getString("title");
            query.put("_id", title.toLowerCase());

            //BasicDBObject newDocument = BasicDBObject.parse(toMongoJSON.toString());//new BasicDBObject(o.toString());
            MongoCollection<Document> cursor = db.getCollection(MONGODB_COLLECTION_DATA);
            //cursor.updateOne(query, newDocument);
            Document doc = Document.parse(toMongoJSON.toString());
            doc.put("serializedText", page.getSerializedText());
            cursor.insertOne(doc);
        } catch (Exception e) {
            logger.error("save mongo page error : "+e.toString());
        }

    }

    @Override
    public void saveWikipediaPageTS(String title, PageTeachingStyle ts) {
        try {
            JSONObject tso=new JSONObject();
            if(ts!=null){
                tso=ts.toJSONObject();
            }
            MongoDatabase db = CLIENT.getDatabase(MONGODB_DB);
            Document doc = Document.parse(tso.toString());
            BasicDBObject newDocument = new BasicDBObject();
            newDocument.append("$set", new BasicDBObject().append("teachingStyle", doc));

            BasicDBObject query = new BasicDBObject().append("_id", title);

            MongoCollection<Document> cursor = db.getCollection(MONGODB_COLLECTION_DATA);
            cursor.updateOne(query, newDocument);
        } catch (Exception e) {
            logger.error("save mongo page ts error : "+e.toString());
        }

    }
    
    
    public Page setFromMongoJSON(Page p,JSONObject o) {
        //Page p=null;
        if (o != null&&p!=null) {

            try {
                //p=new Page();
                p.setTitle(o.getString("title"));
                p.setLang(o.getString("lang"));
                DateFormat fd=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS z");
                String da = o.getString("insertDate");
                p.setInsertDate(fd.parse(da));
                //this.setInsertDate(());
                try {
                    JSONObject tso = o.getJSONObject("teachingStyle");
                    if(tso!=null){                        
                        PageTeachingStyle pageTeachingStyle = new PageTeachingStyle();
                        pageTeachingStyle.fromJSONObject(tso);
                        p.setTeachingStyle(pageTeachingStyle);
                    }
                } catch (Exception e) {}                
            } catch (Exception e) {}

        }
        return p;
    }
    
    public JSONObject toMongoJSON(Page p) {
        JSONObject js = null;
        js=new JSONObject();
        js.put("_id", p.getTitle().toLowerCase());
        js.put("title", p.getTitle());
        js.put("lang", p.getLang());
        DateFormat fd=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss,SSS z");
        if(p.getInsertDate()==null){
            p.setInsertDate(new Date());
        }
        js.put("insertDate", fd.format(p.getInsertDate()));
        
        if(p.getTeachingStyle()!=null){
            js.put("teachingStyle", p.getTeachingStyle().toJSONObject());
        }
        
        return js;
    }

    @Override
    public void saveWikipediaPageText(String title, byte[] text) {
        try {
            JSONObject tso=new JSONObject();
            
            MongoDatabase db = CLIENT.getDatabase(MONGODB_DB);
            
            BasicDBObject newDocument = new BasicDBObject();
            Document doc = Document.parse(newDocument.toJson());
            doc.append("serializedText", text);
           // newDocument.append("$set", new BasicDBObject().append("serializedText", doc));
            newDocument.append("$set", doc);

            BasicDBObject query = new BasicDBObject().append("_id", title);

            MongoCollection<Document> cursor = db.getCollection(MONGODB_COLLECTION_DATA);
            cursor.updateOne(query, newDocument);
        } catch (Exception e) {
            logger.error("save mongo page ts error : "+e.toString());
        }
    }

    @Override
    public void removePage(String title) {
        try {
            MongoDatabase db = CLIENT.getDatabase(MONGODB_DB);
            BasicDBObject query = new BasicDBObject();
            query.put("_id", title.toLowerCase());

            //BasicDBObject newDocument = BasicDBObject.parse(toMongoJSON.toString());//new BasicDBObject(o.toString());
            MongoCollection<Document> cursor = db.getCollection(MONGODB_COLLECTION_DATA);
            //cursor.updateOne(query, newDocument);
            
            cursor.deleteOne(query);
        } catch (Exception e) {
            logger.error("save mongo page error : "+e.toString());
        }
    }

    @Override
    public void closeConnection() {
        this.CLIENT.close();
    }
    
}

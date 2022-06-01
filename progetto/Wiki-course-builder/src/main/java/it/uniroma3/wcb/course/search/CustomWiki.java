package it.uniroma3.wcb.course.search;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.json.JSONArray;
import org.json.JSONObject;
import org.wikipedia.Wiki;

/**
 * Overrides some org.wikipedia.Wiki functions.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 *
 */
public class CustomWiki extends Wiki {

	private static final long serialVersionUID = -4254471479892646063L;
	private int defaultMaxResults = 50;
	
	private static int wikiMaxResultsPerPage = 50;
	
	public CustomWiki() {
		super();
	}

	public CustomWiki(String domain) {
		super(domain);
	}
        
	public CustomWiki(String domain, String scriptPath) {
		super(domain, scriptPath);
	}
	
	public JSONObject limitedSearch(String search, int... namespaces)throws Exception {
		return limitedSearch(defaultMaxResults, search, namespaces);
	}
	
	public JSONObject limitedSearch(int limit, String search, int... namespaces) throws Exception {
		
		if(limit<=0)
			throw new IOException("Invalid limit value: accepting values greater than 0");
		
		// default to main namespace
		if (namespaces.length == 0)
			namespaces = new int[] { MAIN_NAMESPACE };
		
		StringBuilder urlFirstPart = new StringBuilder(query);
		urlFirstPart.append("list=search&srwhat=text&srprop=snippet%7Csectionsnippet%7CwordCount%7Cscore&srlimit=");
		
		StringBuilder urlSecondPart = new StringBuilder("&srsearch=");
		urlSecondPart.append(URLEncoder.encode(search, "UTF-8"));
		constructNamespaceString(urlSecondPart, "sr", namespaces);
		urlSecondPart.append("&sroffset=");
		

		boolean done = false;

		JSONArray results = new JSONArray();
		String totalResults = "";
		
		int pagePointer = 0;
		int retrievedItems = 0;
		int skippedResults = 0;
		Set<String> itemTitles = new HashSet<>(); // for duplicate check
		
		while (!done) {
			int actualQueryLimit = limit - retrievedItems;
			if(actualQueryLimit > wikiMaxResultsPerPage){
				actualQueryLimit = wikiMaxResultsPerPage;
			}
			else{
				if(actualQueryLimit<0)
					actualQueryLimit = 0;
				done = true;
			}
			
			String line = fetch(urlFirstPart.toString() + actualQueryLimit + urlSecondPart.toString() + (pagePointer * wikiMaxResultsPerPage), "search");
			pagePointer ++;

			// if this is the last page of results then there is no sroffset
			// parameter
			if (!line.contains("sroffset=\"")){
				done = true;
			}
			
			if ("".equals(totalResults) && line.contains("searchinfo totalhits=\"")){
				totalResults = parseAttribute(line, "searchinfo totalhits", 0);
			}

			// xml form: <p ns="0" title="Main Page" snippet="Blah blah blah"
			// sectiontitle="Section"/>
			for (int x = line.indexOf("<p "); x > 0; x = line.indexOf("<p ", ++x)) {
				
				
				String title = parseAttribute(line, "title", x);
				if(title!=null && !"".equals(title) && !itemTitles.contains(title.toLowerCase()) && namespace(title)>=0){
					itemTitles.add(title.toLowerCase());
					
					JSONObject resultItem = getPageBaseObject(title);
					
					resultItem.put("searchConstraints", search);
					resultItem.put("wiki", retrievedItems+1);//ordine restituito dal motore di ricerca
					resultItem.put("GsearchResultClass", "WCBSearch");
					
					if (line.contains("sectionsnippet=\""))
						resultItem.put("sectionsnippet", parseAttribute(line, "sectionsnippet", x));
					else
						resultItem.put("sectionsnippet", "");

					resultItem.put("content", parseAttribute(line, "snippet", x));
					
					if (line.contains("wordCount=\""))
						resultItem.put("wordCount", parseAttribute(line, "wordCount", x));
					else
						resultItem.put("wordCount", "");
					
					results.put(resultItem);
					
					retrievedItems ++;
				}
				else{
					//rimuovo un risultato da totalResults
					skippedResults ++;
				}
				
			}
		}
		log(Level.INFO, "search", "Successfully searched for string \""
				+ search + "\" (" + results.length() + " items found)");
		
		JSONObject response = new JSONObject();
		try{
			response.put("totalResults", (Integer.parseInt(totalResults)-skippedResults));	
		}catch(Exception e){
			response.put("totalResults", 0);
		}
		
		response.put("data", results);
		
		return response;
	}
	
	public JSONObject getPageBaseObject(String title) throws Exception{
		JSONObject object = new JSONObject();
		object.put("title", title.replace("_", " "));
		object.put("titleNoFormatting", title.replace(" ", "_"));
		object.put("url", getPageUrl(title, false));
		object.put("unescapedUrl", getPageUrl(title, true));
		object.put("visibleUrl", getDomain());
		
		return object;
	}
	
	private String parseAttribute(String xml, String attribute, int index){
        // let's hope the JVM always inlines this
        if (xml.contains(attribute + "=\""))
        {
            int a = xml.indexOf(attribute + "=\"", index) + attribute.length() + 2;
            int b = xml.indexOf('\"', a);
            return decode(xml.substring(a, b));
        }
        else
            return null;
    }

	public int getDefaultMaxResults() {
		return defaultMaxResults;
	}

	public void setDefaultMaxResults(int defaultMaxResults) {
		this.defaultMaxResults = defaultMaxResults;
	}
	
	public String getPageUrl(String title, boolean unescape) throws Exception{
		String pageUrl = "http://"+getDomain()+"/wiki/"+URLEncoder.encode(title.replace(" ", "_"), "utf-8");
		if(unescape)
			return URLDecoder.decode(pageUrl, "utf-8");
		else
			return pageUrl;
	}
        
        
        @Override
        public String getPageText(String title) throws IOException
    {
        // pitfall check
        if (namespace(title) < 0)
            throw new UnsupportedOperationException("Cannot retrieve Special: or Media: pages!");

        // go for it
        String url = base + encode(title, true) + "&action=raw&redirect=";
        String temp = fetch(url, "getPageText");
        log(Level.INFO, "getPageText", "Successfully retrieved text of " + title);
        
        return temp;
    }
        
        private String encode(String text, boolean normalize) throws IOException
    {
        final String encoding = "UTF-8";
        if (normalize)
            text = normalize(text);
        return URLEncoder.encode(text, encoding);
    }

        
        
}

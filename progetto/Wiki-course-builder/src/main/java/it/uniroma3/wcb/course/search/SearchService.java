package it.uniroma3.wcb.course.search;

import it.uniroma3.wcb.course.ranking.RankType;

import org.json.JSONObject;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface SearchService {

	public JSONObject executeSearch(String queryString, String contextTerms, RankType orderType, String language) throws Exception;
	
	public JSONObject executeSearch(String queryString, String contextTerms, RankType orderType, int maxResults, String language) throws Exception;
}

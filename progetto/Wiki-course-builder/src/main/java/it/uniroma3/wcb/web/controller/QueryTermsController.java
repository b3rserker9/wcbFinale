package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.course.search.QueryTermsService;
import it.uniroma3.wcb.persistence.model.ContextTerms;
import it.uniroma3.wcb.persistence.model.Query;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Controller
public class QueryTermsController {

private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private QueryTermsService queryTermsService;
	
	@RequestMapping(value = "/search/getQuerySuggestions", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getQuerySuggestions(@RequestParam("queryString") final String queryString) throws Exception {
		try {
			
			JSONObject jsonResponse = new JSONObject();
			
			List<Query> queries = this.queryTermsService.getSuggestedQueries(queryString);
			JSONArray dataArray = new JSONArray();
			
			if(queries !=null){
				for(Query query : queries){
					dataArray.put(query.toJSON());
				}
			}
			
			jsonResponse.put("success", true);
			jsonResponse.put("total", dataArray.length());
			jsonResponse.put("data", dataArray);
			
			return jsonResponse.toString();
			
		} catch (Exception e) {
			logger.error("#Exception thrown getting query suggestions "
					+ e.getMessage());
			throw new Exception("Error while retrieving query suggestions");
		}
	}
	
	@RequestMapping(value = "/search/getContextTerms", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getContextTerms(@RequestParam("queryString") final String queryString) throws Exception {
		try {
			
			JSONObject jsonResponse = new JSONObject();
			
			List<ContextTerms> contextTerms = this.queryTermsService.getContextTerms(queryString);
			JSONArray dataArray = new JSONArray();
			
			if(contextTerms !=null){
				for(ContextTerms contextTerm : contextTerms){
					dataArray.put(contextTerm.toJSON());
				}
			}
			
			jsonResponse.put("success", true);
			jsonResponse.put("total", dataArray.length());
			jsonResponse.put("data", dataArray);
			
			return jsonResponse.toString();
			
		} catch (Exception e) {
			logger.error("#getQueryTerms exception thrown getting query terms "
					+ e.getMessage());
			throw new Exception("Error while retrieving context terms");
		}
	}
	
	@RequestMapping(value = "/search/getQueryAndTermsSuggestions", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getQueryAndTermsSuggestions(@RequestParam("queryString") final String queryString) throws Exception {
		try {
			
			JSONObject jsonResponse = new JSONObject();
			
			List<Query> queries = this.queryTermsService.getSuggestedQueries(queryString);
			
			JSONArray qArray = new JSONArray();
			if(queries !=null){
				for(Query query : queries){
					qArray.put(query.toJSON());
				}
			}
			
			List<ContextTerms> contextTerms = this.queryTermsService.getContextTerms(queryString);
			JSONArray ctArray = new JSONArray();
			
			if(contextTerms !=null){
				for(ContextTerms contextTerm : contextTerms){
					ctArray.put(contextTerm.toJSON());
				}
			}
			
			
			jsonResponse.put("success", true);
			jsonResponse.put("suggestedQueries", qArray);
			jsonResponse.put("contextTerms", ctArray);
			
			return jsonResponse.toString();
			
		} catch (Exception e) {
			logger.error("#Exception thrown getting query suggestions "
					+ e.getMessage());
			throw new Exception("Error while retrieving query suggestions");
		}
	}
}

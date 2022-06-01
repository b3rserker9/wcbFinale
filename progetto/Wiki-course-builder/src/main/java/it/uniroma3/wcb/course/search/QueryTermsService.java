package it.uniroma3.wcb.course.search;

import it.uniroma3.wcb.persistence.model.ContextTerms;
import it.uniroma3.wcb.persistence.model.Query;

import java.util.List;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface QueryTermsService {

	public List<Query> getSuggestedQueries(String queryString) throws Exception;
	
	public List<ContextTerms> getContextTerms(String queryString) throws Exception;
}

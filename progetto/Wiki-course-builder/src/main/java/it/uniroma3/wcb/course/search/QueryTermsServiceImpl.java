package it.uniroma3.wcb.course.search;

import it.uniroma3.wcb.persistence.dao.ContextTermsRepository;
import it.uniroma3.wcb.persistence.dao.QueryRepository;
import it.uniroma3.wcb.persistence.model.ContextTerms;
import it.uniroma3.wcb.persistence.model.Query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Service("queryTermsService")
public class QueryTermsServiceImpl implements QueryTermsService{

	@Autowired
	private QueryRepository queryRepository;
	
	@Autowired
	private ContextTermsRepository contextTermsRepository;
	
	public QueryTermsServiceImpl(){}
	
	@Override
	public List<Query> getSuggestedQueries(String queryString) throws Exception {
		return this.queryRepository.getSuggestedQueries(queryString);
	}

	@Override
	public List<ContextTerms> getContextTerms(String queryString) throws Exception {
		
		Query query = this.queryRepository.getByQueryString(queryString);
		if(query!=null){
			return this.contextTermsRepository.getByQueryId(query.getId());
		}
		else
			return null;
	}
}

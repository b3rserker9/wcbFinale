package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.ContextTerms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * ContextTerms JPA Repository.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 */
public interface ContextTermsRepository extends JpaRepository<ContextTerms, Long> {
	
	/**
	 * Returns ContextTerms linked to given query id.
	 * 
	 * @param queryId  query identifier
	 * @return  list of context terms
	 */
	public List<ContextTerms> getByQueryId(Long queryId);
	
	
	/**
	 * Returns ContextTerms that matches with given query id and terms.
	 * 
	 * @param queryId  query identified
	 * @param terms  terms
	 * @return ContextTerms object
	 */
	public ContextTerms getByQueryIdAndTerms (Long queryId, String terms);
}

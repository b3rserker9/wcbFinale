package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.Query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface QueryRepository extends JpaRepository<Query, Long> {

	public Query getByQueryString(String queryString);
	
	@org.springframework.data.jpa.repository.Query("select q from Query q where LOWER(q.queryString) like (%?1%)")
	public List<Query> getSuggestedQueries(String queryString);
	 
}

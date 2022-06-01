package it.uniroma3.wcb.persistence.dao.custom;

import it.uniroma3.wcb.persistence.model.Query;

import java.util.List;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface CustomQueryRepository {

	public List<Query> getSuggestedQueries(String queryString);
}

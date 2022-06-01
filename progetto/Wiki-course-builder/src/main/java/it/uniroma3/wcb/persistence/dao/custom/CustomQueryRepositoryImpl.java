package it.uniroma3.wcb.persistence.dao.custom;

import it.uniroma3.wcb.persistence.model.Query;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class CustomQueryRepositoryImpl implements CustomQueryRepository{

	@PersistenceContext
    private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Query> getSuggestedQueries(String queryString) {
		
		javax.persistence.Query q = em.createQuery("select * from Query where queryString like %"+queryString+"%");
		List<Query> queries = q.getResultList();
		
		return queries;
	}

}

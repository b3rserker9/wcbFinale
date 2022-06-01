package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.PageRelation;
import it.uniroma3.wcb.persistence.model.PageRelation.PageRelationType;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface PageRelationRepository extends JpaRepository<PageRelation, Long> {

	/**
	 * Returns all pages relations of given type.
	 * 
	 * @param type
	 * @return list of pages relations
	 */
	public List<PageRelation> getByType(PageRelationType type);
	
	/**
	 * Returns all relations among pages linked to a specific topic.
	 * 
	 * @param topicId - id of a course topic
	 * @return list of pages relations
	 */
	public List<PageRelation> getByTopicId(Long topicId);
	
	/**
	 * Returns link of given type existing among two pages.
	 * 
	 * @param titleOne
	 * @param titleTwo
	 * @param pageRelationType
	 * @return pages relation
	 */
	@org.springframework.data.jpa.repository.Query("select p from PageRelation p where ((p.pageOneTitle=?1 and p.pageTwoTitle=?2) or (p.pageOneTitle=?2 and p.pageTwoTitle=?1)) and p.type=?3")
	public PageRelation getByTitlesAndType(String titleOne, String titleTwo, PageRelationType pageRelationType);
}

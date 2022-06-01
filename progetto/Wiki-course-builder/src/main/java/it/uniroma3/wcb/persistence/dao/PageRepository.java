package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.Page;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 *
 */
public interface PageRepository extends JpaRepository<Page, Long> {
    @Query("SELECT p FROM Page p WHERE ((LOWER(p.title) LIKE (:title)) AND (p.lang LIKE :lang))")
    public Page getByTitle(@Param("title") String title, @Param("lang") String lang);
    
    /**
     *
     * @param titles of wikipediapage
     * @param lang
     * @return List of page
     */
    @Query("SELECT p FROM Page p WHERE ((LOWER(p.title) IN (:titles)) AND (p.lang LIKE :lang))")
    public List<Page> findAllByTitles(@Param("titles") List<String> titles, @Param("lang") String lang);

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.Feedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author J-HaRd
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    /**
     *     
     * @param type
     * @return List of page
     */
    @Query("SELECT p FROM Feedback p WHERE (p.type LIKE :type)) ORDER BY p.insertDate")
    public List<Feedback> findAllByType(@Param("type") String type);
    
     /**
     *     
     * @param type
     * @return List of page
     */
    @Query("SELECT p FROM Feedback p WHERE (p.type LIKE :type)) ORDER BY p.insertDate LIMIT 1")
    public Feedback findLastByType(@Param("type") String type);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.Statistic;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author J-HaRd
 */
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    /**
     *     
     * @param type
     * @return List of page
     */
    @Query("SELECT p FROM Statistic p WHERE (p.type LIKE :type)) ORDER BY p.insertDate")
    public List<Statistic> findAllByType(@Param("type") String type);
    
     /**
     *     
     * @param type
     * @return List of page
     */
    @Query("SELECT p FROM Statistic p WHERE (p.type LIKE :type)) ORDER BY p.insertDate LIMIT 1")
    public Statistic findLastByType(@Param("type") String type);
    
}

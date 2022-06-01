/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.statistic;

import it.uniroma3.wcb.persistence.model.Statistic;
import java.util.List;

/**
 *
 * @author J-HaRd
 */
public interface StatisticService {
    public Statistic getLastStatistic(String type);
    public List<Statistic> getStatistics(String type);
    public List<Statistic> getStatistics();
    public void saveStatistic(Statistic s);
    
    public int getNumCourse();
    public int getNumTopic();
}

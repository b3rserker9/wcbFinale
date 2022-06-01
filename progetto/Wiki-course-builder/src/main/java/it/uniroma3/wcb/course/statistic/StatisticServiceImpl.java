/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.statistic;

import it.uniroma3.wcb.persistence.dao.CourseRepository;
import it.uniroma3.wcb.persistence.dao.CourseTopicRepository;
import it.uniroma3.wcb.persistence.dao.StatisticRepository;
import it.uniroma3.wcb.persistence.model.Statistic;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author J-HaRd
 */
@Service("statisticService")
public class StatisticServiceImpl implements StatisticService{
    
    @Autowired
    private StatisticRepository statRepository;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private CourseTopicRepository courseTopicRepository;
    
    @Override
    public Statistic getLastStatistic(String type) {
        Statistic findLastByType = this.statRepository.findLastByType(type);
        return findLastByType;
    }

    
    @Override
    public List<Statistic> getStatistics(String type) {
        return this.statRepository.findAllByType(type);
    }

    @Override
    public List<Statistic> getStatistics() {
        return this.statRepository.findAll();
    }
    
    @Override
    public void saveStatistic(Statistic s) {
        s.setInsertDate(new Timestamp(System.currentTimeMillis()) );
        this.statRepository.save(s);
    }

    @Override
    public int getNumCourse() {
        return this.courseRepository.getNumCourse();
    }

    @Override
    public int getNumTopic() {
        return this.courseTopicRepository.getNumTopic();
    }

    
    
    
    
    
}

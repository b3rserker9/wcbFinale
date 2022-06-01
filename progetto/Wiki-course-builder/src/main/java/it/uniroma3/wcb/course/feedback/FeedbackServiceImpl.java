/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.feedback;

import it.uniroma3.wcb.persistence.dao.FeedbackRepository;
import it.uniroma3.wcb.persistence.model.Feedback;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author J-HaRd
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService{
        
    @Autowired
    private FeedbackRepository feedRepository;
    
    @Override
    public void saveFeedback(Feedback s) {
        s.setInsertDate(new Date());
        this.feedRepository.save(s);
    }
    
}

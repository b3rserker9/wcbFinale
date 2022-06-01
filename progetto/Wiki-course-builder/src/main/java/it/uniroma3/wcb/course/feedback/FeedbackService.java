/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.feedback;

import it.uniroma3.wcb.persistence.model.Feedback;

/**
 *
 * @author J-HaRd
 */
public interface FeedbackService {
    public void saveFeedback(Feedback s);
}

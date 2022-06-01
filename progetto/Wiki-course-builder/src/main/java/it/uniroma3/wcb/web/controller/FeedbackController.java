/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.course.feedback.FeedbackService;
import it.uniroma3.wcb.persistence.model.Feedback;
import it.uniroma3.wcb.persistence.model.Statistic;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author J-HaRd
 */
public class FeedbackController {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FeedbackService feedbackService;
    
    @RequestMapping(value = "/feeds/save", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String saveFeedback(final HttpServletRequest request, @RequestParam("feed") final String feed) throws Exception {
        try {
            JSONObject jo = new JSONObject(feed);
            Feedback s = new Feedback();
            s.setFromJSON(jo);
            feedbackService.saveFeedback(s);

            JSONObject response = new JSONObject();
            response.put("success", true);
            return response.toString();

        } catch (Exception e) {
            logger.error("#Error while saving feed " + e.getMessage());
            throw new Exception("Error while saving feed");
        }

    }
    
}

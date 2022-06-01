/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.course.CourseService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author J-HaRd
 */
@Controller
public class PageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CourseService courseService;
    
    
    @RequestMapping(value = "/course/pages/update", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String updatePagesCourse(@RequestParam("courseId") final String courseId) throws Exception {
        try {

            courseService.updatePagesCourse(new Long(courseId));

            
            
            JSONObject response = new JSONObject();
            response.put("success", true);

            return response.toString();

        } catch (Exception e) {
            logger.error("#updatePage exception thrown while updatePage course " + e.getMessage());
            throw new Exception("Error while updatePage course");
        }
    }

    @RequestMapping(value = "/course/topic/pages/update", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String updatePagesTopic(@RequestParam("topicId") final String topicId) throws Exception {
        try {

            courseService.updatePagesTopic(new Long(topicId));

            JSONObject response = new JSONObject();
            response.put("success", true);

            return response.toString();

        } catch (Exception e) {
            logger.error("#updatePage exception thrown while updatePage topic " + e.getMessage());
            throw new Exception("Error while updatePage topic");
        }
    }

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.course.statistic.StatisticService;
import it.uniroma3.wcb.persistence.model.Statistic;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
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
public class StatisticController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "/stats/save", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String saveStatistic(final HttpServletRequest request, @RequestParam("stat") final String stat) throws Exception {
        try {
            JSONObject jo = new JSONObject(stat);
            Statistic s = new Statistic();
            s.setFromJSON(jo);
            statisticService.saveStatistic(s);

            JSONObject response = new JSONObject();
            response.put("success", true);
            return response.toString();

        } catch (Exception e) {
            logger.error("#Error while saving statistic " + e.getMessage());
            throw new Exception("Error while saving statistic");
        }

    }

    @RequestMapping(value = "/stats/getLastByType", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String getlastStatistic(@RequestParam("type") final String type) throws Exception {
        try {

            Statistic ls = this.statisticService.getLastStatistic(type);

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("data", ls.getJSON());

            return response.toString();

        } catch (Exception e) {
            logger.error("#Error while retrieving last Stats " + e.getMessage());
            throw new Exception("Error while retrieving last Stats");
        }
    }

    @RequestMapping(value = "/stats/getAllByType", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String getStatistics(@RequestParam("type") final String type) throws Exception {
        try {

            List<Statistic> ls = this.statisticService.getStatistics(type);
            JSONArray array = new JSONArray();

            if (ls != null) {
                for (Statistic stat : ls) {
                    array.put(stat.getJSON());
                }
            }

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("total", array.length());
            response.put("data", array);

            return response.toString();

        } catch (Exception e) {
            logger.error("#Error while retrieving Stats by Type" + e.getMessage());
            throw new Exception("Error while retrieving Stats by Type");
        }
    }
    
    @RequestMapping(value = "/stats/getAll", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String getStatistics() throws Exception {
        try {

            List<Statistic> ls = this.statisticService.getStatistics();
            JSONArray array = new JSONArray();

            if (ls != null) {
                for (Statistic stat : ls) {
                    array.put(stat.getJSON());
                }
            }

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("total", array.length());
            response.put("data", array);

            return response.toString();

        } catch (Exception e) {
            logger.error("#Error while retrieving All Stats " + e.getMessage());
            throw new Exception("Error while retrieving All Stats");
        }
    }
    
    
    
    
    @RequestMapping(value = "/stats/getNumCourse", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String getNumCourse() throws Exception {
        try {

            int r= this.statisticService.getNumCourse();

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("data", r);

            return response.toString();

        } catch (Exception e) {
            logger.error("#Error while retrieving NumCourse " + e.getMessage());
            throw new Exception("Error while retrieving NumCourse");
        }
    }
    
    
    @RequestMapping(value = "/stats/getNumTopic", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String getNumTopic() throws Exception {
        try {

            int r= this.statisticService.getNumTopic();

            JSONObject response = new JSONObject();
            response.put("success", true);
            response.put("data", r);

            return response.toString();

        } catch (Exception e) {
            logger.error("#Error while retrieving NumTopic " + e.getMessage());
            throw new Exception("Error while retrieving NumTopic");
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.plugin.pagelink.PageLinkService;
import it.uniroma3.wcb.plugin.pagelink.PageLinkServiceImpl;
import java.util.Set;
import java.util.TreeSet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class PluginController {
    //http://localhost:8083/plugin/pageLink?title1=Java_(programming_language)&title2=Programming_language&lang=en

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private PageLinkService pageLinkService;

    @RequestMapping(value = "/plugin/pageLink", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String callPageLinkPlugIn(@RequestParam("title1") final String title1, @RequestParam("title2") final String title2, @RequestParam("lang") final String language) {
        try {
            String langu = "en";
            if (language != null) {

                if ( //lingue con più di 1.000.000 di pagine
                        language.equalsIgnoreCase("it")
                        || language.equalsIgnoreCase("nl")
                        || language.equalsIgnoreCase("de")
                        || language.equalsIgnoreCase("fr")
                        || language.equalsIgnoreCase("sv")
                        || language.equalsIgnoreCase("es")
                        || language.equalsIgnoreCase("ru")
                        || language.equalsIgnoreCase("pl")
                        || language.equalsIgnoreCase("vi")
                        || language.equalsIgnoreCase("war")
                        || language.equalsIgnoreCase("ceb")
                        || language.equalsIgnoreCase("zh")
                        || language.equalsIgnoreCase("ja")) {
                    langu = language;
                }

            }
            pageLinkService = new PageLinkServiceImpl();
            JSONObject jo = pageLinkService.callPageLinkService(title1, title2, langu);
            String s = jo.toString();
            logger.info("PluginPageLink return => " + s);

            return s;

        } catch (Exception e) {
            /*
			logger.error("#search exception thrown while executing search " + e.getMessage());
			throw new Exception("Error while executing search", e);
                    
             */
            logger.error("#search exception thrown while executing search " + e.getMessage());
            JSONObject jo1 = new JSONObject();
            jo1.put("result", -20);
            jo1.put("log", "PageLinkPlugin general Exception");
            jo1.put("time", "0");
            return jo1.toString();
        }
    }
    
    
     @RequestMapping(value = "/plugin/pageLinkAll", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String callPageLinkAllPlugIn(@RequestParam("titles") final String titles, @RequestParam("lang") final String language) {
        try {
            String langu = "en";
            if (language != null) {

                if ( //lingue con più di 1.000.000 di pagine
                        language.equalsIgnoreCase("it")
                        || language.equalsIgnoreCase("nl")
                        || language.equalsIgnoreCase("de")
                        || language.equalsIgnoreCase("fr")
                        || language.equalsIgnoreCase("sv")
                        || language.equalsIgnoreCase("es")
                        || language.equalsIgnoreCase("ru")
                        || language.equalsIgnoreCase("pl")
                        || language.equalsIgnoreCase("vi")
                        || language.equalsIgnoreCase("war")
                        || language.equalsIgnoreCase("ceb")
                        || language.equalsIgnoreCase("zh")
                        || language.equalsIgnoreCase("ja")) {
                    langu = language;
                }

            }            
            
            pageLinkService = new PageLinkServiceImpl();
            JSONObject jo = pageLinkService.callPageLinkAllService(titles, langu);
            String s = jo.toString();
            logger.info("PluginPageLink return => " + s);

            return s;

        } catch (Exception e) {
            /*
			logger.error("#search exception thrown while executing search " + e.getMessage());
			throw new Exception("Error while executing search", e);
                    
             */
            logger.error("#search exception thrown while executing search " + e.getMessage());
            JSONObject jo1 = new JSONObject();
            jo1.put("result", -20);
            jo1.put("log", "PageLinkPlugin general Exception");
            jo1.put("time", "0");
            return jo1.toString();
        }
    }
}

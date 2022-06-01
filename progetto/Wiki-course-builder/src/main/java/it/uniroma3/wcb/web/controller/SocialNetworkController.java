package it.uniroma3.wcb.web.controller;

import it.uniroma3.wcb.course.graph.SocialNetworkGraphService;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Controller
public class SocialNetworkController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SocialNetworkGraphService socialNetworkGraphService;
	
	@RequestMapping(value = "/networks/getHittingPagesGraph", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getCommunityGraph() throws Exception {
		try {
		
			JSONObject jsonResponse = this.socialNetworkGraphService.buildHittingPagesGraph();
			jsonResponse.put("success", true);
			return jsonResponse.toString();
			
		} catch (Exception e) {
			logger.error("Exception thrown while building hitting pages graph : "	+ e.getMessage());
			throw new Exception("Exception thrown while building hitting pages graph");
		}
	}
}

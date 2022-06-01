package it.uniroma3.wcb.course.sequence;

import org.json.JSONObject;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface SequenceService {

	public JSONObject generateSequence(String startingPageTitle, String searchConstraints, int depth, String language) throws Exception;
	
}

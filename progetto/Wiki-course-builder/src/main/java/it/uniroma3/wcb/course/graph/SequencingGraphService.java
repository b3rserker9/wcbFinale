package it.uniroma3.wcb.course.graph;

import it.uniroma3.wcb.course.scan.PageLinksDTO;

import org.json.JSONObject;

/**
 * Manages build of graphs resulting by a sequencing process.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 */
public interface SequencingGraphService {

	/**
	 * Builds graph, compatible with GUI, based on sequencing alghoritm results.   
	 * <p>
	 * 
	 * @param pageLinksDTO  result of sequencing process
	 * @return GUI compliant graph
	 * @throws Exception
	 */
	public JSONObject buildGraph(PageLinksDTO pageLinksDTO) throws Exception;
	
	
}

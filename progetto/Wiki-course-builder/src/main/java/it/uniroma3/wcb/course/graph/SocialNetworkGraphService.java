package it.uniroma3.wcb.course.graph;

import org.json.JSONObject;

/**
 * Manages build of community of practice graphs.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 */
public interface SocialNetworkGraphService {

	/**
	 * Builds graph, compatible with GUI, which represents Community of Practice interaction graph.
	 * <p>
	 * 
	 * @return GUI compliant graph
	 * @throws Exception
	 */
	public JSONObject buildHittingPagesGraph() throws Exception;
}

package it.uniroma3.wcb.course.graph;

import it.uniroma3.wcb.course.ranking.RankType;
import it.uniroma3.wcb.course.scan.PageLinksDTO;
import it.uniroma3.wcb.course.search.CustomWiki;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.gephi.datalab.api.AttributeColumnsController;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.spi.CharacterExporter;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2;
import org.gephi.layout.plugin.forceAtlas2.ForceAtlas2Builder;
import org.gephi.project.api.ProjectController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openide.util.Lookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Manages graphs building using results of a sequencing process. 
 * <p>
 * Starting from initial selected page, are drawn, iteratively, all outgoing links from every page 
 * by highlighting the selected nodes by sequencing processes.
 * </p>
 * 
 * This service uses gephi-toolkit
 * libraries to build graph, implementing Force Atlas 2 layout.
 * <p>
 * Result object contains:
 * <ul>
 * <li> gexf graph format </li>
 * <li> informations, used by GUI, to identify nodes (and edges) selected by sequencing process.</li>
 * </ul>
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 */
@Service("graphService")
public class SequencingGraphServiceImpl implements SequencingGraphService{

	//Layout constants:
    private static final Double LAYOUT_SCALE = 200.0;
    private static final Boolean BARNES_HUTT_OPTIMIZE = true;
    private static final Boolean ADJUST_SIZES = true;
    private static final int LAYOUT_ITERATIONS = 5000;
    private static final Integer THREADS_COUNT = 4;
    private static final Double LAYOUT_GRAVITY = 1.0;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
	private CustomWiki wikipedia;
    

    /**
	 * {@inheritDoc}
	 */
    @Override
	public JSONObject buildGraph(PageLinksDTO pageLinksDTO) throws Exception {
		//Initialize graph
		long start = System.currentTimeMillis();
		logger.info("Starting building graph");
		
	    ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
	    pc.newProject();
	    
	    GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
	    //DynamicModel dynamicModel = Lookup.getDefault().lookup(DynamicController.class).getModel(); //Necessary to create dynamic model, otherwise dynamics usage won't work
	    AttributeColumnsController acc = Lookup.getDefault().lookup(AttributeColumnsController.class);
	    Graph graph = graphModel.getGraph(); // ...or...   DirectedGraph graph = graphModel.getDirectedGraph();
	   
	    //aux map to "remember" created nodes
	    Map<String, Node> createdNodes = new HashMap<>();
	    
		Set<String> createdEdges = new HashSet<>();
		Map<String, Set<RankType>> edgesSelectedByAlghoritms = new HashMap<>();
	    
		Set<PageLinksDTO> selectedPages = new HashSet<>();
		
	    createNodes(null, pageLinksDTO, createdNodes, createdEdges, edgesSelectedByAlghoritms, selectedPages, graph, graphModel);
	    createEdges(pageLinksDTO, createdNodes, createdEdges, edgesSelectedByAlghoritms, graph, graphModel, acc);
	    
	    long end = System.currentTimeMillis();
	    logger.info("Sequencing graph built in "+ (end-start) + " ms.");
	    logger.info("Starting Force Atlas 2 algorithm");
	    
	    start = System.currentTimeMillis();
	    
	    //Run Force Atlas 2 layout - The layout always takes the current visible view
        ForceAtlas2 layout = new ForceAtlas2Builder().buildLayout();
        layout.setGraphModel(graphModel);
        layout.initAlgo();
        layout.resetPropertiesValues();
        layout.setAdjustSizes(ADJUST_SIZES);
        layout.setBarnesHutOptimize(BARNES_HUTT_OPTIMIZE);
        layout.setScalingRatio(LAYOUT_SCALE);
        layout.setThreadsCount(THREADS_COUNT);
        layout.setGravity(LAYOUT_GRAVITY);

        for (int i = 0; i < LAYOUT_ITERATIONS && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        layout.endAlgo();
	    
        end = System.currentTimeMillis();
        logger.info("Force atlas completed in "+ (end-start) + " ms.");
        logger.info("Starting gexf file export");
        
        start = System.currentTimeMillis();
        
	    //Export GEXF
	    ExportController ec = Lookup.getDefault().lookup(ExportController.class);
	    Exporter exporter = ec.getExporter("gexf");
    	CharacterExporter characterExporter = (CharacterExporter)exporter;
    	StringWriter stringWriter = null;
    	String gexfString = "";
    	
	    try {
	    	stringWriter = new StringWriter();
	    	ec.exportWriter(stringWriter, characterExporter);
	    	gexfString = stringWriter.toString();
	        //ec.exportFile(new File("C:/directed.gexf")); //uncomment this if you want process result on file for debug
	    } catch (Exception ex) {
	        logger.error("Error while generating graph", ex);
	    }
	    finally{
	    	try{
	    		stringWriter.close();
	    	}catch(Exception in){
	    		stringWriter = null;
	    	}
	    }
	    
	    end = System.currentTimeMillis();
	    logger.info("Gexf exported in "+ (end-start) + " ms.");
	    JSONObject result =  new JSONObject();
	    result.put("gexf", gexfString);
	    
	    JSONArray selectedArray = new JSONArray();
	    for(PageLinksDTO selectedPage:selectedPages){
	    	
	    	JSONObject selected = new JSONObject();
	    	selected.put("title", selectedPage.getTitle());
	    	JSONObject selectionInfo = new JSONObject();
	    	
	    	for(Map.Entry<RankType, Boolean> isSelectedFromAlg : pageLinksDTO.getSelectedFromSequenceAlgorithms().entrySet()){
	    		selectionInfo.put(isSelectedFromAlg.getKey().getLabel(), isSelectedFromAlg.getValue().booleanValue());
	    	}
	    	selected.put("selectionInfo", selectionInfo);
	    	selectedArray.put(selected);
	    }
	    result.put("selectionInfos", selectedArray);
	    
	    return result;
	}
	
    /**
     * Iterative method used to create graph nodes.
     * 
     * @param ancestorDTO  parent pageDTO
     * @param pageDTO  actual pageDTP
     * @param createdNodes  created nodes map 
     * @param createdEdge  created edges map
     * @param edgesSelectedByAlghoritms  relevant edges resulting from sequencing alghoritms
     * @param selectedPages  relevant nodes resulting from sequencing alghoritms
     * @param graph  gephi graph
     * @param graphModel  gephi graphModel
     * @throws Exception
     */
	private void createNodes(PageLinksDTO ancestorDTO, PageLinksDTO pageDTO, Map<String, Node> createdNodes, Set<String> createdEdge, 
			Map<String, Set<RankType>> edgesSelectedByAlghoritms, Set<PageLinksDTO> selectedPages, 
			Graph graph, GraphModel graphModel) throws Exception{

		//if page wasn't yet created... 
		if(!createdNodes.containsKey(pageDTO.getId())){
			//generate node invoking graph model factory
			Node node = graphModel.factory().newNode(pageDTO.getId());
			// node label is page title
			node.getNodeData().setLabel(pageDTO.getTitle());
			
			//for each ranking alghoritm score will be added a specific attribute to node
			for(Map.Entry<RankType, Double> rankingScore : pageDTO.getRankingScores().entrySet()){
				node.getNodeData().getAttributes().setValue(rankingScore.getKey().getLabel(), rankingScore.getValue().doubleValue());
			}
			
			//for each node will be verified if it was selected by a sequencing alghoritm... this information
			//will be stored in a set of specific node attributes
			JSONObject selectionInfo = new JSONObject();
			for(Map.Entry<RankType, Boolean> isSelectedFromAlg : pageDTO.getSelectedFromSequenceAlgorithms().entrySet()){

				boolean selected = isSelectedFromAlg.getValue().booleanValue();
				node.getNodeData().getAttributes().setValue(isSelectedFromAlg.getKey().getLabel()+"_selected", selected);
				selectionInfo.put(isSelectedFromAlg.getKey().getLabel(), isSelectedFromAlg.getValue().booleanValue());
				if(selected){
					selectedPages.add(pageDTO);
					
					if(ancestorDTO!=null && ancestorDTO.getSelectedFromSequenceAlgorithms().containsKey(isSelectedFromAlg.getKey()) && 
							ancestorDTO.getSelectedFromSequenceAlgorithms().get(isSelectedFromAlg.getKey()).booleanValue()){
						
						Set<RankType> values = edgesSelectedByAlghoritms.get(ancestorDTO.getId()+"_"+pageDTO.getId());
						if(values == null)
							values = new HashSet<>();
						
						values.add(isSelectedFromAlg.getKey());
						edgesSelectedByAlghoritms.put(ancestorDTO.getId()+"_"+pageDTO.getId(), values);
					}
				}
			}
			
			//adding other node attributes
			node.getNodeData().getAttributes().setValue("selectionInfo", selectionInfo.toString());
                        try{
                            node.getAttributes().setValue("pageId", pageDTO.getPage().getId());
                        }
                        catch(Exception e){
                            node.getAttributes().setValue("pageId", pageDTO.getPage().getTitle());
                        }
			JSONObject wrapper = pageDTO.getPage().getBasicJSON();//wikipedia.getPageBaseObject(pageDTO.getTitle());
			node.getAttributes().setValue("url", wrapper.getString("url"));
			node.getAttributes().setValue("unescapedUrl", wrapper.getString("unescapedUrl"));		
			node.getNodeData().setSize(20);
			node.getNodeData().setColor(0.5f, 0.5f, 0.5f);
			
			graph.addNode(node);
			createdNodes.put(pageDTO.getId(), node);
			
			//for each page linked to actual page will be created an edge (not directed!!)
			for(PageLinksDTO link : pageDTO.getLinkedPages()){
				if(!createdEdge.contains(pageDTO.getId()+"_"+link.getId()) && !createdEdge.contains(link.getId()+"_"+pageDTO.getId())){
					createdEdge.add(pageDTO.getId()+"_"+link.getId());
				}
				createNodes(pageDTO, link, createdNodes, createdEdge, edgesSelectedByAlghoritms, selectedPages, graph, graphModel);
			}
		}
		// ... else if page node was yet created, will be only verified if it was selected by a sequencing alghoritm
		// to update relevant edges 
		else{ 
			if(ancestorDTO!=null && ancestorDTO.getLevel()<=pageDTO.getLevel()){
				for(Map.Entry<RankType, Boolean> isSelectedFromAlg : pageDTO.getSelectedFromSequenceAlgorithms().entrySet()){
					
					if(isSelectedFromAlg.getValue().booleanValue()){
						if(ancestorDTO.getSelectedFromSequenceAlgorithms().containsKey(isSelectedFromAlg.getKey()) && 
								ancestorDTO.getSelectedFromSequenceAlgorithms().get(isSelectedFromAlg.getKey()).booleanValue()){
							
							Set<RankType> values = edgesSelectedByAlghoritms.get(ancestorDTO.getId()+"_"+pageDTO.getId());
							if(values == null)
								values = new HashSet<>();
							
							values.add(isSelectedFromAlg.getKey());
							edgesSelectedByAlghoritms.put(ancestorDTO.getId()+"_"+pageDTO.getId(), values);
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * Method used to create graph edges.
	 * 
	 * @param pageLinksDTO  starting pageDTO
	 * @param createdNodes  nodes created by {@link #createNodes(PageLinksDTO, PageLinksDTO, Map, Set, Map, Set, Graph, GraphModel) createNodes} method
	 * @param createdEdges  edges identified by {@link #createNodes(PageLinksDTO, PageLinksDTO, Map, Set, Map, Set, Graph, GraphModel) createNodes} method
	 * @param edgesSelectedByAlghoritms  relevant edges identified by {@link #createNodes(PageLinksDTO, PageLinksDTO, Map, Set, Map, Set, Graph, GraphModel) createNodes} method
	 * @param graph  gephi graph
     * @param graphModel  gephi graphModel
	 * @param acc  attribute columns controller
	 */
	private void createEdges(PageLinksDTO pageLinksDTO, Map<String, Node> createdNodes, Set<String> createdEdges, Map<String, Set<RankType>> edgesSelectedByAlghoritms, 
			Graph graph, GraphModel graphModel, AttributeColumnsController acc){

		for(String createdEdge : createdEdges){
			Node source = createdNodes.get(createdEdge.substring(0, createdEdge.indexOf("_")));
			Node target = createdNodes.get(createdEdge.substring(createdEdge.indexOf("_")+1));
			
			Edge edge = graphModel.factory().newEdge(source, target);
			edge.getEdgeData().setColor(0.5f, 0.5f, 0.5f);
			
			graph.addEdge(edge);
			//acc.convertAttributeColumnToDynamic(attributeModel.getEdgeTable(), attributeModel.getEdgeTable().getColumn("Weight"), 2000, Double.POSITIVE_INFINITY, false, false);
		}
		
		for(Map.Entry<String, Set<RankType>> edgeSelectedByAlghoritms : edgesSelectedByAlghoritms.entrySet()){
			Node source = createdNodes.get(edgeSelectedByAlghoritms.getKey().substring(0, edgeSelectedByAlghoritms.getKey().indexOf("_")));
			Node target = createdNodes.get(edgeSelectedByAlghoritms.getKey().substring(edgeSelectedByAlghoritms.getKey().indexOf("_")+1));
			
			Edge edge = graph.getEdge(source, target);
			
			if(edge==null){
				edge = graph.getEdge(target, source);
				if(edge!=null){
					graph.removeEdge(edge);
				}
				edge = graphModel.factory().newEdge(source, target);
				edge.getEdgeData().setColor(0.5f, 0.5f, 0.5f);
				graph.addEdge(edge);
				edge = graph.getEdge(source, target);
			}
			
			for(RankType rankType : edgeSelectedByAlghoritms.getValue()){
				edge.getEdgeData().getAttributes().setValue(rankType.getLabel()+"_selected", true);
			}
		}
	}
}

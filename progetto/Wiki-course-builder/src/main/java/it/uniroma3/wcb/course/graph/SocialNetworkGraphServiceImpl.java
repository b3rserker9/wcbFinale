package it.uniroma3.wcb.course.graph;

import it.uniroma3.wcb.course.search.CustomWiki;
import it.uniroma3.wcb.course.search.PageService;
import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import it.uniroma3.wcb.persistence.dao.CourseTopicRepository;
import it.uniroma3.wcb.persistence.dao.PageRelationRepository;
import it.uniroma3.wcb.persistence.model.CourseTopic;
import it.uniroma3.wcb.persistence.model.Page;
import it.uniroma3.wcb.persistence.model.PageRelation;
import it.uniroma3.wcb.persistence.model.PageRelation.PageRelationType;
import it.uniroma3.wcb.persistence.model.PageTeachingStyle;
import it.uniroma3.wcb.spring.AppConfig;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.io.exporter.api.ExportController;
import org.gephi.io.exporter.spi.CharacterExporter;
import org.gephi.io.exporter.spi.Exporter;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.project.api.ProjectController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openide.util.Lookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class manages graph building processes.
 * <p>
 *
 * This service uses gephi-toolkit libraries to build graph, implementing Yifan
 * Hu layout.
 * <p>
 * Result object contains:
 * <ul>
 * <li> gexf graph format </li>
 * <li> informations, used by GUI, to color nodes, according to its predominant
 * teaching style attribute.</li>
 * </ul>
 * <p>
 *
 * @author Andrea Tarantini, Alessandra Milita
 */
@Service("socialNetworkGraphService")
public class SocialNetworkGraphServiceImpl implements SocialNetworkGraphService {

    @Autowired
    private PageService pageRepository;

    @Autowired
    private PageRelationRepository pageRelationRepository;

    @Autowired
    private CourseTopicRepository courseTopicRepository;

    //@Autowired
    private CustomWiki customWiki;

    @Autowired
    private AppConfig configs;
    
    private final ExtraConfig extraConfigs = ExtraConfig.getIstance();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //Layout constants:
    private static final int LAYOUT_ITERATIONS = 100;
    private static final float OPTIMAL_DISTANCE = 150f;
    private static final float BARNES_HUT_THETA = 1.2f;
    private static final int QUAD_TREE_MAX_LEVEL = 10;
    private static final float STEP_RATIO = 0.9f;

    
    //Node colors
    private static float[] FORMAL_AUTHORITY_NODE_COLOR = new float[]{0.92f, 0.27f, 0.18f};  //235, 69, 46  
    private static float[] DELEGATOR_NODE_COLOR = new float[]{0.93f, 0.74f, 0.16f}; //237, 189, 41
    private static float[] FACILITATOR_NODE_COLOR = new float[]{0.17f, 0.92f, 0.69f}; // 43, 235, 176
    private static float[] EXPERT_NODE_COLOR = new float[]{0.18f, 0.58f, 0.91f}; //46, 148, 232
    private static float[] PERSONAL_MODEL_NODE_COLOR = new float[]{0.65f, 0.58f, 0.45f}; //166,148,115
    private static float[] DEFAULT_NODE_COLOR = new float[]{0.6f, 0.6f, 0.6f};
    private static float[] DEFAULT_EDGE_COLOR = new float[]{0.75f, 0.75f, 0.75f};

    private static final int TS_DECIMAL_PRECISION = 2;
    private static final double TS_ATTR_APPROX_RATIO = 0.5d;

    
    
    private Map<String, Map<String, Page>> pagesMap;
    /**
     * {@inheritDoc}
     */
    @Override
    public JSONObject buildHittingPagesGraph() throws Exception {
        //Initialize graph
        long start = System.currentTimeMillis();
        logger.info("Starting building hitting pages network graph");
        ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
        pc.newProject();

        GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
        Graph graph = graphModel.getUndirectedGraph();

        //retrieving ALL stored page relations
        List<PageRelation> pageRelations = this.pageRelationRepository.getByType(PageRelationType.TOPIC);

        if(extraConfigs.getNumberGroupQueryCommunity()!=1){
            pagesMap=this.getPages(pageRelations);
        }
        
        //... for each page relation will be created a graph link
        if (pageRelations != null) {
            for (PageRelation pageRelation : pageRelations) {
                String langu = "en";
                if (pageRelation.getLanguage() == null) {
                    pageRelation.setLanguage("");
                }
                if (pageRelation.getLanguage().isEmpty()) {
                    if (pageRelation.getTopicId() != null) {
                        CourseTopic one = this.courseTopicRepository.findOne(pageRelation.getTopicId());
                        if (one != null) {
                            langu = one.getLang();
                        }

                    }
                    pageRelation.setLanguage(langu);
                    this.pageRelationRepository.delete(pageRelation.getId());

                    this.pageRelationRepository.save(pageRelation);

                }

                createEdge(pageRelation, graph, graphModel);
            }
        }

        long end = System.currentTimeMillis();
        logger.info("Hitting pages network graph built in " + (end - start) + " ms.");
        logger.info("Starting YifanHuLayout algorithm");

        start = System.currentTimeMillis();

        //Run YifanHuLayout for LAYOUT_ITERATIONS passes - The layout always takes the current visible view
        YifanHuLayout layout = new YifanHuLayout(null, new StepDisplacement(1f));
        layout.setGraphModel(graphModel);
        layout.initAlgo();
        layout.resetPropertiesValues();
        layout.setOptimalDistance(OPTIMAL_DISTANCE);
        layout.setBarnesHutTheta(BARNES_HUT_THETA);
        layout.setQuadTreeMaxLevel(QUAD_TREE_MAX_LEVEL);
        layout.setStepRatio(STEP_RATIO);

        for (int i = 0; i < LAYOUT_ITERATIONS && layout.canAlgo(); i++) {
            layout.goAlgo();
        }
        layout.endAlgo();

        end = System.currentTimeMillis();
        logger.info("Force atlas completed in " + (end - start) + " ms.");
        logger.info("Starting gexf file export");

        start = System.currentTimeMillis();

        //Export GEXF
        ExportController ec = Lookup.getDefault().lookup(ExportController.class);
        Exporter exporter = ec.getExporter("gexf");
        CharacterExporter characterExporter = (CharacterExporter) exporter;
        StringWriter stringWriter = null;
        String gexfString = "";

        try {
            stringWriter = new StringWriter();
            ec.exportWriter(stringWriter, characterExporter);
            gexfString = stringWriter.toString();
            ec.exportFile(new File("C:/example.gexf"));
        } catch (Exception ex) {
            logger.error("Error while generating graph", ex);
        } finally {
            try {
                stringWriter.close();
            } catch (Exception in) {
                stringWriter = null;
            }
        }

        end = System.currentTimeMillis();
        logger.info("Gexf exported in " + (end - start) + " ms.");
        JSONObject result = new JSONObject();
        result.put("gexf", gexfString);

        //generating colors informations that will be propagated to GUI
        result.put("legendColors", getGraphColorsLegend());

        return result;
    }

    /**
     * Create a graph edge based on given page relation.<br/>
     * If edge yet exists, will be increased its weight.
     * <p>
     *
     * @param pageRelation page relation
     * @param graph gephi graph
     * @param graphModel gephi graphModel
     * @throws Exception
     */
    private void createEdge(PageRelation pageRelation, Graph graph, GraphModel graphModel) throws Exception {

        String titleOne = pageRelation.getPageOneTitle();
        String titleTwo = pageRelation.getPageTwoTitle();

        Node node1 = createNode(graph, graphModel, titleOne, pageRelation.getLanguage());
        Node node2 = createNode(graph, graphModel, titleTwo, pageRelation.getLanguage());

        Edge edge = graph.getEdge(node1, node2);
        if (edge == null) {
            edge = graphModel.factory().newEdge(node1, node2);
            edge.getEdgeData().setColor(DEFAULT_EDGE_COLOR[0], DEFAULT_EDGE_COLOR[1], DEFAULT_EDGE_COLOR[2]);
            edge.setWeight(1f);
            edge.getEdgeData().getAttributes().setValue("weight", 1.1f);
            graph.addEdge(edge);
        } else {
            float newWeight = edge.getWeight() + 1; //increments edge weight
            edge.setWeight(newWeight);
            edge.getEdgeData().getAttributes().setValue("weight", newWeight);
        }
    }

    /**
     * Create a graph node with given label.
     * <p>
     *
     * @param graph gephi graph
     * @param graphModel gephi graphModel
     * @param title node label
     * @return graph node
     * @throws Exception
     */
    private Node createNode(Graph graph, GraphModel graphModel, String title, String lang) throws Exception {

        Node node = graph.getNode(title);
        if (node == null) {

            String trim = lang;
            if (!trim.isEmpty()) {
                customWiki = new CustomWiki(trim + ".wikipedia.org");
            } else {
                trim = "en";
                customWiki = new CustomWiki();
            }

            node = graphModel.factory().newNode(title);
            node.getNodeData().setLabel(title);
            node.getAttributes().setValue("url", this.customWiki.getPageUrl(title, false));
            node.getAttributes().setValue("unescapedUrl", this.customWiki.getPageUrl(title, true));
            //String lang =this.customWiki.getDomain().split("\\.")[0];
            //get page from database
            //Page page = this.pageRepository.(title,lang);
            //get Page from Service 
            Page page=null;
            if(extraConfigs.getNumberGroupQueryCommunity()>1){
                if(pagesMap!=null){
                    
                    Map<String, Page> get = pagesMap.get(trim);
                    if(get!=null){
                        page=get.get(title.toLowerCase());
                    }
                }
            }
            
            if(page==null){
                page = this.pageRepository.getPage(title, extraConfigs.isMongoDbCourseOn(lang), configs.cachePagesOnDatabase(), configs.getMaxLoopSize(), trim);
            }
            //if it exists, will be inserted its teaching style details
            if (page != null && page.getTeachingStyle() != null) {
                node.getNodeData().getAttributes().setValue("teachingStyle", page.getTeachingStyle().toString(TS_DECIMAL_PRECISION));

                node.getNodeData().getAttributes().setValue("contributes", page.getTeachingStyle().getAvgContributors());
                node.getNodeData().setSize(page.getTeachingStyle().getAvgContributors());

                setNodeColor(node, page.getTeachingStyle());
                node.getNodeData().getAttributes().setValue("discipline", page.getTeachingStyle().getPrimaryDiscipline());
            } else {
                setNodeColor(node, null);
                node.getNodeData().getAttributes().setValue("contributes", 1);
                node.getNodeData().setSize(1);
                node.getNodeData().getAttributes().setValue("discipline", "");
            }

            graph.addNode(node);
        }

        return node;
    }

    /**
     * This method apply right color to given node, based on its teaching style
     * attributes.
     * <p>
     *
     * @param node page node
     * @param pageTeachingStyle page teaching style
     */
    private void setNodeColor(Node node, PageTeachingStyle pageTeachingStyle) {

        JSONObject colors = new JSONObject();

        if (pageTeachingStyle != null) {
            JSONArray allColors = new JSONArray();
            Map<Double, List<float[]>> reverseIndexes = new TreeMap<>(Collections.reverseOrder());

            List<float[]> v1 = reverseIndexes.get(pageTeachingStyle.getFormalAuthority());
            if (v1 == null) {
                v1 = new ArrayList<>();
            }
            v1.add(FORMAL_AUTHORITY_NODE_COLOR);
            reverseIndexes.put(pageTeachingStyle.getFormalAuthority(), v1);

            List<float[]> v2 = reverseIndexes.get(pageTeachingStyle.getPersonalModel());
            if (v2 == null) {
                v2 = new ArrayList<>();
            }
            v2.add(PERSONAL_MODEL_NODE_COLOR);
            reverseIndexes.put(pageTeachingStyle.getPersonalModel(), v2);

            List<float[]> v3 = reverseIndexes.get(pageTeachingStyle.getDelegator());
            if (v3 == null) {
                v3 = new ArrayList<>();
            }
            v3.add(DELEGATOR_NODE_COLOR);
            reverseIndexes.put(pageTeachingStyle.getDelegator(), v3);

            List<float[]> v4 = reverseIndexes.get(pageTeachingStyle.getFacilitator());
            if (v4 == null) {
                v4 = new ArrayList<>();
            }
            v4.add(FACILITATOR_NODE_COLOR);
            reverseIndexes.put(pageTeachingStyle.getFacilitator(), v4);

            List<float[]> v5 = reverseIndexes.get(pageTeachingStyle.getExpert());
            if (v5 == null) {
                v5 = new ArrayList<>();
            }
            v5.add(EXPERT_NODE_COLOR);
            reverseIndexes.put(pageTeachingStyle.getExpert(), v5);

            double aux = 0;
            float[] defaultColor = null;

            for (Map.Entry<Double, List<float[]>> reverseIndex : reverseIndexes.entrySet()) {

                for (float[] color : reverseIndex.getValue()) {
                    if (defaultColor == null) {
                        defaultColor = color;
                        aux = reverseIndex.getKey();
                    }

                    if (reverseIndex.getKey() >= (aux - TS_ATTR_APPROX_RATIO)) {
                        allColors.put(getRgbColor(color));
                    }
                }
            }

            node.getNodeData().setColor(defaultColor[0], defaultColor[1], defaultColor[2]);

            colors.put("all", allColors);
            colors.put("formalAuthority", getGradientColor(FORMAL_AUTHORITY_NODE_COLOR, pageTeachingStyle.getFormalAuthority()));
            colors.put("delegator", getGradientColor(DELEGATOR_NODE_COLOR, pageTeachingStyle.getDelegator()));
            colors.put("facilitator", getGradientColor(FACILITATOR_NODE_COLOR, pageTeachingStyle.getFacilitator()));
            colors.put("expert", getGradientColor(EXPERT_NODE_COLOR, pageTeachingStyle.getExpert()));
            colors.put("personalModel", getGradientColor(PERSONAL_MODEL_NODE_COLOR, pageTeachingStyle.getPersonalModel()));

            node.getNodeData().getAttributes().setValue("all_colors", colors.toString());

        } else {
            node.getNodeData().setColor(DEFAULT_NODE_COLOR[0], DEFAULT_NODE_COLOR[1], DEFAULT_NODE_COLOR[2]);
        }
    }

    /**
     * Calculate shades of colors, compared to a referred one (baseColor),
     * according to the given teaching style attribute (value).
     *
     * @param baseColor
     * @param value teaching style attribute weight
     * @return object representing color shades
     */
    private JSONArray getGradientColor(float[] baseColor, Double value) {

        JSONArray array = new JSONArray();

        float initialValue = +0.4f;
        float incremetalRatio = -0.2f;

        if (value != null) {
            float delta = 0;

            if (value.doubleValue() < 1) {
                delta = initialValue;
            } else if (value.doubleValue() < 2) {
                delta = initialValue + (1 * incremetalRatio);
            } else if (value.doubleValue() < 3) {
                delta = initialValue + (2 * incremetalRatio);
            } else if (value.doubleValue() < 4) {
                delta = initialValue + (3 * incremetalRatio);
            } else if (value.doubleValue() < 5) {
                delta = initialValue + (4 * incremetalRatio);
            }

            float[] gradientColor = new float[3];
            gradientColor[0] = Math.max(0f, Math.min(1f, baseColor[0] + (delta)));
            gradientColor[1] = Math.max(0f, Math.min(1f, baseColor[1] + (delta)));
            gradientColor[2] = Math.max(0f, Math.min(1f, baseColor[2] + (delta)));

            array.put(getRgbColor(gradientColor));
        } else {
            for (int i = 0; i < 5; i++) {
                float delta = initialValue + (i * incremetalRatio);;
                float[] gradientColor = new float[3];
                gradientColor[0] = Math.max(0f, Math.min(1f, baseColor[0] + (delta)));
                gradientColor[1] = Math.max(0f, Math.min(1f, baseColor[1] + (delta)));
                gradientColor[2] = Math.max(0f, Math.min(1f, baseColor[2] + (delta)));

                JSONObject object = new JSONObject();
                object.put("from", i);
                object.put("to", i + 1);
                object.put("label", "from " + i + " to " + (i + 1));
                object.put("color", getRgbColor(gradientColor));
                array.put(object);
            }
        }

        return array;
    }

    /**
     * Builds an object used by GUI to represent teaching style colors legend.
     *
     * @return object containing teaching style colors legend.
     */
    private JSONObject getGraphColorsLegend() {

        JSONObject legend = new JSONObject();

        JSONArray allColors = new JSONArray();
        allColors.put(new JSONObject().put("label", "Expert").put("color", getRgbColor(EXPERT_NODE_COLOR)));
        allColors.put(new JSONObject().put("label", "Formal authority").put("color", getRgbColor(FORMAL_AUTHORITY_NODE_COLOR)));
        allColors.put(new JSONObject().put("label", "Personal model").put("color", getRgbColor(PERSONAL_MODEL_NODE_COLOR)));
        allColors.put(new JSONObject().put("label", "Facilitator").put("color", getRgbColor(FACILITATOR_NODE_COLOR)));
        allColors.put(new JSONObject().put("label", "Delegator").put("color", getRgbColor(DELEGATOR_NODE_COLOR)));
        legend.put("all", allColors);

        legend.put("formalAuthority", getGradientColor(FORMAL_AUTHORITY_NODE_COLOR, null));
        legend.put("delegator", getGradientColor(DELEGATOR_NODE_COLOR, null));
        legend.put("facilitator", getGradientColor(FACILITATOR_NODE_COLOR, null));
        legend.put("expert", getGradientColor(EXPERT_NODE_COLOR, null));
        legend.put("personalModel", getGradientColor(PERSONAL_MODEL_NODE_COLOR, null));

        return legend;
    }

    /**
     * Return rgb formatted string from rgb array.
     * <p>
     *
     * @param array rgb array
     * @return formatted rgb string
     */
    private String getRgbColor(float[] array) {

        int r = Math.round(array[0] * 255);
        int g = Math.round(array[1] * 255);
        int b = Math.round(array[2] * 255);

        return "rgb(" + r + "," + g + "," + b + ")";
    }

    private Map<String, Map<String, Page>> getPages(List<PageRelation> pageRelations) {
        return (extraConfigs.getNumberGroupQueryCommunity()<1)? getPagesAll(pageRelations): getPagesSplit(pageRelations);
    }
    
    private Map<String, Map<String, Page>> getPagesAll(List<PageRelation> pageRelations) {
        Map<String, Map<String, Page>> ret = new TreeMap<>();
        Map<String, Set<String>> temp = new TreeMap<>();
        
        
        
        if (pageRelations != null) {
            for (PageRelation pageRelation : pageRelations) {
                String langu = "en";
                if (pageRelation.getLanguage() == null) {
                    pageRelation.setLanguage("");
                }
                if (pageRelation.getLanguage().isEmpty()) {
                    if (pageRelation.getTopicId() != null) {
                        CourseTopic one = this.courseTopicRepository.findOne(pageRelation.getTopicId());
                        if (one != null) {
                            langu = one.getLang();
                        }

                    }
                    pageRelation.setLanguage(langu);
                    this.pageRelationRepository.delete(pageRelation.getId());

                    this.pageRelationRepository.save(pageRelation);

                }
                Set<String> get = temp.get(langu);
                if(get==null){
                    get=new HashSet<>();
                }
                get.add(pageRelation.getPageOneTitle());
                get.add(pageRelation.getPageTwoTitle());
                temp.put(langu, get);
            }
        }
        
        
        
        
        for(Map.Entry<String,Set<String>> l:temp.entrySet()){
            Set<String> pagess = l.getValue();
            Set<Page> pages = new HashSet<>();
            
            
                try {
                    //retrieves page from title...
                    int sz = configs.getMaxLoopSize();

                    pages=this.pageRepository.getPages(pagess, extraConfigs.isMongoDbSearchOn(l.getKey()), configs.cachePagesOnDatabase(), sz, l.getKey());
                    for(Page p:pages){
                        Map<String, Page> get = ret.get(p.getLang());
                        if(get==null){
                            get= new TreeMap<>();
                        }
                        String replaceAll = p.getTitle().toLowerCase().replaceAll("_", " ");
                        get.put(replaceAll, p);
                        ret.put(p.getLang(), get);
                    }
                } catch (IOException e) {
                    logger.warn("Error while retrieving text of pages", e);

                }
        
            
        }
        return ret;
    }
    
    
    
    private Map<String, Map<String, Page>> getPagesSplit(List<PageRelation> pageRelations) {
        Map<String, Map<String, Page>> ret = new TreeMap<>();
        Map<String, Set<String>> temp = new TreeMap<>();
        
        
        
        if (pageRelations != null) {
            for (PageRelation pageRelation : pageRelations) {
                String langu = "en";
                if (pageRelation.getLanguage() == null) {
                    pageRelation.setLanguage("");
                }
                if (pageRelation.getLanguage().isEmpty()) {
                    if (pageRelation.getTopicId() != null) {
                        CourseTopic one = this.courseTopicRepository.findOne(pageRelation.getTopicId());
                        if (one != null) {
                            langu = one.getLang();
                        }

                    }
                    pageRelation.setLanguage(langu);
                    this.pageRelationRepository.delete(pageRelation.getId());

                    this.pageRelationRepository.save(pageRelation);

                }
                Set<String> get = temp.get(langu);
                if(get==null){
                    get=new HashSet<>();
                }
                get.add(pageRelation.getPageOneTitle());
                get.add(pageRelation.getPageTwoTitle());
                temp.put(langu, get);
            }
        }
        
        
        
        
        for(Map.Entry<String,Set<String>> l:temp.entrySet()){
            Set<String> pagess = new HashSet<>();
            int len=l.getValue().size();
            Set<Page> pages = new HashSet<>();
            
            int volte = (len/extraConfigs.getNumberGroupQueryCommunity())+1;
            int delta=0;
            Object[] toArray = l.getValue().toArray();
            for (int x = 0; x < volte; x++) {

                
                //for (int i = 0; i < len; i++) {
                int i=0;
                int c=0;
                while(c < extraConfigs.getNumberGroupQueryCommunity()) {
                    int ind= i+delta;
                    if(ind<len){
                        //JSONObject obj = items.getJSONObject(ind);
                        String title = (String)toArray[ind];//obj.getString("title");
                        
                        if (title != null && !"".equals(title)) {
                            
                            Map<String, Page> get = ret.get(l.getKey());
                            if(get==null){
                                pagess.add(title);
                                i++;
                            }
                            else{
                                Page get1 = get.get(title.toLowerCase());
                            
                                if(get1==null){
                                    pagess.add(title);
                                    i++;
                                }
                            }
                            //jo.put(title.toLowerCase(), obj);
                        } else {
                            logger.warn("Page with no title. Item will be skipped.");
                            continue;
                        }
                    }
                    else{
                        break;
                    }
                    c++;
                }
                delta+=extraConfigs.getNumberGroupQueryCommunity();
                try {
                    //retrieves page from title...
                    int sz = configs.getMaxLoopSize();

                    pages=this.pageRepository.getPages(pagess, extraConfigs.isMongoDbSearchOn(l.getKey()), configs.cachePagesOnDatabase(), sz, l.getKey());
                    for(Page p:pages){
                        Map<String, Page> get = ret.get(p.getLang());
                        if(get==null){
                            get= new TreeMap<>();
                        }
                        String replaceAll = p.getTitle().toLowerCase().replaceAll("_", " ");
                        get.put(replaceAll, p);
                        ret.put(p.getLang(), get);
                    }
                } catch (IOException e) {
                    logger.warn("Error while retrieving text of pages", e);

                }
        
            }
        }
        return ret;
    }
    
}

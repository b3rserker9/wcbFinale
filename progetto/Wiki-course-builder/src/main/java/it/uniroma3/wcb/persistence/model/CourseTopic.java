package it.uniroma3.wcb.persistence.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Entity
public class CourseTopic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String topicName;
	
	private Long courseId;
	
	private Date created;
	
	private Date lastModify;
	
	private String queryConstraints;
        
        private String lang;
        
        private Long position;
	
	@Lob
	private byte[] serializedResults; //byte[] to avoid encoding problems
	
	@Lob
	private byte[] serializedGraph;

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

        
        
        
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastModify() {
		return lastModify;
	}

	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}

	public String getQueryConstraints() {
		return queryConstraints;
	}

	public void setQueryConstraints(String queryConstraints) {
		this.queryConstraints = queryConstraints;
	}

	public byte[] getSerializedResults() {
		return serializedResults;
	}

	public void setSerializedResults(byte[] serializedResults) {
		this.serializedResults = serializedResults;
	}
	
	public byte[] getSerializedGraph() {
		return serializedGraph;
	}

	public void setSerializedGraph(byte[] serializedGraph) {
		this.serializedGraph = serializedGraph;
	}

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

	@Transient
	public JSONArray getResultItems() {
		if(this.serializedResults!=null){
			JSONArray array = new JSONArray(new String(this.serializedResults));
			return array;
		}
		else
			return null;
	}

	@Transient
	public void setResultItems(JSONArray array) {
		if(array!=null)
			this.serializedResults = array.toString().getBytes();
		else
			this.serializedResults = null;
	}
	
	@Transient
	public String getGraph(){
		if(serializedGraph!=null){
			return new String(this.serializedGraph);
		}
		else
			return null;
	}
	
	@Transient
	public void setGraph(String graph){
		if(graph!=null)
			this.serializedGraph = graph.getBytes();
		else
			this.serializedGraph = null;
	}
	
	public JSONObject toJSON(){
		JSONObject object = new JSONObject(this);
		object.put("id", id!=null?String.valueOf(this.id.longValue()):"" );
		object.put("title", this.topicName);
		object.put("courseId", courseId!=null?String.valueOf(this.courseId.longValue()):"" );
                object.put("lang", this.lang );
		object.put("position", position!=null?String.valueOf(this.position.longValue()):"-1" );
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if(this.created!=null){
			object.put("date", df.format(this.created));
		}
		else{
			object.put("date", "");
		}
		
		JSONArray resultItems= this.getResultItems();
		
		if(resultItems!=null){
			object.put("items", resultItems);
		}
		else{
			object.put("items", new JSONArray());
		}
		
		String graph = this.getGraph();
		object.put("gexf", graph!=null?graph:"");
		
		return object;
	}
	
	
}

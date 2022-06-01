package it.uniroma3.wcb.persistence.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.json.JSONObject;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi J-hard
 *
 */
@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long userId;
	
	private Date created;
	
	private Date lastModify;
	
	private String name;
	
	@Lob
	private String description;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public JSONObject toJSON(){
		JSONObject object = new JSONObject(this);
		object.put("id", id!=null?String.valueOf(this.id.longValue()):"" );
		object.put("title", this.name);
		object.put("description", this.description);
		
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		if(this.created!=null){
			object.put("date", df.format(this.created));
			object.put("timestamp", this.created.getTime());
		}
		else{
			object.put("date", "");
			object.put("timestamp", "");
		}
		
		return object;
	}
	
	public void fromJSON(JSONObject object){
		String idStr = object.getString("id");
		if(idStr!=null && !"".equals(idStr) && !"new".equalsIgnoreCase(idStr)){
			this.id = new Long(idStr);
		}
		else{
			this.id = null;
		}
		try{
			this.created = new Date(object.getLong("date"));
		}catch(Exception e){
			this.created = new Date();
		}
			
		this.lastModify = new Date();
		
		this.description = object.getString("description");
		this.name = object.getString("title");
	}
}

package it.uniroma3.wcb.persistence.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.json.JSONObject;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Entity
public class ContextTerms {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Lob
	private String terms;
	
	private Long userId;
	
	private Date created;
	
	private Date lastModify;
	
	private Long queryId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
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

	public Long getQueryId() {
		return queryId;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}
	
	public JSONObject toJSON(){
		return new JSONObject(this);
	}
}

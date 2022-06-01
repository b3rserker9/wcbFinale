package it.uniroma3.wcb.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Entity
@Table(name = "pagerelation", indexes = {
		@Index(name = "indx_type", columnList = "type", unique = false),
		@Index(name = "indx_topicId", columnList = "topicId", unique = false),
		@Index(name = "indx_courseId", columnList = "courseId", unique = false),
		@Index(name = "indx_pageOne", columnList = "pageOneTitle", unique = false),
		@Index(name = "indx_pageTwo", columnList = "pageTwoTitle", unique = false), 
                @Index(name = "indx_language", columnList = "language", unique = false) })
public class PageRelation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "type", nullable = false)
	private PageRelationType type;
	
	@Column(name = "pageOneTitle", nullable = false)
	private String pageOneTitle;
	
	@Column(name = "pageTwoTitle", nullable = false)
	private String pageTwoTitle;
	
	@Column(name = "topicId", nullable = true)
	private Long topicId;
	
	@Column(name = "courseId", nullable = true)
	private Long courseId;
	
        @Column(name = "language", nullable = false)
	private String language;
        
	private Date created;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PageRelationType getType() {
		return type;
	}

	public void setType(PageRelationType type) {
		this.type = type;
	}

	public String getPageOneTitle() {
		return pageOneTitle;
	}

	public void setPageOneTitle(String pageOneId) {
		this.pageOneTitle = pageOneId;
	}

	public String getPageTwoTitle() {
		return pageTwoTitle;
	}

	public void setPageTwoTitle(String pageTwoId) {
		this.pageTwoTitle = pageTwoId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
        
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result
				+ ((pageOneTitle == null) ? 0 : pageOneTitle.hashCode());
		result = prime * result
				+ ((pageTwoTitle == null) ? 0 : pageTwoTitle.hashCode());
		result = prime * result + ((topicId == null) ? 0 : topicId.hashCode());
		result = prime * result + ((courseId == null) ? 0 : courseId.hashCode());
                result = prime * result + ((courseId == null) ? 0 : language.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageRelation other = (PageRelation) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (type != other.type)
			return false;
		
		if (pageOneTitle == null && pageTwoTitle == null) {
			if (other.pageOneTitle != null || other.pageTwoTitle != null)
				return false;
		}
		else{
			if (pageOneTitle == null) {
				if (other.pageOneTitle != null && other.pageTwoTitle != null)
					return false;
			} else if (!pageOneTitle.equalsIgnoreCase(other.pageOneTitle) && !pageOneTitle.equalsIgnoreCase(other.pageTwoTitle))
				return false;
			if (pageTwoTitle == null) {
				if (other.pageOneTitle != null && other.pageTwoTitle != null)
					return false;
			} else if (!pageTwoTitle.equalsIgnoreCase(other.pageOneTitle) && !pageTwoTitle.equalsIgnoreCase(other.pageTwoTitle))
				return false;
		}
		
		if (courseId == null) {
			if (other.courseId != null)
				return false;
		} else if (!courseId.equals(other.courseId))
			return false;
		if (topicId == null) {
			if (other.topicId != null)
				return false;
		} else if (!topicId.equals(other.topicId))
			return false;
                else if (!language.equals(other.language))
			return false;
		return true;
	}
	
	public enum PageRelationType{
		TOPIC,
		LINK;
	}
}

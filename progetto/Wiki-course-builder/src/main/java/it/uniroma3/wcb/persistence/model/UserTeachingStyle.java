package it.uniroma3.wcb.persistence.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Entity
public class UserTeachingStyle implements Serializable{
	
	private static final long serialVersionUID = -7774448314933387654L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private double expert;
	private double formalAuthority;
	private double personalModel;
	private double facilitator;
	private double delegator;
	
	private String expertRange;
	private String formalAuthorityRange;
	private String personalModelRange;
	private String facilitatorRange;
	private String delegatorRange;
	
	private String discipline;
	private String level;
	private String teacherrank;
	private String race;
	private String gender;
	
	@Lob
	private String surveyModule;
	
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
	public double getExpert() {
		return expert;
	}
	public void setExpert(double expert) {
		this.expert = expert;
	}
	public double getFormalAuthority() {
		return formalAuthority;
	}
	public void setFormalAuthority(double formalAuthority) {
		this.formalAuthority = formalAuthority;
	}
	public double getPersonalModel() {
		return personalModel;
	}
	public void setPersonalModel(double personalModel) {
		this.personalModel = personalModel;
	}
	public double getFacilitator() {
		return facilitator;
	}
	public void setFacilitator(double facilitator) {
		this.facilitator = facilitator;
	}
	public double getDelegator() {
		return delegator;
	}
	public void setDelegator(double delegator) {
		this.delegator = delegator;
	}
	
	public String getSurveyModule() {
		return surveyModule;
	}
	public void setSurveyModule(String surveyModule) {
		this.surveyModule = surveyModule;
	}
	public String getExpertRange() {
		return expertRange;
	}
	public void setExpertRange(String expertRange) {
		this.expertRange = expertRange;
	}
	public String getFormalAuthorityRange() {
		return formalAuthorityRange;
	}
	public void setFormalAuthorityRange(String formalAuthorityRange) {
		this.formalAuthorityRange = formalAuthorityRange;
	}
	public String getPersonalModelRange() {
		return personalModelRange;
	}
	public void setPersonalModelRange(String personalModelRange) {
		this.personalModelRange = personalModelRange;
	}
	public String getFacilitatorRange() {
		return facilitatorRange;
	}
	public void setFacilitatorRange(String facilitatorRange) {
		this.facilitatorRange = facilitatorRange;
	}
	public String getDelegatorRange() {
		return delegatorRange;
	}
	public void setDelegatorRange(String delegatorRange) {
		this.delegatorRange = delegatorRange;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTeacherrank() {
		return teacherrank;
	}
	public void setTeacherrank(String teacherrank) {
		this.teacherrank = teacherrank;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}

package it.uniroma3.wcb.persistence.model;

import it.uniroma3.wcb.util.DecimalFormatter;

import java.io.Serializable;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.json.JSONObject;

/**
 *
 * @author Andrea Tarantini, Alessandra , Andrea Giardi
 *
 */
@Entity
public class PageTeachingStyle implements Serializable {

    private static final long serialVersionUID = 2209313467898554642L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double expert = 0;
    private double formalAuthority = 0;
    private double personalModel = 0;
    private double facilitator = 0;
    private double delegator = 0;
    private int avgContributors = 0;
    private String primaryDiscipline;

    @Lob
    private String details;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getExpert() {
        return expert;
    }

    @Transient
    public double getExpert(int decimalPrecision) {
        return formatDouble(expert, decimalPrecision);
    }

    public void setExpert(double expert) {
        this.expert = expert;
    }

    public double getFormalAuthority() {
        return formalAuthority;
    }

    @Transient
    public double getFormalAuthority(int decimalPrecision) {
        return formatDouble(formalAuthority, decimalPrecision);
    }

    public void setFormalAuthority(double formalAuthority) {
        this.formalAuthority = formalAuthority;
    }

    public double getPersonalModel() {
        return personalModel;
    }

    @Transient
    public double getPersonalModel(int decimalPrecision) {
        return formatDouble(personalModel, decimalPrecision);
    }

    public void setPersonalModel(double personalModel) {
        this.personalModel = personalModel;
    }

    public double getFacilitator() {
        return facilitator;
    }

    @Transient
    public double getFacilitator(int decimalPrecision) {
        return formatDouble(facilitator, decimalPrecision);
    }

    public void setFacilitator(double facilitator) {
        this.facilitator = facilitator;
    }

    public double getDelegator() {
        return delegator;
    }

    @Transient
    public double getDelegator(int decimalPrecision) {
        return formatDouble(delegator, decimalPrecision);
    }

    public void setDelegator(double delegator) {
        this.delegator = delegator;
    }

    public int getAvgContributors() {
        return avgContributors;
    }

    public void setAvgContributors(int avgContributors) {
        this.avgContributors = avgContributors;
    }

    public String getPrimaryDiscipline() {
        return primaryDiscipline;
    }

    public void setPrimaryDiscipline(String primaryDiscipline) {
        this.primaryDiscipline = primaryDiscipline;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Transient
    public void addDisciplineContribute(String discipline) {
        if (discipline != null && !"".equals(discipline)) {
            JSONObject objDetails = null;
            try {
                objDetails = new JSONObject(this.details);
            } catch (Exception e) {
                objDetails = new JSONObject();
            }
            int contributes = 1;
            if (objDetails.has(discipline)) {
                contributes = objDetails.getInt(discipline);
                contributes++;
                if (contributes < 0) {
                    contributes = 1;
                }
            }
            objDetails.put(discipline, contributes);
            updatePrimaryDiscipline(objDetails);
        }
    }

    @Transient
    public void removeDisciplineContribute(String discipline) {
        if (discipline != null && !"".equals(discipline)) {
            JSONObject objDetails = null;
            try {
                objDetails = new JSONObject(this.details);
            } catch (Exception e) {
                objDetails = new JSONObject();
            }
            int contributes = 0;
            if (objDetails.has(discipline)) {
                contributes = objDetails.getInt(discipline);
                contributes--;
                if (contributes < 0) {
                    contributes = 0;
                }
            }
            objDetails.put(discipline, contributes);
            updatePrimaryDiscipline(objDetails);
        }
    }

    @Transient
    private void updatePrimaryDiscipline(JSONObject objDetails) {
        int maxValue = 0;
        Iterator<?> keys = objDetails.keys();
        while (keys.hasNext()) {
            try {
                String discipline = (String) keys.next();
                int actualValue = objDetails.getInt(discipline);
                if (actualValue > maxValue) {
                    this.primaryDiscipline = discipline;
                    maxValue = actualValue;
                }
            } catch (Exception e) {
                continue;
            }
        }

        this.details = objDetails.toString();
    }

    public String toString(int decimalPrecision) {
        JSONObject obj = new JSONObject();

        obj.put("id", id);
        obj.put("expert", getExpert(decimalPrecision));
        obj.put("formalAuthority", getFormalAuthority(decimalPrecision));
        obj.put("personalModel", getPersonalModel(decimalPrecision));
        obj.put("facilitator", getFacilitator(decimalPrecision));
        obj.put("delegator", getDelegator(decimalPrecision));
        obj.put("avgContributors", avgContributors);
        obj.put("primaryDiscipline", primaryDiscipline);
        obj.put("details", details);

        return obj.toString();
    }
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();

        obj.put("id", id);
        obj.put("expert", getExpert());
        obj.put("formalAuthority", getFormalAuthority());
        obj.put("personalModel", getPersonalModel());
        obj.put("facilitator", getFacilitator());
        obj.put("delegator", getDelegator());
        obj.put("avgContributors", avgContributors);
        obj.put("primaryDiscipline", primaryDiscipline);
        obj.put("details", details);

        return obj;
    }
    public void fromJSONObject(JSONObject obj) throws Exception{
        try {
            this.setId(obj.getLong("id"));
            this.setExpert(obj.getDouble("expert"));
            this.setFormalAuthority(obj.getDouble("formalAuthority"));
            this.setPersonalModel(obj.getDouble("personalModel"));
            this.setFacilitator(obj.getDouble("facilitator"));
            this.setDelegator(obj.getDouble("delegator"));
            this.setAvgContributors(obj.getInt("avgContributors"));
            this.setPrimaryDiscipline(obj.getString("primaryDiscipline"));
            this.setDetails(obj.getString("details"));
        } catch (Exception e) {
            throw e;
        }
    }

    private double formatDouble(double value, int decimalPrecision) {

        DecimalFormatter df = new DecimalFormatter(decimalPrecision);
        value = df.format(value);
        return value;
    }
}

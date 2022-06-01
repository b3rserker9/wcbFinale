/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.persistence.model;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.json.JSONObject;

/**
 *
 * @author J-HaRd
 */
@Entity
@Table(name = "statistics")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    private String emailUser;
    
    private Long idCourse;
    
    private Long idTopic;
    
    //@Temporal(javax.persistence.TemporalType.DATE)
    private Timestamp insertDate;
    
    @Lob
    private byte[] serializedStat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getSerializedStat() {
        return serializedStat;
    }

    public void setSerializedStat(byte[] serializedStat) {
        this.serializedStat = serializedStat;
    }
    
    public Timestamp getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Timestamp insertDate) {
        this.insertDate = insertDate;
    }

    public String getIdUser() {
        return emailUser;
    }

    public void setIdUser(String idUser) {
        this.emailUser = idUser;
    }

    public Long getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(Long idCourse) {
        this.idCourse = idCourse;
    }

    public Long getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Long idTopic) {
        this.idTopic = idTopic;
    }
    
    
    
    
    
    public String getStat(){
        if(this.getSerializedStat()!=null){
            return new String(this.getSerializedStat());
        }
        else{
            return null;
        }
    }
    
    public void setStat(String stat){
        if(stat!=null){
            this.setSerializedStat(stat.getBytes());
        }
        else{
            this.setSerializedStat(null);
        }
    }
    
    public void setFromJSON(JSONObject jo) {
        try {
            JSONObject st = jo.getJSONObject("stat");
            this.type = jo.getString("type");
            this.idCourse = st.getLong("courseId");
            this.idTopic = st.getLong("topicId");
            this.emailUser = st.getString("userEmail");
            this.setStat(st.toString());
        } catch (Exception e) {
            System.out.println("setFromJSON 0 - " + e.toString());
            try {
                JSONObject st = jo.getJSONObject("stat");
                this.type = jo.getString("type");
                this.idCourse = Long.parseLong(st.getString("courseId"));
                this.idTopic = Long.parseLong(st.getString("topicId"));
                this.emailUser = st.getString("userEmail");
                this.setStat(st.toString());
            } catch (Exception e1) {
                System.out.println("setFromJSON 1 - " + e1.toString());
                try {
                    JSONObject st = jo.getJSONObject("stat");
                    this.type = jo.getString("type");
                    this.idCourse = new Long(-1);
                    this.idTopic = new Long(-1);
                    this.emailUser = st.getString("userEmail");
                    this.setStat(st.toString());
                } catch (Exception e2) {
                    System.out.println("setFromJSON 2 - " + e2.toString());
                }
            }
        }
    }
    public JSONObject getJSON(){
        JSONObject jo = new JSONObject();
        jo.put("type", this.type);
        jo.put("stat", this.getStat());
        return jo;
    }
        

    
    
}

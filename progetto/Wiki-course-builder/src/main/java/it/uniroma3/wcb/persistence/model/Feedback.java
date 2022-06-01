/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.persistence.model;

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
@Table(name = "feedback", indexes = {
    @Index(name = "indx_type", columnList = "type", unique = false)})
public class Feedback {
   


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "type", nullable = false)
    private String type;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date insertDate;
    
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
    
    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
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
    
    public void setFromJSON(JSONObject jo){
        try{
            this.type=jo.getString("type");
            this.setStat(jo.getJSONObject("feed").toString());
        }catch(Exception e){}
    }
    public JSONObject getJSON(){
        JSONObject jo = new JSONObject();
        jo.put("type", this.type);
        jo.put("feed", this.getStat());
        return jo;
    }       
    
}

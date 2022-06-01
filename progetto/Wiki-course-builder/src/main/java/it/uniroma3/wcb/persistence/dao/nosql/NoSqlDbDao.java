/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.persistence.dao.nosql;


import it.uniroma3.wcb.persistence.model.Page;
import it.uniroma3.wcb.persistence.model.PageTeachingStyle;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author J-HaRd
 */
public interface NoSqlDbDao {
    
    public String getLanguage();
    
    public Map<String,Page> getWikipediaMapPages(Set<String> titles);
    public List<Page> getWikipediaPages(Set<String> titles);
    public Page getWikipediaPage(String title);
    public void saveWikipediaPage(Page page);
    public void saveWikipediaPageTS(String title, PageTeachingStyle ts);
    public void saveWikipediaPageText(String title, byte[] text);
    public void removePage(String title);
    public void closeConnection();
}

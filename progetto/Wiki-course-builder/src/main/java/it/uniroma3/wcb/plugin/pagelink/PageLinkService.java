/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.plugin.pagelink;

import java.util.Set;
import org.json.JSONObject;

/**
 *
 * @author J-HaRd
 */
public interface PageLinkService {
    
    public JSONObject callPageLinkService(String title1, String title2, String lang);
    public JSONObject callPageLinkAllService(String titles, String lang);
    
}

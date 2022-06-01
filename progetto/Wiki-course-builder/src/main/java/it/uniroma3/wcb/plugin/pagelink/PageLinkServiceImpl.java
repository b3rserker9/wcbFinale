/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.plugin.pagelink;

import it.uniroma3.wcb.nospring.configs.ExtraConfig;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.json.JSONObject;

/**
 *
 * @author J-HaRd
 */
//@Service("PageLinkService")
public class PageLinkServiceImpl implements PageLinkService{

  
    private ExtraConfig config;
    
    @Override
    public JSONObject callPageLinkService(String title1, String title2, String lang){
        config=ExtraConfig.getIstance();
        if(config.pluginPageLinkMode()>0){
            try {
                URL pl = new URL(config.PluginPageLinkURL()+"/pageLink?title1="+URLEncoder.encode(title1, "UTF-8")+"&title2="+URLEncoder.encode(title2, "UTF-8")+"&lang="+URLEncoder.encode(lang, "UTF-8"));
                URLConnection yc = pl.openConnection();
                String sobj="";
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                yc.getInputStream()));
                String inputLine;
                
                while ((inputLine = in.readLine()) != null){
                    sobj+=inputLine;
                }
                in.close();
                JSONObject jo = new JSONObject(sobj);
                return jo;
            } catch (Exception ex) {
                JSONObject jo = new JSONObject();
                jo.put("result", -9);
                jo.put("log", "PageLinkPlugin is Active in wcb but plugin has not respond correctly");
                jo.put("time", "0");
                return jo;
            }
        }
        else{
            JSONObject jo = new JSONObject();
            jo.put("result", -10);
            jo.put("log", "PageLinkPlugin is Inactive in wcb");
            jo.put("time", "0");
            return jo;
        }
        
    }

    @Override
    public JSONObject callPageLinkAllService(String titles, String lang) {
        config=ExtraConfig.getIstance();
        if(config.pluginPageLinkMode()>0){
            try {
                String ts=titles;
                /*
                for(String title:titles){
                    ts+=title+",";
                }
*/
                URL pl = new URL(config.PluginPageLinkURL()+"/pageLinkAll?titles="+URLEncoder.encode(ts, "UTF-8")+"&lang="+URLEncoder.encode(lang, "UTF-8"));
                
                URLConnection yc = pl.openConnection();
                String sobj="";
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                yc.getInputStream()));
                String inputLine;
                
                while ((inputLine = in.readLine()) != null){
                    sobj+=inputLine;
                }
                in.close();
                JSONObject jo = new JSONObject(sobj);
                return jo;
            } catch (Exception ex) {
                JSONObject jo = new JSONObject();
                jo.put("result", -9);
                jo.put("log", "PageLinkPlugin is Active in wcb but plugin has not respond correctly");
                jo.put("time", "0");
                return jo;
            }
        }
        else{
            JSONObject jo = new JSONObject();
            jo.put("result", -10);
            jo.put("log", "PageLinkPlugin is Inactive in wcb");
            jo.put("time", "0");
            return jo;
        }
    }
    
    
    
}

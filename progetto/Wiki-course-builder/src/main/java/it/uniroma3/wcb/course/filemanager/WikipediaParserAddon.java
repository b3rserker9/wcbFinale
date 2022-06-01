/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.course.filemanager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author J-HaRd
 * 
 * Addon for WikiMediaParser
 */
public class WikipediaParserAddon {

    public WikipediaParserAddon() {
    }
    
    
    
    
    public String cleanText(String text){
                
        
        //per => TEMPLATE[testo seza nodificazione di parQuadre]
        Pattern p1 = Pattern.compile("TEMPLATE\\[[\\w\\s\\,\\=\\(\\)\\#\\:\\;\\§\\.\\<\\>\\?\\/\\\"\\!\\£\\$\\%\\&\\^\\+\\*\\@\\-\\']*\\]");  
        //per => [testo seza nodificazione di parQuadre]
        Pattern p2 = Pattern.compile("\\[[\\w\\s\\,\\=\\(\\)\\#\\:\\;\\§\\.\\<\\>\\?\\/\\\"\\!\\£\\$\\%\\&\\^\\+\\*\\@\\-\\']*\\]"); 
        //per => thumb|xxx|xxx| oppure thumb|xxx| oppure thumb| :dove xxx è una stringa qualsiasi 
        Pattern p3 = Pattern.compile("(thumb\\|\\w*\\|\\w*\\|)|(thumb\\|\\w*\\|)|(thumb\\|)");   
        //per => (/ xxxxx /) le pronunce
        Pattern p4 = Pattern.compile("\\(\\/[\\w\\s\\,\\=\\(\\)\\#\\:\\;\\§\\.\\<\\>\\?\\/\\\"\\!\\£\\$\\%\\&\\^\\+\\*\\@\\-\\']*\\/\\)");    
        //per i spazi
        Pattern ps = Pattern.compile("\\&nbsp;|=");
        
               
        
        
        String cleanText=text+"";
        
        for (int x = 0; x < 3; x++) {
            Matcher m1 = p1.matcher(cleanText);
            cleanText = m1.replaceAll("");
            Matcher m2 = p2.matcher(cleanText);
            cleanText = m2.replaceAll("");
            
        }
        Matcher m3 = p3.matcher(cleanText);
        cleanText = m3.replaceAll("");
        
        Matcher m4 = p4.matcher(cleanText);
        cleanText = m4.replaceAll("");
        
        Matcher ms = ps.matcher(cleanText);
        cleanText = ms.replaceAll(" ");
  
        String trim = cleanText.trim();
        if(trim.equalsIgnoreCase("TEMPLATE")){
            trim="";
        }
        return trim;
    }
    
    
}

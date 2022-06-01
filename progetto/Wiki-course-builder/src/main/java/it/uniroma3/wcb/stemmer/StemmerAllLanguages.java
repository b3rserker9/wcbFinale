/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.stemmer;

//import it.uniroma3.wcb.stemmer.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.SnowballStemmer;
//import org.tartarus.snowball.ext.*;
import java.util.HashSet;
import java.util.TreeSet;
import org.openide.util.Exceptions;
import org.wikipedia.miner.util.text.StopwordRemover;

/**
 *
 * @author J-HaRd
 */
public class StemmerAllLanguages extends Stemmer {

    private String stemmerType;

    public StemmerAllLanguages() {
        super();
        stemmerType = "english";
    }

    public StemmerAllLanguages(HashSet<String> stopWords, TreeSet<String> sectionsToSkip, int ttr) {
        super(stopWords, sectionsToSkip, ttr);
        stemmerType = "english";
    }

    public StemmerAllLanguages(String lang) {
        super();

        switch (lang) {
            case "it":
                stemmerType = "italian";
                break;
            case "de":
                stemmerType = "german";
                break;
            case "nl":
                stemmerType = "english";
                break;
            case "fr":
                stemmerType = "french";
                break;
            case "sv":
                stemmerType = "swedish";
                break;
            case "es":
                stemmerType = "spanish";
                break;
            case "ru":
                stemmerType = "russian";
                break;
            case "pl":
                stemmerType = "english";
                break;
            case "war":
                stemmerType = "english";
                break;
            case "ceb":
                stemmerType = "english";
                break;
            case "ja":
                stemmerType = "english";
                break;
            case "vi":
                stemmerType = "english";
                break;
            case "en":
                stemmerType = "porter";
                //stemmerType = "english";
                break;
            case "en2":
                //stemmerType = "porter";
                stemmerType = "english";
                break;
            default:
                stemmerType = "english";
                break;
        }

    }

    public StemmerAllLanguages(String lang, HashSet<String> stopWords, TreeSet<String> sectionsToSkip, int ttr) {
        super(stopWords, sectionsToSkip, ttr);
        switch (lang) {
            case "it":
                stemmerType = "italian";
                break;
            case "de":
                stemmerType = "german";
                break;
            case "nl":
                stemmerType = "english";
                break;
            case "fr":
                stemmerType = "french";
                break;
            case "sv":
                stemmerType = "swedish";
                break;
            case "es":
                stemmerType = "spanish";
                break;
            case "ru":
                stemmerType = "russian";
                break;
            case "pl":
                stemmerType = "english";
                break;
            case "war":
                stemmerType = "english";
                break;
            case "ceb":
                stemmerType = "english";
                break;
            case "ja":
                stemmerType = "english";
                break;
            case "vi":
                stemmerType = "english";
                break;
            case "en":
                stemmerType = "porter";
                //stemmerType = "english";
                break;
            case "en2":
                //stemmerType = "porter";
                stemmerType = "english";
                break;
            default:
                stemmerType = "english";
                break;
        }
    }

    @Override
    public String stem(String text) {
        
        StopwordRemover swr = new StopwordRemover(this.stopWords);
        String cleaned = swr.processText(text.toLowerCase());
            
        try {
            
            Class stemClass = Class.forName("org.tartarus.snowball.ext." + this.stemmerType + "Stemmer");
            SnowballStemmer stemmer = (SnowballStemmer) stemClass.newInstance();
            stemmer.setCurrent(cleaned);
            stemmer.stem();
            String stemmed = stemmer.getCurrent();
            return stemmed;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
        }
        return cleaned;
    }

}

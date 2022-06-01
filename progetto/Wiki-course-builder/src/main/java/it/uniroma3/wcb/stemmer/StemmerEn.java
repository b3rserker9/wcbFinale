/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.uniroma3.wcb.stemmer;

import java.util.HashSet;
import java.util.TreeSet;
import org.wikipedia.miner.util.text.PorterStemmer;
import org.wikipedia.miner.util.text.StopwordRemover;

/**
 *
 * @author J-HaRd
 */
public class StemmerEn extends Stemmer {

    public StemmerEn() {
        super();
    }

    public StemmerEn(HashSet<String> stopWords, TreeSet<String> sectionsToSkip, int ttr) {
        super(stopWords, sectionsToSkip, ttr);
    }

    @Override
    public String stem(String text) {
        StopwordRemover swr = new StopwordRemover(this.stopWords);
        String cleaned = swr.processText(text);

        PorterStemmer st = new PorterStemmer();
        String stemmed = st.processText(cleaned);

        return stemmed;
    }
}

package it.uniroma3.wcb.util;

import it.uniroma3.wcb.course.Corpus;

import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class DocumentMapPrinter {
	
	public void printResults(Map<String, Map<String, Double>> processResultMap, Corpus corpus, String title){
		this.printResults(processResultMap, corpus, title, new PrintWriter(System.out, true));
	}
	
	public void printResults(Map<String, Map<String,Double>> processResultMap, Corpus corpus, String title, PrintWriter writer){
	    //retrieving all corpus terms
  		Set<String> allTerms = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
  		allTerms.addAll(corpus.getCorpusTermsCardinality().keySet());
	    
  		//retrieving all corpus documents
  		Set<String> allDocKeys= new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
  		allDocKeys.addAll(processResultMap.keySet());
  		
  		writer.printf("=== %s ===%n", title);
	    writer.printf("%15s", " ");
  		
  		for(String docKey : allDocKeys){
  			writer.printf("%8s", docKey);
  		}
	    writer.println();
	    
	    
	    for(String term : allTerms){
	    	writer.printf("%15s", term);
	    	for(String docKey : allDocKeys){
	    		if(processResultMap.get(docKey).containsKey(term)){
	    			writer.printf("%8.4f", processResultMap.get(docKey).get(term));
	    		}
	    		else{
	    			writer.printf("%8.4f", 0D);
	    		}
	    	}
	    	writer.println();
	    }
	    writer.println();
	    writer.println();
	    
	    writer.flush();
	}
}

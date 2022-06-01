package it.uniroma3.wcb.course;

import it.uniroma3.wcb.stemmer.Stemmer;
import it.uniroma3.wcb.stemmer.StemmerAllLanguages;
import it.uniroma3.wcb.stemmer.StemmerEn;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Corpus represents document collection on which apply ranking and sequencing algorithms.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita, Andrea Giardi
 */
public class Corpus {
	
	private Stemmer stemmer = null;
	private boolean applyStemming = false;
	
	//terms with their frequencies
	private Map<String, Map<String, Integer>> documentsTermsFrequencies = new LinkedHashMap<>();
	
	//this map counts how many documents (value) cointains a specific term (key)
	private Map<String, Integer> corpusTermsCardinality = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
	
	//this map counts how many times (value) a term (key) appears into corpus 
	private Map<String, Integer> corpusTermsOccurrencies = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

	//same as "corpusTermsCardinality", but limited by "limitCorpusTerms" function
	private Map<String, Integer> limitedCorpusTermsCardinality = null;
	
	//same as "corpusTermsOccurrencies", but limited by "limitCorpusTerms" function
	private Map<String, Integer> limitedCorpusTermsOccurrencies = null;
	
	//map of all documents added (key - text) 
	private Map<String, String> documentsMap = new LinkedHashMap<>();
	
	//document key/number of document words
	private Map<String, Integer> documentsWordsCounters = new LinkedHashMap<>();
	
	private String language;
	/**
	 * Default constructor. Use this if you don't want apply stemming to document's collection.
	 */
	public Corpus(String lang){
            language=lang;
            this.applyStemming = false;
	}

    public Stemmer getStemmer() {
        return stemmer;
    }

    public void setStemmer(Stemmer stemmer) {
        this.stemmer = stemmer;
    }
	
	
	/**
	 * Corpus constructor which enables stemming use.<br/> 
	 * <b>stopWords</b> and <b>sectionsToSkip</b>, which can be null or empty,
	 * are used to initialize Stemmer processor.
	 * <p>
	 * 
    
	 * @see Stemmer
	 *
         * @param lang language.
	 * @param stopWords  set of words to ignore during stemming phase.
	 * @param sectionsToSkip  wiki article sections to ignore. 
         * @param ttr text type rank
	 */
	public Corpus(String lang,HashSet<String> stopWords, TreeSet<String> sectionsToSkip, int ttr){
		if(stopWords==null)
			stopWords = new HashSet<>();
		if(sectionsToSkip==null)
			sectionsToSkip = new TreeSet<>();
                if(lang.equalsIgnoreCase("wikiporter")){
                    language="en";
                    this.stemmer=new StemmerEn(stopWords, sectionsToSkip, ttr);
                }
                else{
                    language=lang;
                    this.stemmer = new StemmerAllLanguages(lang,stopWords, sectionsToSkip, ttr);
                }
               
                
		this.applyStemming = true;
	}
	
	
	/**
	 * Add a new document to corpus collection.
	 * <p>
	 * 
	 * @param documentKey  document reference key
	 * @param text  document content
	 */
	public void addDocumentToCollection(String documentKey, String text){
		
		Map<String, Integer> documentTF = getTextTermFrequencies(text);
		
		int documentWords = 0;
		for(Map.Entry<String, Integer> tf : documentTF.entrySet()){
			if(tf.getValue().intValue() > 0){
				Integer gc = null;
				Integer gf = null;
				//updates global cardinality and occurrencies maps
				gc = corpusTermsCardinality.get(tf.getKey());
				gf = corpusTermsOccurrencies.get(tf.getKey());
				if(gc==null)
					gc = new Integer(0);
				if(gf==null)
					gf = new Integer(0);
				
				gc++;
				gf += tf.getValue();
				
				corpusTermsCardinality.put(tf.getKey(), gc);
				corpusTermsOccurrencies.put(tf.getKey(), gf);
				
				documentWords += tf.getValue().intValue();
			}
		}
		
		documentsTermsFrequencies.put(documentKey, documentTF);
		documentsMap.put(documentKey, text);
		documentsWordsCounters.put(documentKey, documentWords);
	}
	
	
	/**
	 * Returns terms/frequency map calculated on given text.
	 * <p>
	 * 
	 * @param text
	 * @return terms/frequency map
	 */
	public Map<String, Integer> getTextTermFrequencies(String text){
		
		Map<String, Integer> termsCounters = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		
		//text tokenization
		String[] tokens = null;
		
		if(this.applyStemming)
			tokens = this.stemmer.tokenizeAndStemText(text); 
		else
			tokens = text.split("\\s+");
		
		
		if(tokens!=null){
			for(String token : tokens){
				Integer wc = termsCounters.get(token);
				if(wc==null){
					wc = new Integer(0);
				}
				wc++;
				termsCounters.put(token, wc);
			}
			
		}
		return termsCounters;
	}
	
	
	/**
	 * Returns the map of all document's terms with their frequencies.
	 * <p>
	 * 
	 * @return (document key, (term, frequency)) map
	 */
	public Map<String, Map<String, Integer>> getDocumentsTermFrequencies() {
		return documentsTermsFrequencies;
	}
	

	/**
	 * Returns a map which counts how many documents (value) contain a specific term (key).
	 * <p>
	 * 
	 * @return (term, counter) map
	 */
	public Map<String, Integer> getCorpusTermsCardinality() {
		if(this.limitedCorpusTermsCardinality!=null)
			return this.limitedCorpusTermsCardinality;
		else
			return corpusTermsCardinality;
	}
	
	
	/**
	 *  Returns a map which counts how many times (value) a term (key) appears into corpus.
	 *  <p>
	 * 
	 * @return (term, counter) map
	 */
	public Map<String, Integer> getCorpusTermsOccurencies() {
		if(this.limitedCorpusTermsOccurrencies!=null)
			return this.limitedCorpusTermsOccurrencies;
		else
			return this.corpusTermsOccurrencies;
	}
	
	
	/**
	 * Returns the map of all documents added (key - text) 
	 * <p>
	 * 
	 * @return (document key, text) map
	 */
	public Map<String, String> getDocumentsMap() {
		return documentsMap;
	}
	

	/**
	 * Number of words for each corpus document.
	 * <p>
	 * 
	 * @return (document key, words count)
	 */
	public Map<String, Integer> getDocumentsWordsCounters() {
		return documentsWordsCounters;
	}
	
	
	/**
	 * Gives all corpus terms.
	 * Set depends on stop words list and stemming application.
	 * If corpus terms were limited by {@linkplain Corpus#limitCorpusTerms(int, int, String) limitCorpusTerms} method,
	 * will be returned a limited set.
	 * <p>
	 * 
	 * @return set of all terms, ascending order case insensitive.
	 */
	public Set<String> getAllCorpusTerms(){
		if(this.limitedCorpusTermsCardinality!=null)
			return this.limitedCorpusTermsCardinality.keySet();
		else
			return this.corpusTermsCardinality.keySet();
	}
	
	
	/**
	 * Invoke this method if you want to limit corpus terms size (for performance/stability reasons).
	 * Less frequent terms will be discarded.
	 * <p>
	 * This method works on documents yet collected; if the dimension of corpus change, invoke this
	 * method again.
	 * <p>
	 * 
	 * @param limit  max number of terms to preserve
	 * @param skipTermsShorterThan  skip all words smaller than given size
	 * @param textToPreseve  term obtained by this text tokenization will be preserved 
	 */
	public void limitCorpusTerms(int limit, int skipTermsShorterThan, String textToPreseve){
		
		if(limit<=0)
			return;
		if(textToPreseve == null)
			textToPreseve="";
		
		//used to order term by frequency value
		Map<Double, Set<String>> reverseIndexes = new TreeMap<>(Collections.reverseOrder());
		
		for(Map.Entry<String, Integer> corpusTermCardinality : this.corpusTermsCardinality.entrySet()){
			
			double key = this.corpusTermsOccurrencies.get(corpusTermCardinality.getKey()).doubleValue() / corpusTermCardinality.getValue().doubleValue();
			
			Set<String> set = reverseIndexes.get(key);
			if(set == null)
				set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
			set.add(corpusTermCardinality.getKey());
			
			reverseIndexes.put(key, set);
		}
		
		this.limitedCorpusTermsCardinality = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		this.limitedCorpusTermsOccurrencies = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		int counter = 0;
		
		if(textToPreseve!=null && !"".equals(textToPreseve.trim())){
			String[] textTokens = null;
			
			if(this.applyStemming)
				textTokens = this.stemmer.tokenizeAndStemText(textToPreseve); 
			else
				textTokens = textToPreseve.split("\\s+");
			
			for(String textToken : textTokens){
				if(this.corpusTermsCardinality.containsKey(textToken)){
					this.limitedCorpusTermsCardinality.put(textToken, this.corpusTermsCardinality.get(textToken));
					this.limitedCorpusTermsOccurrencies.put(textToken, this.corpusTermsOccurrencies.get(textToken));
					counter ++;
				}
			}
		}
		
		for(Map.Entry<Double, Set<String>> index : reverseIndexes.entrySet()){
			for(String value : index.getValue()){
				if(skipTermsShorterThan<=0 || value.length()>skipTermsShorterThan){
					if(counter >= limit)
						return;
					
					this.limitedCorpusTermsCardinality.put(value, this.corpusTermsCardinality.get(value));
					this.limitedCorpusTermsOccurrencies.put(value, this.corpusTermsOccurrencies.get(value));
					
					counter ++;	
				}
			}
		}
	}
	
	
	/**
	 * Print on stdout of terms/document frequency map.
	 * Used only for test.
	 */
	public void printResults(String title){
		this.printResults(title, new PrintWriter(System.out, true));
	}
	
	
	/**
	 * Print on given writer of terms/document frequency map.
	 * Used for only test.
	 */
	public void printResults(String title, PrintWriter writer){
	    //retrieving all corpus terms
  		Set<String> allTerms = this.getAllCorpusTerms();
	    
  		//retrieving all corpus document keys
  		Set<String> allDocKeys= new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
  		allDocKeys.addAll(this.documentsTermsFrequencies.keySet());
  		
  		writer.printf("=== %s ===%n", title);
	    writer.printf("%15s", " ");
  		
  		for(String docKey : allDocKeys){
  			writer.printf("%8s", docKey);
  		}
	    writer.println();
	    
	    
	    for(String term : allTerms){
	    	writer.printf("%15s", term);
	    	for(String docKey : allDocKeys){
	    		if(this.documentsTermsFrequencies.get(docKey).containsKey(term)){
	    			writer.printf("%8.4f", this.documentsTermsFrequencies.get(docKey).get(term).doubleValue());
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

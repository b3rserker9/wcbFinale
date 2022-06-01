package it.uniroma3.wcb.course.ranking;

import it.uniroma3.wcb.course.Corpus;
import it.uniroma3.wcb.util.DecimalFormatter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class InformationGainProcessor extends RankProcessor{

	//contains all documents terms/weights map
	private Map<String, Map<String,Double>> documentsTermsWeights;
	
	//contains all terms entropies of collection
	private Map<String, Double> termsEntropy;
	
	private boolean initialized = false;
		
	public InformationGainProcessor(Corpus corpus, int decimalPrecision) {
		super(corpus, decimalPrecision);
		this.initialized = false;
	}

	/**
	 * Calculate cosine similarity between all corpus documents and given query.
	 * 
	 * @param query
	 * @return map (document key, cosine similarity score)
	 */
	@Override
	public Map<String, Double> scoreDocuments(String query) throws Exception {
		
		if(!this.initialized){
			this.init();
		}
		
		Map<String, Double> results = new LinkedHashMap<>();
		
		//query 
		Map<String,Double> queryIG = this.calculateTextWeights(query);
		
		CosineSimilarity cosSimProcessor = new CosineSimilarity(this.getDecimalPrecision());
		
		for(Map.Entry<String, Map<String,Double>> documentTermsWeights : this.documentsTermsWeights.entrySet()){
			Double cosineSimilarity = cosSimProcessor.calculate(queryIG, documentTermsWeights.getValue());
			results.put(documentTermsWeights.getKey(), cosineSimilarity);
		}
		
		return results;
	}
	
	/**
	 * Information Gain elaboration based on corpus documents.
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception{
		
		try{
			this.calculateCorpusIG();
		    this.initialized = true;
		    
		}catch(Exception e){
			this.initialized = false;
			throw new Exception("Error while initializing Information Gain Processor. ",e);
		}
	}
	
	/**
	 *  Calculation of Log-entropy for all corpus documents terms.
	 */
	private void calculateCorpusIG(){
		
		this.documentsTermsWeights = new LinkedHashMap<>();
		
		//for g{i} calculation
		this.calculateCorpusEntropy();
		
		//for tf{i,j} calculation
		Map<String, Map<String, Integer>> documentsTermFrequencies = this.getCorpus().getDocumentsTermFrequencies();
		
		for(Map.Entry<String, Map<String, Integer>> documentTermFrequencies : documentsTermFrequencies.entrySet()){
			//a{i,j} = g{i} * log(tf{i,j} + 1)
			Map<String, Double> termsWeights = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
			
			for(Map.Entry<String, Integer> termFrequency : documentTermFrequencies.getValue().entrySet()){
				try{
					double weight= this.calculateWeight(termFrequency.getValue().doubleValue(), this.termsEntropy.get(termFrequency.getKey()).doubleValue());
					termsWeights.put(termFrequency.getKey(), new Double(weight));
				}catch(Exception e){
					termsWeights.put(termFrequency.getKey(), new Double(0));
				}
			}
			
			this.documentsTermsWeights.put(documentTermFrequencies.getKey(), termsWeights);
		}
	}
	
	/**
	 * Entropy calculation for all terms of corpus.
	 */
	private void calculateCorpusEntropy(){
		
		this.termsEntropy = new TreeMap<String, Double>(String.CASE_INSENSITIVE_ORDER);
		
		//for tf{i,j} calculation
		Map<String, Map<String, Integer>> documentsTermFrequencies = this.getCorpus().getDocumentsTermFrequencies();
				
		//for gf{i} calculation
		Map<String, Integer> corpusTermsOccurencies = this.getCorpus().getCorpusTermsOccurencies();
		
		int n = this.getCorpus().getDocumentsMap().size();
		
		for(String term : this.getCorpus().getAllCorpusTerms()){
			
			double sum = 0.0d;
			
			double gfi = corpusTermsOccurencies.get(term).doubleValue();
			
			for(Map.Entry<String, Map<String, Integer>> documentTermFrequencies : documentsTermFrequencies.entrySet()){
				
				Integer tfij = documentTermFrequencies.getValue().get(term);
				if(tfij!=null){
					sum += calculateDocumentEntropy(tfij.doubleValue(), gfi, n);	
				}
			}	
			
			double gi = 1+sum;
			
			this.termsEntropy.put(term, new Double(gi));
		}
	}
	
	/**
	 * calculate (p{i,j} * log p{i,j}) / log n
	 * where p{i,j} = tf{i,j} / gf{i} 
	 * 
	 * @param tfij - frequency of term i for document j
	 * @param gfi - occurrencies of term i for given collection
	 * @param n - number of documents of given collection
	 * @return g{i,j}
	 */
	private double calculateDocumentEntropy(double tfij, double gfi, int n){
		if(n==0)
			return 0.0d;
		try{
			double pij = tfij/gfi;
			return (pij * Math.log10(pij)) / Math.log10((double)n);
	
		}catch(Exception e){
			return 0.0d;
		}
	}
	
	/**
	 * calculate weight of term i for document j as:
	 * w{i,j} = g{i} * log(tf{i,j} + 1)
	 * 
	 * @param tfij - frequency of term i for document j
	 * @param gi - entropy of term i
	 * @return w{i,j}
	 */
	private double calculateWeight(double tfij, double gi){
		double weight = 0.0d;
		
		try{
			weight = gi * Math.log10(tfij + 1);
		}catch(Exception e){
			return 0.0d;
		}
		
		DecimalFormatter df = new DecimalFormatter(this.getDecimalPrecision());
    	weight = df.format(weight);
    	
		return weight;
	}

	/**
	 * Calculation of Information gain for all corpus documents.
	 * 
	 * @return map (document key, (term, ig score))
	 * @throws Exception 
	 */
	@Override
	public Map<String, Map<String, Double>> getWeightedMap() throws Exception {
		if(!this.initialized){
			this.init();
		}
		
		return this.documentsTermsWeights;
	}

	/**
	 * Calculate terms weights map based on the given text.
	 * 
	 * @param text
	 * @return map (term, ig score)
	 * @throws Exception 
	 */
	@Override
	public Map<String, Double> calculateTextWeights(String text) throws Exception {
		if(!this.initialized){
			this.init();
		}
		
		Map<String, Double> termsWeights = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		
		//for tf{i,j} calculation
		Map<String, Integer> textTermsFrequencies = this.getCorpus().getTextTermFrequencies(text);
		
		for(Map.Entry<String, Integer> termFrequency : textTermsFrequencies.entrySet()){
			try{
				double weight= this.calculateWeight(termFrequency.getValue().doubleValue(), this.termsEntropy.get(termFrequency.getKey()).doubleValue());
				termsWeights.put(termFrequency.getKey(), new Double(weight));
			}catch(Exception e){
				termsWeights.put(termFrequency.getKey(), new Double(0));
			}
		}
		
		return termsWeights;
	}
}

package it.uniroma3.wcb.course.ranking;

import it.uniroma3.wcb.course.Corpus;
import it.uniroma3.wcb.util.DecimalFormatter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class TfIdfProcessor extends RankProcessor{
	
	//contains all documents terms/tf-idf map
	private Map<String, Map<String,Double>> documentsTfIdf;
	//contains idf score for each term of corpus
	private Map<String, Double> termsIdf;
	
	private boolean useWeightedTFFormula = true;
	private boolean initialized = false;
	
	
	public TfIdfProcessor(Corpus corpus, int decimalPrecision){
		this(corpus, decimalPrecision, true);
	}
	
	public TfIdfProcessor(Corpus corpus, int decimalPrecision, boolean useWeightedTFFormula){
		super(corpus, decimalPrecision);
		this.useWeightedTFFormula = useWeightedTFFormula;
		this.initialized = false;
	}
	
	/**
	 * TF-IDF elaboration based on corpus documents.
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception{
		
		try{
			this.calculateCorpusTfIdf();
			
		    this.initialized = true;
		    
		}catch(Exception e){
			this.initialized = false;
			throw new Exception("Error while initializing TfIdfProcessor. ",e);
		}
	}
	
	/**
	 * Calculate cosine similarity between all corpus documents and given query.
	 * 
	 * @param query
	 * @return map (document key, cosine similarity score)
	 */
	@Override
	public Map<String, Double> scoreDocuments(String query) throws Exception{
		
		if(!this.initialized){
			this.init();
		}
		
		Map<String, Double> results = new LinkedHashMap<>();
		
		//query tf-idf
		Map<String,Double> queryTfIdf = this.calculateTextWeights(query);
		
		CosineSimilarity cosSimProcessor = new CosineSimilarity(this.getDecimalPrecision());
		
		for(Map.Entry<String, Map<String,Double>> documentTfIdf : this.documentsTfIdf.entrySet()){
			Double tfIdfCosineSimilarity = cosSimProcessor.calculate(queryTfIdf, documentTfIdf.getValue());
			results.put(documentTfIdf.getKey(), tfIdfCosineSimilarity);
		}
		
		return results;
	}
	
	/**
	 * Calculation of TF-IDF for all corpus documents.
	 * 
	 * @return map (document key, (term, tf-idf score))
	 * @throws Exception 
	 */
	@Override
	public Map<String, Map<String,Double>> getWeightedMap() throws Exception{
		
		if(!this.initialized){
			this.init();
		}
		
		return this.documentsTfIdf;
	}

	/**
	 * Calculation of TF for all corpus documents.
	 */
	private Map<String, Map<String, Double>> calculateCorpusTF(){
		Map<String, Map<String, Double>> documentsTermFrequencies = new LinkedHashMap<>();
		
		for(Map.Entry<String, Map<String, Integer>> docTermsFrequencies : this.getCorpus().getDocumentsTermFrequencies().entrySet()){
			
			Map<String, Double> tfMap = new HashMap<>();
			
			for(Map.Entry<String, Integer> termFrequency : docTermsFrequencies.getValue().entrySet()){
				
				double tf;
				if(this.useWeightedTFFormula){
					tf = calculateWeightedTF(termFrequency.getValue().doubleValue());
				}
				else{
					tf = calculateTF(termFrequency.getValue().doubleValue(), this.getCorpus().getDocumentsWordsCounters().get(docTermsFrequencies.getKey()));
				}
				
				tfMap.put(termFrequency.getKey(), new Double(tf));
			}
			documentsTermFrequencies.put(docTermsFrequencies.getKey(), tfMap);
		}
		
		return documentsTermFrequencies;
	}
	
	
	/**
	 * Calculate frequency of term t{i} of document d{j}.
	 * 
	 * tf{i,j} = n{i,j} / |d{j}|
	 * 
	 * @param nij number of occurrences of term t{i} for document d{j}
	 * @param dj number of terms of document d{j}.
	 * @return 
	 */
	private double calculateTF(double nij, Integer dj){
		if(dj == null || dj==0)
			return 0;
		
		double tf = (double)(nij / dj);
		return tf;
	}
	
	/**
	 * Calculate frequency of term t{i}, log normalized, of document d{j}.
	 * 
	 * tf{i,j} = log(1+ n{i,j})
	 * 
	 * @param nij number of occurrences of term t{i} for document d{j}
	 * @return 
	 */
	private double calculateWeightedTF(double nij){
		double tf = Math.log(1+nij); 
		return tf;
	}
	
	/**
	 * Terms IDF calculation based on given corpus.
	 * 
	 * @param totalResults
	 * @param globalWordCounters
	 * @return
	 */
	private Map<String, Double> calculateTermsIDF(int totalResults , Map<String, Integer> globalWordCounters){
		
		Map<String, Double> calculatedIdf = new HashMap<>();
		
		for(Map.Entry<String, Integer> gwc : globalWordCounters.entrySet()){
			
			int ti = gwc.getValue().intValue();
			if(ti>0){
				double fun = (double)totalResults / (double)ti;
				double wIdf = Math.log10(1 + fun);
				
				calculatedIdf.put(gwc.getKey(), new Double(wIdf));
			}
			else{
				calculatedIdf.put(gwc.getKey(), new Double(0));
			}
		}
		return calculatedIdf;
	}
	
	/**
	 *  Calculation of TF-IDF for all corpus documents.
	 */
	private void calculateCorpusTfIdf(){
		//Term Frequency calculation TF
		Map<String, Map<String, Double>> documentsTermFrequencies = this.calculateCorpusTF();
		
		//calcolo degli idf
		this.termsIdf = calculateTermsIDF(this.getCorpus().getDocumentsMap().size(), this.getCorpus().getCorpusTermsCardinality());
		
		//results
		this.documentsTfIdf = new LinkedHashMap<>();
				
		for(String documentKey : this.getCorpus().getDocumentsMap().keySet()){
			Map<String,Double> documentTfIdf = calculateDocumentTfIdf(documentsTermFrequencies.get(documentKey), this.termsIdf);
			this.documentsTfIdf.put(documentKey, documentTfIdf);
		}
	}
	
	/**
	 * TF-IDF calculation for the given document.
	 * 
	 * @param tf - term frequency map - (term, TF value)
	 * @param idf - idf of all terms of given collection
	 * @return map (term, tf-idf score)
	 */
	private Map<String, Double> calculateDocumentTfIdf(Map<String, Double> tf, Map<String, Double> idf){
		
		Map<String, Double> termsTfIdf = new HashMap<>();
		
		for(String term : tf.keySet()){
			if(idf.containsKey(term)){
				double tfIdf = (tf.get(term).doubleValue() * idf.get(term).doubleValue());
				
				DecimalFormatter df = new DecimalFormatter(this.getDecimalPrecision());
		    	tfIdf = df.format(tfIdf);
		    	
				termsTfIdf.put(term, tfIdf);
			}
		}
		
		return termsTfIdf;
	}
	
	/**
	 * Calculate terms tf-idf map based on the given text.
	 * 
	 * @param text
	 * @return map (term, tf-idf score)
	 * @throws Exception 
	 */
	@Override
	public Map<String, Double> calculateTextWeights(String text) throws Exception{
		if(!this.initialized){
			this.init();
		}
		
		//tf calculation
		Map<String, Double> tfMap = new HashMap<>();
		
		Map<String, Integer> textTermsFrequencies = this.getCorpus().getTextTermFrequencies(text);
		
		int counter = 0;
		if(!this.useWeightedTFFormula){
			for(Integer freq : textTermsFrequencies.values())
				counter += freq.intValue();
		}
		
		for(Map.Entry<String, Integer> termFrequency : textTermsFrequencies.entrySet()){
			
			double tf;
			if(this.useWeightedTFFormula){
				tf = calculateWeightedTF(termFrequency.getValue().doubleValue());
			}
			else{
				tf = calculateTF(termFrequency.getValue().doubleValue(), counter);
			}
			
			tfMap.put(termFrequency.getKey(), new Double(tf));
		}
		
		return calculateDocumentTfIdf(tfMap, this.termsIdf);
	}
	
	
	/*
	 public double getTfIdfTermsScore(Map<String, Double> documentTfIdf, List<String> terms){
		
		List<String> normalizedTerms = this.corpus.normalizeTermsCollection(terms);
		
		double tfIdf = 0.0;
		
		for(String term : normalizedTerms){
			if(documentTfIdf.containsKey(term)){
				tfIdf += documentTfIdf.get(term).doubleValue();
			}
		}
		
		return tfIdf;
	}
	*/
}

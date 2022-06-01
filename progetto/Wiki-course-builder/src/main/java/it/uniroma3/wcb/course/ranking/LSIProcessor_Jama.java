package it.uniroma3.wcb.course.ranking;

import it.uniroma3.wcb.course.Corpus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Jama.Matrix;
import Jama.SingularValueDecomposition;

/**
 * LSI Processor based on Jama libraries.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class LSIProcessor_Jama extends RankProcessor{
	
	private RankProcessor weightProcessor;
	
	//k
	private int maxMatrixReductionRatio;
	
	private boolean initialized = false; 
	
	//reduced terms matrix (Terms * k)
	private Matrix Uk = null;
	//reduced Sigma matrix (k * k)
	private Matrix Sk = null;
	//reduced and transposed document matrix (k * Documents)
	private Matrix VTk = null;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * LSI map elaborated on term frequencies of all corpus documents.
	 * 
	 * @param corpus
	 * @param maxMatrixReductionRatio
	 * @param decimalPrecision
	 * 
	 */
	public LSIProcessor_Jama(Corpus corpus, int maxMatrixReductionRatio, int decimalPrecision){
		super(corpus, decimalPrecision);
		this.maxMatrixReductionRatio = maxMatrixReductionRatio;
		this.initialized = false;
	}
	
	/**
	 * LSI map elaborated on terms weight calculated by given weight-processor.
	 * 
	 * @param corpus
	 * @param maxMatrixReductionRatio
	 * @param decimalPrecision
	 * @param weightProcessor
	 * 
	 */
	public LSIProcessor_Jama(Corpus corpus, int maxMatrixReductionRatio, int decimalPrecision, RankProcessor weightProcessor){
		super(corpus, decimalPrecision);
		this.maxMatrixReductionRatio = maxMatrixReductionRatio;
		this.weightProcessor = weightProcessor;
		this.initialized = false;
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
		
		//query matrix
		Matrix Q = getQueryMatrix(query);
		
		Matrix Qk = Q.transpose().times(Uk.times(Sk.inverse())).transpose();
		
		Map<String, Double> results = new LinkedHashMap<>();
		
		CosineSimilarity cosSimProcessor = new CosineSimilarity(this.getDecimalPrecision());
		
		//for each document, compute the rank
        for (int i = 0; i < this.VTk.getColumnDimension(); i++) {
            Matrix documentVector = VTk.getMatrix(0, VTk.getRowDimension() - 1, i, i);
            Double rank = cosSimProcessor.calculate(Qk, documentVector);
            results.put(String.valueOf(i), rank);
        }
        
        return results;
	}
	
	
	/**
	 * LSI elaboration based on corpus documents.
	 * 
	 * @throws Exception
	 */
	private void init() throws Exception{
		
		try{
			//term/document matrix 
			Matrix matrix = getTermsDocumentsMatrix();
			
			//Single Value Decomposition elaboration
			SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
			Matrix wordVector = svd.getU();
		    Matrix sigma = svd.getS();
		    Matrix documentVector = svd.getV();
			
		    //calculating k matrix reduction
		    int k = Math.min(matrix.getColumnDimension(), this.maxMatrixReductionRatio);
		    //int k = (int) Math.floor(Math.sqrt(matrix.getColumnDimension()));
		    
		    this.Uk = wordVector.getMatrix(0, wordVector.getRowDimension() - 1, 0, k - 1);
		    this.Sk = sigma.getMatrix(0, k - 1, 0, k - 1);
		    this.VTk = documentVector.getMatrix(0, documentVector.getRowDimension() - 1, 0, k - 1).transpose();
			
		    this.initialized = true;
		    
		}catch(Exception e){
			this.initialized = false;
			throw new Exception("Error while initializing LSIProcessor. ",e);
		}
	}
	
	
	/**
	 * Returns term/document weights matrix elaborated on given corpus.
	 * 
	 * @return matrix
	 * @throws Exception 
	 */
	private Matrix getTermsDocumentsMatrix() throws Exception{
		long start = System.currentTimeMillis();
		Set<String> allTerms = this.getCorpus().getAllCorpusTerms();
		
		int numTerms = allTerms.size();
		int numDocs = this.getCorpus().getDocumentsMap().size();
		
		double[][] doubleMat = new double[numTerms][numDocs];
		
		if(this.weightProcessor!=null){
			Map<String, Map<String, Double>> tfIdfs = this.weightProcessor.getWeightedMap();
			
			int i=0;
			for(String term : allTerms){
				for(int j=0; j<numDocs; j++){
					Map<String, Double> docTfIdf = tfIdfs.get(String.valueOf(j));
					
					if(docTfIdf != null && docTfIdf.containsKey(term)){
						doubleMat[i][j] = docTfIdf.get(term).doubleValue();
					}
				}
				i++;
			}
		}
		else{
			Map<String, Map<String, Integer>> docsTFs = this.getCorpus().getDocumentsTermFrequencies();
			
			int i=0;
			for(String term : allTerms){
				for(int j=0; j<numDocs; j++){
					Map<String, Integer> docTFs = docsTFs.get(String.valueOf(j));
					
					if(docTFs != null && docTFs.containsKey(term)){
						doubleMat[i][j] = docTFs.get(term).doubleValue();
					}
				}
				i++;
			}
		}
		
		Matrix matrix = new Matrix(doubleMat);
		logger.debug("getTermsDocumentsMatrix elaboration time: "+ (System.currentTimeMillis()-start)+" ms.");
		return matrix;
	}
	
	/**
	 * Returns term's weights vector elaborated on given query string.
	 * 
	 * @param query
	 * @return query matrix
	 * @throws Exception
	 */
	private Matrix getQueryMatrix(String query) throws Exception{
		//query matrix
		Set<String> allTerms = this.getCorpus().getAllCorpusTerms();
		
		int numTerms = allTerms.size();
		double[][] doubleMat = new double[numTerms][1];
		
		if(this.weightProcessor!=null){
			Map<String, Double> qTfIdf = this.weightProcessor.calculateTextWeights(query);
			
			int i=0;
			for(String term : allTerms){
				if(qTfIdf != null && qTfIdf.containsKey(term)){
					doubleMat[i][0] = qTfIdf.get(term).doubleValue();
				}
				i++;
			}
		}
		else{
			Map<String, Integer> queryTermsFrequencies = this.getCorpus().getTextTermFrequencies(query);
			
			int i=0;
			for(String term : allTerms){
				
				if(queryTermsFrequencies.containsKey(term)){
					doubleMat[i][0] = queryTermsFrequencies.get(term).doubleValue();
				}
				i++;
			}
		}
		
		Matrix matrix = new Matrix(doubleMat);

		return matrix;
	}
	
	
	/**
	 * Returns a normalized the word scores for all corpus documents.
	 * Used for test.
	 * 
	 * @return map (document key, (term, normalized weight))
	 * @throws Exception 
	 */
	@Override
	public Map<String, Map<String,Double>> getWeightedMap() throws Exception{
		
		if(!this.initialized){
			this.init();
		}
		
		Matrix weights = Uk.times(Sk).times(VTk);
	    //normalize the word scrores for a single document
		for (int j = 0; j < weights.getColumnDimension(); j++) {
	    	double sum = sum(weights.getMatrix(0, weights.getRowDimension() - 1, j, j));
	        for (int i = 0; i < weights.getRowDimension(); i++) {
	        	if(sum > 0.0D)
	        		weights.set(i, j, Math.abs((weights.get(i, j)) / sum));
	        	else
	        		weights.set(i, j, 0.0D);
	        }
	    }
		
		Map<String, Map<String,Double>> normalizedWeightedMap = new LinkedHashMap<>();
		
		Set<String> allTerms = this.getCorpus().getAllCorpusTerms();
		
		for(int j=0; j<weights.getColumnDimension(); j++){
			Map<String, Double> docMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER); 
			
			int i = 0;
			for(String term : allTerms){
				double value = weights.get(i, j);
	    		docMap.put(term, new Double(value));
	    		i++;
			}
			normalizedWeightedMap.put(String.valueOf(j), docMap);
    	}
	    
	    return normalizedWeightedMap;
	}
	
	
	/**
	 * sum all column matrix values
	 * 
	 * @param colMatrix
	 * @return
	 */
	private double sum(Matrix colMatrix) {
	    double sum = 0.0D;
	    for (int i = 0; i < colMatrix.getRowDimension(); i++) {
	    	sum += colMatrix.get(i, 0);
	    }
	    return sum;
	}
	
	@Override
	public Map<String, Double> calculateTextWeights(String text) throws Exception {
		throw new Exception("Not implemented method...");
	}
}

package it.uniroma3.wcb.course.ranking;

import it.uniroma3.wcb.util.DecimalFormatter;

import java.util.Map;

import org.jblas.DoubleMatrix;

import Jama.Matrix;

/**
 * Cosine similarity calculator. 
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 */
public class CosineSimilarity {

	private int numberOfDecimals = 0;
	
	
	/**
	 * Default constructor.
	 */
	public CosineSimilarity(){
		//default constructor
	}
	
	
	/**
	 * Constructor used to set max decimal precision. 
	 *<p>
	 * 
	 * @param numberOfDecimals - decimal precision
	 */
	public CosineSimilarity(int numberOfDecimals){
		this.numberOfDecimals = numberOfDecimals;
	}
	
	
	/**
	 * Returns cosine similarity between query and document term maps.
	 * <p>
	 * 
	 * @param queryTermsWeights  weights of query terms
	 * @param documentTermsWeights  weights of document terms
	 * @return
	 */
    public Double calculate(Map<String, Double> queryTermsWeights, Map<String, Double> documentTermsWeights) {
    	if(queryTermsWeights==null || documentTermsWeights==null)
    		return new Double(0.0);
    	
        double dotProduct = 0.0;
        double magnitude1 = 0.0;
        double magnitude2 = 0.0;
        double cosineSimilarity = 0.0;

        for(Map.Entry<String, Double> dt : documentTermsWeights.entrySet()){
        	Double qt = queryTermsWeights.get(dt.getKey());
        	
        	if(qt!=null ){
        		dotProduct += qt.doubleValue() * dt.getValue().doubleValue();  //a.b
                magnitude1 += Math.pow(qt.doubleValue(), 2);  //(a^2)
        	}
        	magnitude2 += Math.pow(dt.getValue().doubleValue(), 2);  //(a^2)
        }

        magnitude1 = Math.sqrt(magnitude1);//sqrt(a^2)
        magnitude2 = Math.sqrt(magnitude2);//sqrt(b^2)

        if (magnitude1 != 0.0 && magnitude2 != 0.0) {
            cosineSimilarity = (double)((double)dotProduct / (double)(magnitude1 * magnitude2));
        } else {
        	return new Double(0.0);
        }
        
        DecimalFormatter df = new DecimalFormatter(this.numberOfDecimals);
    	cosineSimilarity = df.format(cosineSimilarity);
        
        return new Double(cosineSimilarity);
    }
    
    
    /**
     * Returns cosine similarity between query and document matrix.
     * 
     * @param queryMatrix
     * @param documentMatrix
     * @return
     */
    public Double calculate(Matrix queryMatrix, Matrix documentMatrix){
    	
    	double dotProduct = queryMatrix.arrayTimes(documentMatrix).norm1();
    	double eucledianDist = queryMatrix.normF() * documentMatrix.normF();
    	
    	double cosineSimilarity = 0.0;
    		
    	if (eucledianDist != 0.0) {
    		cosineSimilarity = dotProduct / eucledianDist;
    	}
    	
    	DecimalFormatter df = new DecimalFormatter(this.numberOfDecimals);
    	cosineSimilarity = df.format(cosineSimilarity);
    	
    	return new Double(cosineSimilarity);
    }
    
    
    /**
     * Returns cosine similarity between query and document matrix.
     * 
     * @param queryMatrix
     * @param documentMatrix
     * @return
     */
    public Double calculate(DoubleMatrix queryMatrix, DoubleMatrix documentMatrix){
    	
    	double dotProduct = queryMatrix.mul(documentMatrix).norm1();
    	double eucledianDist = queryMatrix.norm2() * documentMatrix.norm2();
    	
    	double cosineSimilarity = 0.0;
    		
    	if (eucledianDist != 0.0) {
    		cosineSimilarity = dotProduct / eucledianDist;
    	}
    	
    	DecimalFormatter df = new DecimalFormatter(this.numberOfDecimals);
    	cosineSimilarity = df.format(cosineSimilarity);
    	
    	return new Double(cosineSimilarity);
    }
}

package it.uniroma3.wcb.course.ranking;

import it.uniroma3.wcb.course.Corpus;
import it.uniroma3.wcb.util.DocumentMapPrinter;

import java.io.PrintWriter;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * Basic Rank Processor abstract class.
 * <p>
 * 
 * @author Andrea Tarantini, Alessandra Milita
 */
public abstract class RankProcessor {

	private Corpus corpus;
	private int decimalPrecision = 0;
	
	/**
	 * Default constructor, where corpus contains all documents collection and decimalPrecision
	 * the precision of resulting scores.
	 * 
	 * @param corpus
	 * @param decimalPrecision
	 */
	public RankProcessor(Corpus corpus, int decimalPrecision){
		Validate.notNull(corpus, "corpus cannot be null");
		this.corpus = corpus;
		this.decimalPrecision = decimalPrecision;
	}
	
	/**
	 * Calculate similarity score between each corpus document and the given query
	 * 
	 * @param query
	 * @return map (document key, similarity score)
     * @throws java.lang.Exception
	 */
	public abstract Map<String, Double> scoreDocuments(String query) throws Exception;
	
	/**
	 * Returns documents/terms weighted map.
	 * 
	 * @return map (document key, (term, weight))
	 * @throws Exception 
	 */
	public abstract Map<String, Map<String,Double>> getWeightedMap() throws Exception;
	
	/**
	 * Calculate terms weights map based on the given text.
	 * 
	 * @param text
	 * @return map (term, weight)
	 * @throws Exception 
	 */
	public abstract Map<String, Double> calculateTextWeights(String text) throws Exception;
	
	public Corpus getCorpus() {
		return corpus;
	}

	public int getDecimalPrecision() {
		return decimalPrecision;
	}

	/**
	 * Prints on stdout document/terms weighted map.
	 * Used for test.
     * @param title
	 * @throws Exception 
	 */
	public void printResults(String title) throws Exception{
		DocumentMapPrinter printer = new DocumentMapPrinter();
		printer.printResults(this.getWeightedMap(), this.corpus, title);
	}
	
	
	/**
	 * Prints on given writer  document/terms weighted map.
	 * Used for test.
     * @param title
     * @param writer
	 * @throws Exception 
	 */
	public void printResults(String title, PrintWriter writer) throws Exception{
		DocumentMapPrinter printer = new DocumentMapPrinter();
		printer.printResults(this.getWeightedMap(), this.corpus, title, writer);
	}
	
}

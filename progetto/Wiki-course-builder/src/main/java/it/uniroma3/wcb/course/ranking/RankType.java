package it.uniroma3.wcb.course.ranking;

import java.util.Comparator;

/**
 * Enum that represents all implemented ranking alghoritms.
 * <p> 
 * 
 * @author Andrea Tarantini, Alessandra Milita
 */
public enum RankType {

	TEACHING_STYLE ("ts_rank", "Teaching Style", new AscendingComparator()),
	TF_IDF ("tfidf_rank", "Cosine Similarity on TF-IDF", new DescendingComparator()), 
	LSI ("lsi_rank", "Cosine Similarity on LSI", new DescendingComparator()),
	IG ("ig_rank", "Cosine Similarity on Information Gain", new DescendingComparator());
	
	private String label;
	private String description;
	private Comparator<Double> comparator;
	
	
	/**
	 * RankType constructor.
	 * <p>
	 *  
	 * @param label  RankType label
	 * @param description  RankType description
	 * @param comparator  RankType comparator that will be used to order rank results
	 */
	RankType(String label, String description, Comparator<Double> comparator){
		this.label = label;
		this.description = description;
		this.comparator = comparator;
	}
	
	
	/**
	 * RankType label.
	 * <p>
	 * 
	 * @return label
	 */
	public String getLabel(){
		return this.label;
	}
	
	
	/**
	 * RankType description.
	 * <p>
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	
	/**
	 * RankType scores comparator.
	 * <p>
	 * 
	 * @return comparator
	 */
	public Comparator<Double> getComparator(){
		return this.comparator;
	}
}

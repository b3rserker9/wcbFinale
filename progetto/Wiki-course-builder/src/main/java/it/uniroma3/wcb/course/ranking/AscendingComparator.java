package it.uniroma3.wcb.course.ranking;

import java.util.Comparator;

/**
 * Used by ranking alghoritm where a smaller value is more relevant than a greater one.
 * <p>
 *  
 * @see RankType
 * @see DescendingComparator
 * @author Andrea Tarantini, Alessandra Milita
 */
public class AscendingComparator implements Comparator<Double>{

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compare(Double v1, Double v2) {
		if(v1==null && v2==null)
			return 0;
		if(v1 == null)
			return -1 ;
		else if(v2 == null)
			return 1;
		return v2.compareTo(v1);
	}
	
	
	/**
	 * Check if first value is better than second one.
	 * 
	 * @param v1  first value
	 * @param v2  first value
	 * @return {@code true} if v1 is smaller v2
	 */
	public boolean isBetter(Double v1, Double v2){
		return this.compare(v1, v2)>0;
	}
	
	
	/**
	 * Check if first value is equal to second one.
	 * 
	 * @param v1  first value
	 * @param v2  first value
	 * @return {@code true} if v1 equals v2
	 */
	public boolean isEqual(Double v1, Double v2){
		return this.compare(v1, v2)==0;
	}
}

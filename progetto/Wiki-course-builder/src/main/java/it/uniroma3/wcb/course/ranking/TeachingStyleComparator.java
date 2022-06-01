package it.uniroma3.wcb.course.ranking;

import it.uniroma3.wcb.persistence.model.PageTeachingStyle;
import it.uniroma3.wcb.persistence.model.UserTeachingStyle;
import it.uniroma3.wcb.util.DecimalFormatter;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public class TeachingStyleComparator {

	// Math.sqrt(Math.pow(<max_score_value> - <min_score_value>, 2) * number_of_teaching_style_parameters)
	public static double MAX_DISTANCE = Math.sqrt(Math.pow(5 - 1, 2) * 5);
	
	private int decimalPrecision = 0;
	
	public TeachingStyleComparator(int decimalPrecision){
		this.decimalPrecision = decimalPrecision;
	}
	
	public double getEuclideanDistance(UserTeachingStyle userTS, PageTeachingStyle pageTS){
		
		if(userTS==null)
			return MAX_DISTANCE;
		
		if(pageTS==null){
			pageTS = getNeutralTS();
		}
		
		double result = Math.sqrt(
							Math.pow(userTS.getExpert() - pageTS.getExpert(), 2) +
							Math.pow(userTS.getDelegator() - pageTS.getDelegator(), 2) +
							Math.pow(userTS.getFacilitator() - pageTS.getFacilitator(), 2) +
							Math.pow(userTS.getFormalAuthority() - pageTS.getFormalAuthority(), 2) +
							Math.pow(userTS.getPersonalModel() - pageTS.getPersonalModel(), 2)
						);
		return result;
	}
	
	public double getTSOrderingScore(UserTeachingStyle userTS, PageTeachingStyle pageTS){
		double euclidianDistance = getEuclideanDistance(userTS, pageTS);
		
		//valore compreso tra 0 e 1
		
		//double result = (double)((MAX_DISTANCE - euclidianDistance) / MAX_DISTANCE);
		
		double result = (double)(euclidianDistance / MAX_DISTANCE);
		
		DecimalFormatter df = new DecimalFormatter(this.decimalPrecision);
    	result = df.format(result);
    	
		return result;
	}
	
	private PageTeachingStyle getNeutralTS(){
		PageTeachingStyle neutralTeachingStyle = new PageTeachingStyle();
		
		neutralTeachingStyle.setDelegator(2.5);
		neutralTeachingStyle.setExpert(2.5);
		neutralTeachingStyle.setFacilitator(2.5);
		neutralTeachingStyle.setFormalAuthority(2.5);
		neutralTeachingStyle.setPersonalModel(2.5);
		
		return neutralTeachingStyle;
	}
}

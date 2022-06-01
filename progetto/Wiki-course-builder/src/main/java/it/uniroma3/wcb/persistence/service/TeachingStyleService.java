package it.uniroma3.wcb.persistence.service;

import it.uniroma3.wcb.persistence.model.UserTeachingStyle;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface TeachingStyleService {

	public UserTeachingStyle saveUserTeachingStyle(GRTeachingStyleDto teachingStyleDto);
	
	public GRTeachingStyleDto loadUserTeachingStyle();
	
}

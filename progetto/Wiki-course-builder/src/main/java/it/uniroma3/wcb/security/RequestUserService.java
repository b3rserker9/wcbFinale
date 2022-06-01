package it.uniroma3.wcb.security;

import it.uniroma3.wcb.persistence.model.User;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface RequestUserService {
	public User getRequestUser() throws Exception;
}

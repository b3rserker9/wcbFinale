package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.persistence.model.VerificationToken;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    public VerificationToken findByToken(String token);

    public VerificationToken findByUser(User user);
}

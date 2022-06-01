package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.PasswordResetToken;
import it.uniroma3.wcb.persistence.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    public PasswordResetToken findByToken(String token);

    public PasswordResetToken findByUser(User user);
}

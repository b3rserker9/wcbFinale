package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public void delete(User user);

}

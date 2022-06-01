package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.Privilege;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    public Privilege findByName(String name);

    public void delete(Privilege privilege);
}

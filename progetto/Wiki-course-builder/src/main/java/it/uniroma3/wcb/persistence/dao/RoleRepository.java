package it.uniroma3.wcb.persistence.dao;

import it.uniroma3.wcb.persistence.model.Role;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    public Role findByName(String name);

    public void delete(Role role);
}

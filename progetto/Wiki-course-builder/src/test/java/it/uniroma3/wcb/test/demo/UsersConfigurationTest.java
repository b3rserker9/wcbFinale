package it.uniroma3.wcb.test.demo;

import it.uniroma3.wcb.persistence.dao.PrivilegeRepository;
import it.uniroma3.wcb.persistence.dao.RoleRepository;
import it.uniroma3.wcb.persistence.dao.UserRepository;
import it.uniroma3.wcb.persistence.model.Privilege;
import it.uniroma3.wcb.persistence.model.Role;
import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.spring.ConfigTest;
import it.uniroma3.wcb.spring.PersistenceJPAConfig;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ConfigTest.class, PersistenceJPAConfig.class }, loader = AnnotationConfigContextLoader.class)
public class UsersConfigurationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void createDemoUsers() {
    	
    	// == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");

        // == create initial roles
        final List<Privilege> userPrivileges = Arrays.asList(readPrivilege);
    	
    	Role userRole = createRoleIfNotFound("ROLE_DEMO", userPrivileges);
    	
    	String encodedPwd = passwordEncoder.encode("test");
    	
    	User ex = userRepository.findByEmail("brian.smith@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Brian");
            user.setLastName("Smith");
            user.setPassword(encodedPwd);
            user.setEmail("brian.smith@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
        
        ex = userRepository.findByEmail("roger.johnson@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Roger");
            user.setLastName("Johnson");
            user.setPassword(encodedPwd);
            user.setEmail("roger.johnson@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);
        }
        
        ex = userRepository.findByEmail("sandra.williams@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Sandra");
            user.setLastName("Williams");
            user.setPassword(encodedPwd);
            user.setEmail("sandra.williams@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
    	
        ex = userRepository.findByEmail("eric.brown@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Eric");
            user.setLastName("Brown");
            user.setPassword(encodedPwd);
            user.setEmail("eric.brown@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
    	
        ex = userRepository.findByEmail("donna.jones@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Donna");
            user.setLastName("Jones");
            user.setPassword(encodedPwd);
            user.setEmail("donna.jones@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
        
        ex = userRepository.findByEmail("mike.miller@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Mike");
            user.setLastName("Miller");
            user.setPassword(encodedPwd);
            user.setEmail("mike.miller@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
        
        ex = userRepository.findByEmail("daniel.davis@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Daniel");
            user.setLastName("Davis");
            user.setPassword(encodedPwd);
            user.setEmail("daniel.davis@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
        
        ex = userRepository.findByEmail("sabrina.garcia@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Sabrina");
            user.setLastName("Garcia");
            user.setPassword(encodedPwd);
            user.setEmail("sabrina.garcia@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
        
        ex = userRepository.findByEmail("laurent.wilson@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Laurent");
            user.setLastName("Wilson");
            user.setPassword(encodedPwd);
            user.setEmail("laurent.wilson@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
        
        ex = userRepository.findByEmail("paula.martinez@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Paula");
            user.setLastName("Martinez");
            user.setPassword(encodedPwd);
            user.setEmail("paula.martinez@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
        
        ex = userRepository.findByEmail("stuart.taylor@test.com");
        if(ex==null){
        	final User user = new User();
            user.setFirstName("Stuart");
            user.setLastName("Taylor");
            user.setPassword(encodedPwd);
            user.setEmail("stuart.taylor@test.com");
            user.setRoles(Arrays.asList(userRole));
            user.setEnabled(true);
            userRepository.save(user);	
        }
    }

    @Transactional
    private final Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    private final Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}

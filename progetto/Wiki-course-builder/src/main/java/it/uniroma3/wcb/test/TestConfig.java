package it.uniroma3.wcb.test;

import it.uniroma3.wcb.spring.PersistenceJPAConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@ComponentScan({ "it.uniroma3.wcb.persistence.dao" })
public class TestConfig extends PersistenceJPAConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

}

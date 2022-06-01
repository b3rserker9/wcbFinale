package it.uniroma3.wcb.security;

import it.uniroma3.wcb.persistence.dao.RoleRepository;
import it.uniroma3.wcb.persistence.dao.UserRepository;
import it.uniroma3.wcb.persistence.model.Privilege;
import it.uniroma3.wcb.persistence.model.Role;
import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.web.error.UserNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService, RequestUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    public UserDetailsServiceImpl() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        String ip = request.getRemoteAddr();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        try {
            final User user = userRepository.findByEmail(email);
            if (user == null) {
                return new CustomUserDetail(" ", " ", " ", " ", true, true, true, true, getAuthorities(Arrays.asList(roleRepository.findByName("ROLE_USER"))));
            }

            return new CustomUserDetail(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public User getRequestUser() throws Exception{
		try{
			String email = request.getUserPrincipal().getName();
	        User user = userRepository.findByEmail(email);
	        
	        if(user!=null){
	        	return user;
	        }
	        else{
	        	throw new UserNotFoundException();
	        }
		}catch(Exception e){
			throw new Exception("Error while retrieving user ",e);
		}
	}
    
    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private final List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}

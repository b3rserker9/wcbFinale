package it.uniroma3.wcb.persistence.service;

import it.uniroma3.wcb.persistence.dao.PasswordResetTokenRepository;
import it.uniroma3.wcb.persistence.dao.RoleRepository;
import it.uniroma3.wcb.persistence.dao.UserRepository;
import it.uniroma3.wcb.persistence.dao.VerificationTokenRepository;
import it.uniroma3.wcb.persistence.model.PasswordResetToken;
import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.persistence.model.VerificationToken;
import it.uniroma3.wcb.validation.EmailExistsException;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User registerNewUserAccount(final UserDto accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email adress: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return repository.save(user);
    }

    @Override
    public User getUser(final String verificationToken) {
        final User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(final User user) {
        repository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        repository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public User findUserByEmail(final String email) {
        return repository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID(final long id) {
        return repository.findOne(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    private boolean emailExist(final String email) {
        final User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

}
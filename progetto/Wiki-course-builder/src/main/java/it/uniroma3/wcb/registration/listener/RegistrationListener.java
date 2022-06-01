package it.uniroma3.wcb.registration.listener;

import it.uniroma3.wcb.persistence.model.User;
import it.uniroma3.wcb.persistence.service.UserService;
import it.uniroma3.wcb.registration.OnRegistrationCompleteEvent;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }


    private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
       
        String confirmationUrl = env.getProperty("system.web.url");
        if(confirmationUrl==null || "".equals(confirmationUrl))
        	confirmationUrl = event.getAppUrl();
        confirmationUrl+="/registrationConfirm.html?token=" + token;
        final String message = messages.getMessage("message.confirmationEmail", null, event.getLocale()).replace("{CONF_URL}", confirmationUrl);
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

}

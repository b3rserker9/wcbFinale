package it.uniroma3.wcb.registration;

import it.uniroma3.wcb.persistence.model.User;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;

    public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        //return locale; //FIXME - forced to use english locale
    	return new Locale("en");
    }

    public User getUser() {
        return user;
    }
}

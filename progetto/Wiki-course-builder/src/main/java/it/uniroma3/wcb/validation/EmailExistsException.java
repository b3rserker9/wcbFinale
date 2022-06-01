package it.uniroma3.wcb.validation;

/**
 * 
 * @author Andrea Tarantini, Alessandra Milita
 *
 */
@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

    public EmailExistsException(String message) {
        super(message);
    }
}

package br.eti.arthurgregorio.library.infrastructure.mail;

/**
 * A simple interface to define how to create a mail content provider
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 03/04/2018
 */
public interface MailContentProvider {

    /**
     * Return the content processed by the provider
     * 
     * @return the content in string for sending in the body of the message
     */
    String getContent();
}

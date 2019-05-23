package br.eti.arthurgregorio.library.infrastructure.mail;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

/**
 * Simple facade to define how a mail message looks like
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 02/04/2018
 */
public interface MailMessage {

    /**
     * @return the title
     */
    public String getTitle();

    /**
     * @return the content
     */
    public String getContent();

    /**
     * @return the from {@link InternetAddress}
     */
    public Address getFrom();

    /**
     * @return the replay to {@link InternetAddress}
     */
    public Address getReplyTo();

    /**
     * @return the list of addressees of this message
     */
    public Address[] getAddressees();

    /**
     * @return the list of 'with-copy' for this message
     */
    public Address[] getCcs();
}

package br.eti.arthurgregorio.library.infrastructure.mail;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.util.List;

/**
 * Simple facade to define how a mail message looks like
 *
 * @author Arthur Gregorio
 *
 * @version 1.1.0
 * @since 1.0.0, 02/04/2018
 */
public interface MailMessage {

    /**
     * @return the title
     */
    String getTitle();

    /**
     * @return the content
     */
    String getContent();

    /**
     * @return the from {@link InternetAddress}
     */
    Address getFrom();

    /**
     * @return the replay to {@link InternetAddress}
     */
    Address getReplyTo();

    /**
     * @return the list of addressees of this message
     */
    Address[] getAddressees();

    /**
     * @return the list of 'with-copy' for this message
     */
    Address[] getCcs();

    /**
     * @return the list of attachments for this message
     */
    List<File> getAttachments();
}
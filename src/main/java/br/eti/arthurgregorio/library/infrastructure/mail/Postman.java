package br.eti.arthurgregorio.library.infrastructure.mail;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * This class has one job: send e-mails
 *
 * @author Arthur Gregorio
 *
 * @version 1.1.0
 * @since 1.0.0, 02/04/2018
 */
@ApplicationScoped
public class Postman {

    @Resource(name = "java:/mail/mailService")
    private Session mailSession;

    /**
     * Listen for e-mail requests through CDI events and send the message
     *
     * @param mailMessage the message to send
     * @throws Exception if any problem occur in the process
     */
    public void send(@Observes MailMessage mailMessage) throws Exception {

        final MimeMessage message = new MimeMessage(this.mailSession);

        // message header
        message.setFrom(mailMessage.getFrom());
        message.setSubject(mailMessage.getTitle());
        message.setRecipients(Message.RecipientType.TO, mailMessage.getAddressees());
        message.setRecipients(Message.RecipientType.CC, mailMessage.getCcs());

        // message body
        message.setText(mailMessage.getContent(), "UTF-8", "html");
        message.setSentDate(new Date());

        // send
        Transport.send(message);
    }
}

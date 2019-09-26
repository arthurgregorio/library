package br.eti.arthurgregorio.library.infrastructure.mail;

import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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

        // create the message
        final MimeMessage message = new MimeMessage(this.mailSession);

        message.setFrom(mailMessage.getFrom());
        message.setSubject(mailMessage.getTitle());
        message.setRecipients(Message.RecipientType.TO, mailMessage.getAddressees());
        message.setRecipients(Message.RecipientType.CC, mailMessage.getCcs());

        message.setSentDate(new Date());

        final MimeMultipart multipart = new MimeMultipart();

        // message text
        final MimeBodyPart messagePart = new MimeBodyPart();

        messagePart.setText(mailMessage.getContent(), "UTF-8", "html");
        multipart.addBodyPart(messagePart);

        // attachments part
        mailMessage.getAttachments().forEach(file -> {
            try {
                final FileDataSource dataSource = new FileDataSource(file);
                final MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.setDataHandler(new DataHandler(dataSource));
                attachmentPart.setFileName(dataSource.getName());
                multipart.addBodyPart(attachmentPart);
            } catch (MessagingException ex) {
                throw new BusinessLogicException("error.email.cant-attach-file", ex, file.getName());
            }
        });

        message.setContent(multipart);

        // send
        Transport.send(message);
    }
}
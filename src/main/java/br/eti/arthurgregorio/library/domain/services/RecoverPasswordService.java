package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.application.components.MessageSource;
import br.eti.arthurgregorio.library.domain.model.entities.security.StoreType;
import br.eti.arthurgregorio.library.domain.model.entities.security.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.model.mail.SimpleMailMessage;
import br.eti.arthurgregorio.library.domain.repositories.tools.UserRepository;
import br.eti.arthurgregorio.library.infrastructure.mail.MailContentProvider;
import br.eti.arthurgregorio.library.infrastructure.mail.MailMessage;
import br.eti.arthurgregorio.library.infrastructure.mail.MustacheProvider;
import br.eti.arthurgregorio.library.infrastructure.utilities.CodeGenerator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service responsible for all the operations about the password recovery 
 * process
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 02/04/2018
 */
@ApplicationScoped
public class RecoverPasswordService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private Event<MailMessage> mailSender;

    /**
     * Recover the password and send a e-mail to the user
     *
     * @param email the e-mail address of the user to recover and notify
     */
    @Transactional
    public void recover(String email) {

        final User user = this.userRepository
                .findOptionalByEmailAndStoreType(email, StoreType.LOCAL)
                .orElseThrow(() -> new BusinessLogicException(
                        "error.recover-password.user-not-found"));

        final String newPassword = CodeGenerator.alphanumeric(8);

        user.setPassword(newPassword);

        this.userRepository.saveAndFlushAndRefresh(user);

        final MailMessage mailMessage = SimpleMailMessage.getBuilder()
                .from("no-reply@pti.org.br")
                .to(user.getEmail())
                .withTitle(MessageSource.get("recover-password.email.title"))
                .withContent(this.buildContent(user, newPassword))
                .build();

        try {
            this.mailSender.fire(mailMessage);
        } catch (Exception ex) {
            throw new BusinessLogicException("error.core.sending-mail-error");
        }
    }

    /**
     * Construct the content of the e-mail message
     * 
     * @param user the user
     * @param newPassword the new password
     * @return the content provider with the content
     */
    private MailContentProvider buildContent(User user, String newPassword) {
       
        final MustacheProvider provider = 
                new MustacheProvider("recover-password.mustache");

        final String date = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm")
                .format(LocalDateTime.now());
        
        provider.addContent("title", MessageSource.get("recover-password.email.title"));
        provider.addContent("detail", MessageSource.get("recover-password.email.detail"));
        provider.addContent("on", MessageSource.get("recover-password.email.on"));
        provider.addContent("requestDate", date);
        provider.addContent("username", user.getUsername());
        provider.addContent("message", MessageSource.get("recover-password.email.message"));
        provider.addContent("newPassword", newPassword);
        
        return provider;
    }
}

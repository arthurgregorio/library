package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.domain.entities.configuration.StoreType;
import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import br.eti.arthurgregorio.library.infrastructure.i18n.MessageSource;
import br.eti.arthurgregorio.library.infrastructure.mail.*;
import br.eti.arthurgregorio.library.infrastructure.misc.Configurations;
import br.eti.arthurgregorio.library.infrastructure.misc.RandomString;
import br.eti.arthurgregorio.shiroee.auth.PasswordEncoder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.transaction.Transactional;

/**
 * The service responsible for all the operations about the password recovery process
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
    private PasswordEncoder passwordEncoder;

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
                .findByEmailAndStoreType(email, StoreType.LOCAL)
                .orElseThrow(() -> new BusinessLogicException("error.recover-password.user-not-found"));

        final String newPassword = new RandomString(8).nextString();

        user.setPassword(this.passwordEncoder.encryptPassword(newPassword));

        this.userRepository.saveAndFlushAndRefresh(user);

        final String noReplyAddress = Configurations.get("email.no-reply-address");

        final MailMessage mailMessage = SimpleMailMessage.builder()
                .from("noreply@pti.org.br", MessageSource.get("application.name"))
                .to(user.getEmail())
                .withTitle(MessageSource.get("mail.recover-password.title"))
                .withContent(this.buildContent(user, newPassword))
                .build();

        this.mailSender.fire(mailMessage);
    }

    /**
     * Construct the content of the e-mail message
     *
     * @param user the user
     * @param newPassword the new password
     * @return the content provider with the content
     */
    private MailContentProvider buildContent(User user, String newPassword) {

        final MustacheProvider provider = new MustacheProvider("recover-password.mustache");

        provider.addContent("user", user);
        provider.addContent("newPassword", newPassword);
        provider.addContent("greeting", EmailUtil.getGreeting());
        provider.addContent("baseUrl", Configurations.getBaseURL());

        provider.addContent("translate", EmailUtil.translateFunction());

        return provider;
    }
}
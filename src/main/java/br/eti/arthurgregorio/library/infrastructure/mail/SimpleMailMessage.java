package br.eti.arthurgregorio.library.infrastructure.mail;

import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Simple implementation of the {@link MailMessage} with a build and a fluent interface to construct e-mail messages
 *
 * @author Arthur Gregorio
 *
 * @version 1.2.0
 * @since 1.0.0, 03/04/2018
 */
@ToString
public class SimpleMailMessage implements MailMessage {

    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private Address from;
    @Getter
    @Setter
    private Address replyTo;

    @Getter
    private List<File> attachments;
    @Setter
    private List<InternetAddress> ccs;
    @Setter
    private List<InternetAddress> addressees;

    /**
     * Private constructor to prevent misuse
     */
    private SimpleMailMessage() {
        this.ccs = new ArrayList<>();
        this.addressees = new ArrayList<>();
        this.attachments = new ArrayList<>();
    }

    /**
     * @return all the addressees for the message
     */
    @Override
    public InternetAddress[] getAddressees() {
        return this.addressees.toArray(new InternetAddress[]{});
    }

    /**
     * @return all the cc for the message
     */
    @Override
    public InternetAddress[] getCcs() {
        return this.ccs.toArray(new InternetAddress[]{});
    }

    /**
     * @return the builder of {@link SimpleMailMessage}
     */
    public static SimpleMailMessageBuilder builder() {
        return new SimpleMailMessageBuilder();
    }

    /**
     * A builder pattern implementation to create e-mail messages
     */
    public static class SimpleMailMessageBuilder {

        private final SimpleMailMessage message;

        /**
         * Private constructor to prevent misuse
         */
        private SimpleMailMessageBuilder() {
            this.message = new SimpleMailMessage();
        }

        /**
         * Add the addressee of the message
         *
         * @param to the addressee of the message
         * @return this builder
         */
        public SimpleMailMessageBuilder to(String to) {
            this.message.addressees.add(this.toAddress(to));
            return this;
        }

        /**
         *
         * @param addressees
         * @return
         */
        public SimpleMailMessageBuilder to(String... addressees) {
            for (String addressee : addressees) {
                this.message.addressees.add(this.toAddress(addressee));
            }
            return this;
        }

        /**
         * From field of the message
         *
         * @param from address
         * @return this builder
         */
        public SimpleMailMessageBuilder from(String from) {
            this.message.setFrom(this.toAddress(from));
            return this;
        }

        /**
         * From field of the message
         *
         * @param from the address
         * @param name the name
         * @return this builder
         */
        public SimpleMailMessageBuilder from(String from, String name) {
            this.message.setFrom(this.toAddress(from, name));
            return this;
        }

        /**
         * The reply to address
         *
         * @param replyTo reply to address
         * @return this builder
         */
        public SimpleMailMessageBuilder replyTo(String replyTo) {
            this.message.setReplyTo(this.toAddress(replyTo));
            return this;
        }

        /**
         * The with-copy address to the message
         *
         * @param copy the address to copy the message
         * @return this builder
         */
        public SimpleMailMessageBuilder withCopy(String copy) {
            this.message.ccs.add(this.toAddress(copy));
            return this;
        }

        /**
         * The string content of the message
         *
         * @param content content in string format
         * @return this builder
         */
        public SimpleMailMessageBuilder withContent(String content) {
            this.message.setContent(content);
            return this;
        }

        /**
         * Set a provider to provide the content to the message
         *
         * @param provider the provider to provide the content
         * @return this builder
         */
        public SimpleMailMessageBuilder withContent(MailContentProvider provider) {
            this.message.setContent(checkNotNull(provider).getContent());
            return this;
        }

        /**
         * The title of the message
         *
         * @param title the title
         * @return this builder
         */
        public SimpleMailMessageBuilder withTitle(String title) {
            this.message.setTitle(title);
            return this;
        }

        /**
         *
         * @param attachment
         * @return
         */
        public SimpleMailMessageBuilder putAttachment(File attachment) {
            this.message.attachments.add(attachment);
            return this;
        }

        /**
         * @return the instance of the {@link SimpleMailMessage} to be created
         */
        public SimpleMailMessage build() {
            return this.message;
        }

        /**
         * Convert a string to a {@link Address}
         *
         * @param address the string address
         * @return the {@link InternetAddress} implementation of {@link Address}
         */
        private InternetAddress toAddress(String address) {
            return this.toAddress(address, null);
        }

        /**
         * Convert a string to a {@link Address}
         *
         * @param address the string address
         * @param personal the name
         * @return the {@link InternetAddress} implementation of {@link Address}
         */
        private InternetAddress toAddress(String address, String personal) {
            try {
                return new InternetAddress(address, personal);
            } catch (UnsupportedEncodingException ex) {
                throw new BusinessLogicException("error.core.email-address-invalid", ex, address);
            }
        }
    }
}
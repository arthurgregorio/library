package br.eti.arthurgregorio.library.application.components;

import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * This class is a simple value holder for the current authenticated user produced by the {@link AuthenticatedUserProducer}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 19/08/2019
 */
@ToString
@EqualsAndHashCode
public class CurrentUser implements Serializable {

    @Getter
    private String username;
    @Getter
    private String email;
    @Getter
    private String name;
    @Getter
    private boolean administrator;

    /**
     *
     */
    public CurrentUser() { }

    /**
     *
     * @param user
     */
    public CurrentUser(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
        this.email = user.getEmail();
        this.administrator = user.isAdministrator();
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return StringUtils.isBlank(this.username);
    }
}

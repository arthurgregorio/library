package br.eti.arthurgregorio.library.domain.entities.configuration;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import br.eti.arthurgregorio.library.infrastructure.misc.DefaultSchemes;
import br.eti.arthurgregorio.library.infrastructure.shiro.ldap.LdapAttribute;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * The user account entity
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/12/2017
 */
@Entity
@Audited
@Table(name = "users", schema = DefaultSchemes.CONFIGURATION)
@EqualsAndHashCode(callSuper = true, of = {"email", "username"})
@ToString(callSuper = true, exclude = {"profile", "group", "passwordConfirmation"})
public class User extends PersistentEntity implements UserDetails {

    @Getter
    @Setter
    @LdapAttribute("displayName")
    @NotBlank(message = "{user.name}")
    @Column(name = "name", length = 90, nullable = false)
    private String name;
    @Getter
    @Setter
    @LdapAttribute("mail")
    @NotBlank(message = "{user.email}")
    @Column(name = "email", length = 90, nullable = false)
    private String email;
    @Getter
    @Setter
    @LdapAttribute("department")
    @Column(name = "department", length = 90)
    private String department;
    @Getter
    @Setter
    @LdapAttribute("telephoneNumber")
    @Column(name = "telephone", length = 90)
    private String telephone;
    @Getter
    @Setter
    @LdapAttribute("sAMAccountName")
    @NotBlank(message = "{user.username}")
    @Column(name = "username", length = 20, nullable = false)
    private String username;
    @Getter
    @Setter
    @Column(name = "password", length = 60)
    private String password;
    @Getter
    @Setter
    @Column(name = "active", nullable = false)
    private boolean active;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "{user.store-type}")
    @Column(name = "store_type", nullable = false)
    private StoreType storeType;

    @Getter
    @Setter
    @JoinColumn(name = "id_profile", nullable = false)
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    private Profile profile;
    @Getter
    @Setter
    @ManyToOne
    @NotNull(message = "{user.group}")
    @JoinColumn(name = "id_group", nullable = false)
    private Group group;

    @Getter
    @Setter
    @Transient
    private String passwordConfirmation;
    @Getter
    @Setter
    @Transient
    private boolean selected;
    @Getter
    @Setter
    @Transient
    private boolean imported;

    /**
     * Constructor...
     */
    public User() {
        this.active = true;
        this.profile = new Profile();
        this.storeType = StoreType.LOCAL;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean isBlocked() {
        return !this.isActive();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean isLdapBindAccount() {
        return this.storeType == StoreType.LDAP;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public Set<String> getPermissions() {
        return this.group != null ? this.group.getPermissions() : new HashSet<>();
    }

    /**
     * Use this method to get the user {@link Group} name
     *
     * @return {@link Group} name for this user
     */
    public String getGroupName() {
        return this.group != null ? this.group.getName() : null;
    }

    /**
     * To check if the password of this user is valid in case of account creation or password change
     *
     * @return true if is, false otherwise
     */
    public boolean isPasswordValid() {
        return isNotBlank(this.password) && isNotBlank(this.passwordConfirmation) && this.password.equals(this.passwordConfirmation);
    }

    /**
     * Method to check if the user changed his password
     *
     * @return true if changed, false otherwise
     */
    public boolean hasChangedPasswords() {
        return isNotBlank(this.password) && isNotBlank(this.passwordConfirmation);
    }

    /**
     * Method to check if this user is an administrator
     *
     * @return true if is, false otherwise
     */
    public boolean isAdministrator() {
        return this.username.equals("admin");
    }
}

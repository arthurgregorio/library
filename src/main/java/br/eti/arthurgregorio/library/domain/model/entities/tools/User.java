package br.eti.arthurgregorio.library.domain.model.entities.tools;

import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetails;
import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY_AUDIT;
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
@Table(name = "users", schema = SECURITY)
@ToString(callSuper = true, exclude = "group")
@AuditTable(value = "users", schema = SECURITY_AUDIT)
@EqualsAndHashCode(callSuper = true, exclude = "group")
public class User extends PersistentEntity implements UserDetails {

    @Getter
    @Setter
    @NotBlank(message = "{user.name}")
    @Column(name = "name", length = 90, nullable = false)
    private String name;
    @Getter
    @Setter
    @NotBlank(message = "{user.email}")
    @Column(name = "email", length = 90, nullable = false)
    private String email;
    @Getter
    @Setter
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

    /**
     *
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
     * @return the {@link Group} for this user
     */
    public String getGroupName() {
        return this.group != null ? this.group.getName() : null;
    }

    /**
     * @return if the user password is valid
     */
    public boolean isPasswordValid() {
        return isNotBlank(this.password)
                && isNotBlank(this.passwordConfirmation)
                && this.password.equals(this.passwordConfirmation);
    }

    /**
     * @return if the password has changed
     */
    public boolean hasChangedPasswords() {
        return isNotBlank(this.password)
                && isNotBlank(this.passwordConfirmation);
    }

    /**
     * @return if this user is an administrator
     */
    public boolean isAdministrator() {
        return this.username.equals("admin");
    }
}

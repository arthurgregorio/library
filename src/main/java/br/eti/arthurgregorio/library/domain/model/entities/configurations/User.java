package br.eti.arthurgregorio.library.domain.model.entities.configurations;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import br.eti.arthurgregorio.library.infrastructure.soteria.identity.UserDetails;
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
import java.util.List;
import java.util.Set;

import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.CONFIGURATION;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.CONFIGURATION_AUDIT;
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
@ToString(callSuper = true, exclude = "group")
@EqualsAndHashCode(callSuper = true, exclude = "group")
@AuditTable(value = "users", schema = CONFIGURATION_AUDIT)
@Table(name = "users", schema = CONFIGURATION, uniqueConstraints = @UniqueConstraint(columnNames = {"username","email"}))
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Token> tokens;

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
    public boolean isPasswordInvalid() {
        return isNotBlank(this.password)
                && isNotBlank(this.passwordConfirmation) && this.password.equals(this.passwordConfirmation);
    }

    /**
     * @return if the password has changed
     */
    public boolean passwordsHasChanged() {
        return isNotBlank(this.password) && isNotBlank(this.passwordConfirmation);
    }

    /**
     * @return if this user is an administrator
     */
    public boolean isAdministrator() {
        return this.username.equals("admin");
    }
}

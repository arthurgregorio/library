package br.eti.arthurgregorio.library.domain.model.entities.tools;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY_AUDIT;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetails;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

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
    @NotNull(message = "{user.name}")
    @Column(name = "name", length = 90, nullable = false)
    private String name;
    @Getter
    @Setter
    @NotNull(message = "{user.email}")
    @Column(name = "email", length = 90, nullable = false)
    private String email;
    @Getter
    @Setter
    @NotNull(message = "{user.username}")
    @Column(name = "username", length = 20, nullable = false)
    private String username;
    @Getter
    @Setter
    @Column(name = "password", length = 60)
    private String password;
    @Getter
    @Setter
    @NotNull(message = "{user.active}")
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
     *
     * @return
     */
    @Override
    public boolean isBlocked() {
        return !this.isActive();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isLdapBindAccount() {
        return this.storeType == StoreType.LDAP;
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getPermissions() {
        return this.group != null ? this.group.getPermissions() : new HashSet<>();
    }

    /**
     * @return o nome do grupo deste usuario
     */
    public String getGroupName() {
        return this.group != null ? this.group.getName() : null;
    }

    /**
     *
     * @return
     */
    public boolean isPasswordValid() {
        return isNotBlank(this.password)
                && isNotBlank(this.passwordConfirmation)
                && this.password.equals(this.passwordConfirmation);
    }

    /**
     *
     * @return
     */
    public boolean hasChangedPasswords() {
        return isNotBlank(this.password)
                && isNotBlank(this.passwordConfirmation);
    }

    /**
     *
     * @return
     */
    public boolean isAdministrator() {
        return this.username.equals("admin");
    }
}

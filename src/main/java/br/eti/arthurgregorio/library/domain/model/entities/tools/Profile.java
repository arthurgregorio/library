package br.eti.arthurgregorio.library.domain.model.entities.tools;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY_AUDIT;

/**
 * The user profile entity
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
@Entity
@Audited
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "profiles", schema = SECURITY)
@AuditTable(value = "audit_profiles", schema = SECURITY_AUDIT)
public class Profile extends PersistentEntity {

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "active_theme", nullable = false, length = 45)
    private ThemeType activeTheme;
    @Getter
    @Setter
    @Column(name = "dark_sidebar", nullable = false)
    private boolean userDarkSidebar;

    /**
     * Constructor
     */
    public Profile() {
        this.activeTheme = ThemeType.BLACK;
        this.userDarkSidebar = true;
    }
}

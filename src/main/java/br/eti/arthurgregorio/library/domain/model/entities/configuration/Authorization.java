package br.eti.arthurgregorio.library.domain.model.entities.configuration;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.CONFIGURATION;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.CONFIGURATION_AUDIT;

/**
 * The authorization entity
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 26/12/2017
 */
@Entity
@Audited
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "authorizations", schema = CONFIGURATION)
@AuditTable(value = "authorizations", schema = CONFIGURATION_AUDIT)
public class Authorization extends PersistentEntity {
    
    @Getter
    @Setter
    @Column(name = "functionality", nullable = false, length = 90)
    private String functionality;
    @Getter
    @Setter
    @Column(name = "permission", nullable = false, length = 90)
    private String permission;

    /**
     * Constructor
     *
     * @param functionality the functionality to authorize
     * @param permission the permission for the functionality
     */
    public Authorization(String functionality, String permission) {
        this.functionality = functionality;
        this.permission = permission;
    }

    /**
     * @return the full authorization (functionality + permission)
     */
    public String getFullPermission() {
        return this.functionality + ":" + this.permission;
    }
    
    /**
     * Check if this permission if for the given functionality
     * 
     * @param functionality the functionality to test
     * @return true if is, false otherwise
     */
    public boolean isFunctionality(String functionality) {
        return this.functionality.equals(functionality);
    }

    /**
     * Compare two permissions
     * 
     * @param permission the permission to compare
     * @return true if they ara equal, false otherwise
     */
    public boolean isPermission(String permission) {
        return permission != null && (this.permission.equals(permission) || this.getFullPermission().equals(permission));
    }
}

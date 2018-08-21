package br.eti.arthurgregorio.library.domain.model.entities.tools;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY_AUDIT;

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
@Table(name = "authorizations", schema = SECURITY)
@AuditTable(value = "authorizations", schema = SECURITY_AUDIT)
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
     * 
     * @param functionality
     * @param permission 
     */
    public Authorization(String functionality, String permission) {
        this.functionality = functionality;
        this.permission = permission;
    }

    /**
     * @return 
     */
    public String getFullPermission() {
        return this.functionality + ":" + this.permission;
    }
    
    /**
     * 
     * @param functionality
     * @return 
     */
    public boolean isFunctionality(String functionality) {
        return functionality != null && this.functionality.equals(functionality);
    }

    /**
     * 
     * @param permission
     * @return 
     */
    public boolean isPermission(String permission) {
        return permission != null && (this.permission.equals(permission) 
                || this.getFullPermission().equals(permission));
    }
}

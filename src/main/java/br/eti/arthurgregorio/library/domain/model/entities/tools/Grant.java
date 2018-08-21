package br.eti.arthurgregorio.library.domain.model.entities.tools;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY_AUDIT;

/**
 * The grant for authorization entity
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
@Table(name = "grants", schema = SECURITY)
@AuditTable(value = "grants", schema = SECURITY_AUDIT)
public class Grant extends PersistentEntity {

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_group", nullable = false)
    private Group group;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_authorization", nullable = false)
    private Authorization authorization;

    /**
     * 
     * @param group
     * @param authorization 
     */
    public Grant(Group group, Authorization authorization) {
        this.group = group;
        this.authorization = authorization;
    }
}

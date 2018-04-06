package br.eti.arthurgregorio.library.domain.model.entities.security;

import br.eti.arthurgregorio.library.application.components.Color;
import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import br.eti.arthurgregorio.library.infrastructure.jpa.ColorConverter;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY_AUDIT;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * The user profile entity
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 26/12/2017
 */
@Entity
@Audited
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "profiles", schema = SECURITY)
@AuditTable(value = "profiles", schema = SECURITY_AUDIT)
public class Profile extends PersistentEntity {

    @Getter
    @Setter
    @Column(name = "color", nullable = false)
    @Convert(converter = ColorConverter.class)
    private Color color;

    /**
     * 
     */
    public Profile() {
        this.color = Color.randomize();
    }
}

package br.eti.arthurgregorio.library.domain.entities.registration;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

import static br.eti.arthurgregorio.library.infrastructure.misc.DefaultSchemes.REGISTRATION;

/**
 * The author entity
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Entity
@Audited
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "authors", schema = REGISTRATION)
public class Author extends PersistentEntity {

    @Getter
    @Setter
    @NotBlank(message = "{author.name}")
    @Column(name = "name", length = 90, nullable = false)
    private String name;
    @Getter
    @Setter
    @Past(message = "{author.born-date-past}")
    @Column(name = "born_date")
    private LocalDate bornDate;
    @Getter
    @Setter
    @NotBlank(message = "{author.email}")
    @Column(name = "email", length = 90, nullable = false)
    private String email;
    @Getter
    @Setter
    @Column(name = "active", nullable = false)
    private boolean active;

    /**
     *
     */
    public Author() {
        this.active = true;
    }
}

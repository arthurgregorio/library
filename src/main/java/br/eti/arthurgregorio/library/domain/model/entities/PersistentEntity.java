package br.eti.arthurgregorio.library.domain.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * The default implementation of a entity in the application. 
 * 
 * Every entity should extend this class to have the default behaviors of a 
 * JPA entity
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/12/2017
 */
@ToString
@MappedSuperclass
@NoArgsConstructor
@EqualsAndHashCode
public abstract class PersistentEntity implements IPersistentEntity<Long> {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    /**
     * {@inheritDoc }
     * 
     * @return
     */
    @Override
    public boolean isSaved() {
        return this.id != null && this.id != 0;
    }

    /**
     * The default implementation for this method throws a {@link UnsupportedOperationException}
     */
    @Override
    public void validate() {
        throw new UnsupportedOperationException("Not supported yet");
    }
}
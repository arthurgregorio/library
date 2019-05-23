package br.eti.arthurgregorio.library.domain.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The default implementation of a entity in the application. 
 *
 * Every entity should extend this class to have the default behaviors of a JPA entity
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
    @GenericGenerator(
            name = "pooled_sequence_generator",
            strategy = "enhanced-sequence",
            parameters = {
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "5"),
                    @Parameter(name = "optimizer", value = "pooled-lo")
            })
    @Column(name = "id", unique = true, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pooled_sequence_generator")
    private Long id;

    @Getter
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Getter
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    /**
     * Set the date of creation for this entity
     */
    @PrePersist
    protected void beforeInsert() {
        this.createdOn = LocalDateTime.now();
    }

    /**
     * Set the date of update for this entity
     */
    @PreUpdate
    protected void beforeUpdate() {
        this.updatedOn = LocalDateTime.now();
    }

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
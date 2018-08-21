package br.eti.arthurgregorio.library.domain.model.entities;

import lombok.*;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The revision entity to store all the audit revisions of the other entities
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 04/04/2018
 */
@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "revisions")
@RevisionEntity(RevisionListener.class)
public class Revision implements Serializable {

    @Id
    @Getter
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;
    @Getter
    @RevisionTimestamp
    @Column(name = "timestamp", nullable = false)    
    private Long timestamp;
    @Getter
    @Setter
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Getter
    @Setter
    @Column(name = "created_by", length = 45, nullable = false)
    private String createdBy;
}

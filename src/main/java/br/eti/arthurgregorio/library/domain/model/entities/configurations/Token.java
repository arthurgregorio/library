package br.eti.arthurgregorio.library.domain.model.entities.configurations;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.CONFIGURATION;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.CONFIGURATION_AUDIT;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 24/10/18
 */
@Entity
@Audited
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "tokens", schema = CONFIGURATION)
@AuditTable(value = "tokens", schema = CONFIGURATION_AUDIT)
public class Token extends PersistentEntity {

    @Getter
    @Setter
    @Column(name = "hash", length = 45)
    private String hash;
    @Getter
    @Setter
    @Column(name = "created_on", length = 45)
    private Instant createdOn;
    @Getter
    @Setter
    @Column(name = "expiration", length = 45)
    private Instant expiration;
    @Getter
    @Setter
    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false, length = 45)
    private TokenType tokenType;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    /**
     *
     */
    public Token() {
        this.createdOn = Instant.now();
        this.expiration = Instant.now().plus(5, ChronoUnit.DAYS);
    }

    /**
     *
     * @param hash
     * @param ipAddress
     * @param tokenType
     * @param user
     */
    public Token(String hash, String ipAddress, TokenType tokenType, User user) {
        this();
        this.hash = hash;
        this.ipAddress = ipAddress;
        this.tokenType = tokenType;
        this.user = user;
    }
}

package br.eti.arthurgregorio.library.domain.model.entities.tools;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY;
import static br.eti.arthurgregorio.library.infrastructure.utilities.DefaultSchemes.SECURITY_AUDIT;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.EAGER;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

/**
 * The user group entity
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 26/12/2017
 */
@Entity
@Audited
@ToString(exclude = {"parent", "grants"})
@Table(name = "groups", schema = SECURITY)
@AuditTable(value = "groups", schema = SECURITY_AUDIT)
@EqualsAndHashCode(callSuper = true, exclude = {"parent"})
public class Group extends PersistentEntity {

    @Getter
    @Setter
    @NotNull(message = "{group.name}")
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Getter
    @Setter
    @NotNull(message = "{group.active}")
    @Column(name = "active", nullable = false)
    private boolean active;
    
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "id_parent")
    private Group parent;
    
    @OneToMany(mappedBy = "group", fetch = EAGER, cascade = REMOVE)
    private final List<Grant> grants;
    
    /**
     * 
     */
    public Group() { 
        this.active = true;
        this.grants = Collections.emptyList();
    }

    /**
     * 
     * @param name 
     */
    public Group(String name) {
        this();
        this.name = name;
    }
    
    /**
     * 
     * @param name
     * @param parent 
     */
    public Group(String name, Group parent) {
        this();
        this.name = name;
        this.parent = parent;
    }

    /**
     * 
     * @param grant 
     */
    public void addGrant(Grant grant) {
        this.grants.add(grant);
    }
    
    /**
     * 
     * @param grants 
     */
    public void addGrants(List<Grant> grants) {
        this.grants.addAll(grants);
    }

    /**
     * @return os grants do grupo e seu superior
     */
    public List<Grant> getGrants() {
        
        final List<Grant> groupGrants = new ArrayList<>(this.grants);
        
        if (this.parent != null) {
            groupGrants.addAll(this.parent.getGrants());
        }
        return Collections.unmodifiableList(groupGrants);
    }

    /**
     * @return as permissoes deste grupo e seu superior
     */
    public Set<String> getPermissions() {
        return this.getGrants().stream()
                    .map(Grant::getAuthorization)
                    .map(Authorization::getFullPermission)
                    .collect(Collectors.toSet());
    }

    /**
     * 
     * @return 
     */
    public boolean isAdministrator() {
        return this.name.equals("Administradores");
    }
}

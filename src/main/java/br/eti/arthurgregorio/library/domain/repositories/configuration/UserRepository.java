package br.eti.arthurgregorio.library.domain.repositories.configuration;

import br.eti.arthurgregorio.library.domain.model.entities.configuration.StoreType;
import br.eti.arthurgregorio.library.domain.model.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.model.entities.configuration.User_;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The {@link User} accounts repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/12/2017
 */
@Repository
public interface UserRepository extends DefaultRepository<User> {

    /**
     * Find an {@link User} by the email address
     *
     * @param email the {@link User} email address to find
     * @return an {@link Optional} of the {@link User}
     */
    Optional<User> findByEmail(String email);

    /**
     * Find an {@link User} by the email address and the {@link StoreType}
     *
     * @param email the {@link User} email address to find
     * @param storeType the enum value of {@link StoreType}
     * @return an {@link Optional} of the {@link User}
     */
    Optional<User> findByEmailAndStoreType(String email, StoreType storeType);

    /**
     * Find an {@link User} by the username
     *
     * @param username the username to find the {@link User} object
     * @return an {@link Optional} of the {@link User}
     */
    Optional<User> findByUsername(String username);

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default SingularAttribute<User, Boolean> getEntityStateProperty() {
        return User_.active;
    }

    /**
     * {@inheritDoc}
     *
     * @param filter
     * @return
     */
    @Override
    default Collection<Criteria<User, User>> getRestrictions(String filter) {
        return List.of(
                this.criteria().likeIgnoreCase(User_.name, this.likeAny(filter)),
                this.criteria().likeIgnoreCase(User_.username, this.likeAny(filter)),
                this.criteria().likeIgnoreCase(User_.email, this.likeAny(filter)));
    }
}

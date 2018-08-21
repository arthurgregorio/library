package br.eti.arthurgregorio.library.domain.repositories.tools;

import br.eti.arthurgregorio.library.domain.model.entities.tools.StoreType;
import br.eti.arthurgregorio.library.domain.model.entities.tools.User;
import br.eti.arthurgregorio.library.domain.model.entities.tools.User_;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Optional;

/**
 * The user accounts repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/12/2017
 */
@Repository
public interface UserRepository extends DefaultRepository<User> {

    /**
     * 
     * @param email
     * @return 
     */
    Optional<User> findOptionalByEmail(String email);
    
    /**
     * 
     * @param username
     * @return 
     */
    Optional<User> findOptionalByUsername(String username);
    
    /**
     * 
     * @param email
     * @param storeType
     * @return 
     */
    Optional<User> findOptionalByEmailAndStoreType(String email, StoreType storeType);

    /**
     * {@inheritDoc }
     * 
     * @param filter
     * @return 
     */
    @Override
    default Criteria<User, User> getRestrictions(String filter) {
        return this.criteria()
                .likeIgnoreCase(User_.name, filter)
                .likeIgnoreCase(User_.username, filter)
                .likeIgnoreCase(User_.email, filter);
    }

    /**
     * {@inheritDoc }
     * 
     * @return 
     */
    @Override
    default SingularAttribute<User, Boolean> getEntityStateProperty() {
        return User_.active;
    }

    /**
     * {@inheritDoc }
     * 
     * @param criteria 
     */
    @Override
    public default void setOrder(Criteria<User, User> criteria) {
        criteria.orderAsc(User_.name);
    }
}

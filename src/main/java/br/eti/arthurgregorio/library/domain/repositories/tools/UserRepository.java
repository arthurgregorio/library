package br.eti.arthurgregorio.library.domain.repositories.tools;

import br.eti.arthurgregorio.library.domain.model.entities.security.StoreType;
import br.eti.arthurgregorio.library.domain.model.entities.security.User;
import br.eti.arthurgregorio.library.domain.model.entities.security.User_;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.metamodel.SingularAttribute;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

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
     * @param blocked
     * @return 
     */
    List<User> findAllByBlocked(boolean blocked);
    
    /**
     * 
     * @param email
     * @return 
     */
    Optional<User> findOptionalByEmail(String email);
    
    /**
     * 
     * @param email
     * @param storeType
     * @return 
     */
    Optional<User> findOptionalByEmailAndStoreType(String email, StoreType storeType);
    
    /**
     * 
     * @param username
     * @return 
     */
    Optional<User> findOptionalByUsername(String username);

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
    default SingularAttribute<User, Boolean> getBlockedProperty() {
        return User_.blocked;
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

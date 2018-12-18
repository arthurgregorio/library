package br.eti.arthurgregorio.library.domain.repositories.registration;

import br.eti.arthurgregorio.library.domain.model.entities.registration.Author;
import br.eti.arthurgregorio.library.domain.model.entities.registration.Author_;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;

/**
 * The {@link Author} repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Repository
public interface AuthorRepository extends DefaultRepository<Author> {

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default SingularAttribute<Author, Boolean> getEntityStateProperty() {
        return Author_.active;
    }

    /**
     * {@inheritDoc}
     *
     * @param filter
     * @return
     */
    @Override
    default Collection<Criteria<Author, Author>> getRestrictions(String filter) {
        return List.of(
                this.criteria().likeIgnoreCase(Author_.name, this.likeAny(filter)),
                this.criteria().likeIgnoreCase(Author_.email, this.likeAny(filter)));
    }
}

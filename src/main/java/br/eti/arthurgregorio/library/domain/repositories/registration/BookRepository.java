package br.eti.arthurgregorio.library.domain.repositories.registration;

import br.eti.arthurgregorio.library.domain.model.entities.registration.Book;
import br.eti.arthurgregorio.library.domain.model.entities.registration.Book_;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;

/**
 * The {@link Book} repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Repository
public interface BookRepository extends DefaultRepository<Book> {

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default SingularAttribute<Book, Boolean> getEntityStateProperty() {
        return Book_.active;
    }

    /**
     * {@inheritDoc}
     *
     * @param filter
     * @return
     */
    @Override
    default Collection<Criteria<Book, Book>> getRestrictions(String filter) {
        return List.of(
                this.criteria().likeIgnoreCase(Book_.isbn, this.likeAny(filter)),
                this.criteria().likeIgnoreCase(Book_.title, this.likeAny(filter)));
    }
}

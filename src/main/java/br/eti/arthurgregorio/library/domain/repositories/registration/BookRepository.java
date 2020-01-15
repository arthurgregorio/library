package br.eti.arthurgregorio.library.domain.repositories.registration;

import br.eti.arthurgregorio.library.domain.entities.registration.Book;
import br.eti.arthurgregorio.library.domain.entities.registration.Book_;
import br.eti.arthurgregorio.library.infrastructure.deltaspike.repositories.DeltaSpikeRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.Criteria;

import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * The {@link Book} repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Repository
public interface BookRepository extends DeltaSpikeRepository<Book> {

    /**
     * Find a {@link Book} by the ISBN
     *
     * @param isbn to search for
     * @return an {@link Optional} of the {@link Book}
     */
    Optional<Book> findByISBN(String isbn);

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

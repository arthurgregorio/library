package br.eti.arthurgregorio.library.domain.repositories.registration;

import br.eti.arthurgregorio.library.domain.entities.registration.Author;
import br.eti.arthurgregorio.library.domain.entities.registration.QAuthor;
import br.eti.arthurgregorio.library.infrastructure.deltaspike.repositories.QueryDSLRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

/**
 * The {@link Author} repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Repository
public interface AuthorRepository extends QueryDSLRepository<Author> {

    /**
     * Find an {@link Author} by the e-mail address
     *
     * @param email the e-mail address to search
     * @return an {@link Optional} of the {@link Author}
     */
    Optional<Author> findByEmail(String email);

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default BooleanPath getQStateProperty() {
        return QAuthor.author.active;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default EntityPathBase<Author> getQType() {
        return QAuthor.author;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default OrderSpecifier<?>[] getQOrders() {
        return new OrderSpecifier<?>[]{QAuthor.author.id.asc()};
    }

    /**
     * {@inheritDoc}
     *
     * @param filter
     * @return
     */
    @Override
    default Predicate getQPredicate(String filter) {

        final QAuthor author = QAuthor.author;

        return author.name.likeIgnoreCase(this.likeAny(filter))
                .or(author.email.likeIgnoreCase(this.likeAny(filter)));
    }
}

package br.eti.arthurgregorio.library.domain.repositories.registration;

import br.eti.arthurgregorio.library.domain.entities.registration.Book;
import br.eti.arthurgregorio.library.domain.entities.registration.QBook;
import br.eti.arthurgregorio.library.infrastructure.deltaspike.repositories.QueryDSLRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.EntityPathBase;
import org.apache.deltaspike.data.api.Repository;

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
public interface BookRepository extends QueryDSLRepository<Book> {

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
    default BooleanPath getQStateProperty() {
        return QBook.book.active;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default EntityPathBase<Book> getQType() {
        return QBook.book;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    default OrderSpecifier<?>[] getQOrders() {
        return new OrderSpecifier<?>[]{QBook.book.id.asc()};
    }

    /**
     * {@inheritDoc}
     *
     * @param filter
     * @return
     */
    @Override
    default Predicate getQPredicate(String filter) {

        final QBook book = QBook.book;

        return book.isbn.likeIgnoreCase(this.likeAny(filter))
                .or(book.title.likeIgnoreCase(this.likeAny(filter)))
                .or(book.author.name.likeIgnoreCase(this.likeAny(filter)));
    }
}

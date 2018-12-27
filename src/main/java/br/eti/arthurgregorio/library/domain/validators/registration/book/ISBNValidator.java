package br.eti.arthurgregorio.library.domain.validators.registration.book;

import br.eti.arthurgregorio.library.domain.model.entities.registration.Book;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.registration.BookRepository;

import javax.inject.Inject;

/**
 * The {@link Book} ISBN validator
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 18/12/2018
 */
public class ISBNValidator implements BookSavingLogic {

    @Inject
    private BookRepository bookRepository;

    /**
     * {@inheritDoc}
     *
     * @param value
     */
    @Override
    public void run(Book value) {
        this.bookRepository.findByISBN(value.getIsbn()).ifPresent(book -> {
            throw new BusinessLogicException("error.book.isbn-duplicated", book.getIsbn());
        });
    }
}

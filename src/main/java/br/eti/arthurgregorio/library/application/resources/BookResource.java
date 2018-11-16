package br.eti.arthurgregorio.library.application.resources;

import br.eti.arthurgregorio.library.domain.model.entities.registration.Book;
import br.eti.arthurgregorio.library.domain.repositories.registration.BookRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * The {@link Book} REST endpoint
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Path("book")
public class BookResource {

    @Inject
    private BookRepository bookRepository;

    /**
     * GET all books
     *
     * @return all books
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> getBooks() {
        return this.bookRepository.findAll();
    }
}

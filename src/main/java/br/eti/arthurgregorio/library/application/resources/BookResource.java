package br.eti.arthurgregorio.library.application.resources;

import br.eti.arthurgregorio.library.domain.model.entities.registration.Book;
import br.eti.arthurgregorio.library.domain.model.exception.ResourceNotFoundException;
import br.eti.arthurgregorio.library.domain.repositories.registration.BookRepository;
import br.eti.arthurgregorio.library.infrastructure.jaxrs.ResponseBuilder;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * The {@link Book} REST endpoint
 *
 * @book Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Path("books")
public class BookResource {

    @Inject
    private BookRepository bookRepository;

    /**
     * Create a new {@link Book}
     *
     * @param book the {@link Book} to be saved
     * @param uri the URL info
     * @return http 200 response if everything works
     */
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid Book book, @Context UriInfo uri) {

        final Book created = this.bookRepository.save(book);

        return ResponseBuilder.to(uri)
                .withEntity(created.getId())
                .created();
    }

    /**
     * Update an {@link Book}
     *
     * @param book the {@link Book} to be updated
     */
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@Valid Book book) {
        this.findById(book.getId());
        this.bookRepository.save(book);
    }

    /**
     * Delete an {@link Book}
     *
     * @param id the id of the {@link Book} to be deleted
     */
    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") long id) {
        this.bookRepository.attachAndRemove(this.findById(id));
    }

    /**
     * GET all books
     *
     * @return all books
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    /**
     * GET an {@link Book} by the given id
     *
     * @param id the id to search
     * @return the {@link Book}
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book findById(@PathParam("id") long id) {
        return this.bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.book.cant-find-book", id));
    }
}

package br.eti.arthurgregorio.library.application.resources;

import br.eti.arthurgregorio.library.domain.model.entities.registration.Author;
import br.eti.arthurgregorio.library.domain.model.exception.ResourceNotFoundException;
import br.eti.arthurgregorio.library.domain.repositories.registration.AuthorRepository;
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
 * The {@link Author} REST endpoint
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Path("authors")
public class AuthorResource {

    @Inject
    private AuthorRepository authorRepository;

    /**
     * Create a new {@link Author}
     *
     * @param author the {@link Author} to be saved
     * @param uri the URL info
     * @return http 200 response if everything works
     */
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid Author author, @Context UriInfo uri) {

        final Author created = this.authorRepository.save(author);

        return ResponseBuilder.to(uri)
                .withEntity(created.getId())
                .created();
    }

    /**
     * Update an {@link Author}
     *
     * @param author the {@link Author} to be updated
     */
    @PUT
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@Valid Author author) {
        this.findById(author.getId());
        this.authorRepository.save(author);
    }

    /**
     * Delete an {@link Author}
     *
     * @param id the id of the {@link Author} to be deleted
     */
    @DELETE
    @Path("{id}")
    @Transactional
    public void delete(@PathParam("id") long id) {
        this.authorRepository.attachAndRemove(this.findById(id));
    }

    /**
     * GET all {@link Author}
     * 
     * @return all {@link Author}
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> findAll() {
        return this.authorRepository.findAll();
    }

    /**
     * GET an {@link Author} by the given id
     *
     * @param id the id to search
     * @return the {@link Author}
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Author findById(@PathParam("id") long id) {
        return this.authorRepository.findOptionalBy(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.author.cant-find-author", id));
    }
}

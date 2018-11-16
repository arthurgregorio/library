package br.eti.arthurgregorio.library.application.resources;

import br.eti.arthurgregorio.library.domain.model.entities.registration.Author;
import br.eti.arthurgregorio.library.domain.repositories.registration.AuthorRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * The {@link Author} REST endpoint
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Path("author")
public class AuthorResource {

    @Inject
    private AuthorRepository authorRepository;

    /**
     * GET all authors
     * 
     * @return all authors
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Author> getAuthors() {
        return this.authorRepository.findAll();
    }
}

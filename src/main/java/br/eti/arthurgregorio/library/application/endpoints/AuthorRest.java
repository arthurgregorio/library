/*
 * Copyright 2017 Arthur Gregorio, AG.Software.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.eti.arthurgregorio.library.application.endpoints;

import br.eti.arthurgregorio.library.domain.entities.Author;
import br.eti.arthurgregorio.library.domain.services.Library;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Arthur Gregorio
 *
 * @since 1.0.0
 * @version 1.0.0, 15/12/2017
 */
@Path("author")
public class AuthorRest {

    @Inject
    private Library library;
    
    /**
     * 
     * @param author
     * @return 
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Author author) {
        final Author created = this.library.save(author);
        return Response.ok(created, MediaType.APPLICATION_JSON).build();
    }
    
    /**
     * 
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        final List<Author> authors = this.library.listAllAuthors();
        return Response.ok(authors).build();
    }
}

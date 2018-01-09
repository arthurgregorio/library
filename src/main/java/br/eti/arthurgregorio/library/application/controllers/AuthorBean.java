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
package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.domain.entities.Author;
import br.eti.arthurgregorio.library.domain.repositories.AuthorRepository;
import br.eti.arthurgregorio.library.domain.services.LibraryService;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Arthur Gregorio
 *
 * @since 1.0.0
 * @version 1.0.0, 16/12/2017
 */
@Named
@ViewScoped
public class AuthorBean extends AbstractBean {

    @Getter
    @Setter
    private Author author;
    
    @Getter
    private List<Author> authors;

    @Inject
    private LibraryService libraryService;
    
    @Inject
    private AuthorRepository authorRepository;
    
    /**
     * 
     */
    public void initializeListing() {
        this.viewState = ViewState.LISTING;
        this.authors = this.authorRepository.findAll();
    }
    
    /**
     * 
     * @param id
     * @param viewState 
     */
    public void initializeForm(long id, String viewState) {
        
        this.viewState = ViewState.valueOf(viewState);
        
        switch (this.viewState) {
            case ADDING:
                this.author = new Author();
                break;
            case EDITING:
                this.author = this.libraryService.findAuthorById(id);
                break;
        }
    }
    
    /**
     * 
     * @return 
     */
    public String save() {
       
        try {
            this.author = this.libraryService.save(this.author);
            return "formAuthor.xhtml?faces-redirect=true&viewState=EDITING&id=" 
                    + this.author.getId();
        } catch (Exception ex) {
            this.addError(false, "Nao foi possivel salvar o autor");
            return null;
        } 
    }
    
    /**
     * 
     * @return 
     */
    public String update() {
        return "";
    }
}

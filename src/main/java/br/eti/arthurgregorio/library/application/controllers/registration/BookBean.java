package br.eti.arthurgregorio.library.application.controllers.registration;

import br.eti.arthurgregorio.library.application.components.ViewState;
import br.eti.arthurgregorio.library.application.components.table.Page;
import br.eti.arthurgregorio.library.application.controllers.FormBean;
import br.eti.arthurgregorio.library.domain.model.entities.registration.Author;
import br.eti.arthurgregorio.library.domain.model.entities.registration.Book;
import br.eti.arthurgregorio.library.domain.repositories.registration.AuthorRepository;
import br.eti.arthurgregorio.library.domain.repositories.registration.BookRepository;
import lombok.Getter;
import org.primefaces.model.SortOrder;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import java.util.List;

import static br.eti.arthurgregorio.library.application.components.NavigationManager.PageType.*;
import static br.eti.arthurgregorio.library.application.components.NavigationManager.PageType.DELETE_PAGE;

/**
 * The {@link Book} maintenance controller
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Named
@ViewScoped
public class BookBean extends FormBean<Book> {

    @Getter
    private List<Author> authors;

    @Inject
    private BookRepository bookRepository;
    @Inject
    private AuthorRepository authorRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        super.initialize();
        this.temporizeHiding(this.getDefaultMessagesComponentId());
    }

    /**
     * {@inheritDoc}
     *
     * @param id
     * @param viewState
     */
    @Override
    public void initialize(long id, ViewState viewState) {
        this.viewState = viewState;

        if (this.viewState.isEditable()) {
            this.authors = this.authorRepository.findAllActive();
        }

        this.value = this.bookRepository.findById(id).orElseGet(Book::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeNavigationManager() {
        this.navigation.addPage(LIST_PAGE, "listBooks.xhtml");
        this.navigation.addPage(ADD_PAGE, "formBook.xhtml");
        this.navigation.addPage(UPDATE_PAGE, "formBook.xhtml");
        this.navigation.addPage(DETAIL_PAGE, "detailBook.xhtml");
        this.navigation.addPage(DELETE_PAGE, "detailBook.xhtml");
    }

    /**
     * {@inheritDoc}
     *
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @Override
    public Page<Book> load(int first, int pageSize, String sortField, SortOrder sortOrder) {
        return this.bookRepository.findAllBy(this.filter.getValue(), this.filter.getEntityStatusValue(), first, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void doSave() {
        this.bookRepository.save(this.value);
        this.value = new Book();
        this.addInfo(true, "saved");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void doUpdate() {
        this.bookRepository.save(this.value);
        this.addInfo(true, "updated");
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    @Transactional
    public String doDelete() {
        this.bookRepository.attachAndRemove(this.value);
        this.addInfoAndKeep("deleted");
        return this.changeToListing();
    }
}

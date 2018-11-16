package br.eti.arthurgregorio.library.application.controllers.registration;

import br.eti.arthurgregorio.library.application.components.ViewState;
import br.eti.arthurgregorio.library.application.components.table.Page;
import br.eti.arthurgregorio.library.application.controllers.FormBean;
import br.eti.arthurgregorio.library.domain.model.entities.registration.Author;
import br.eti.arthurgregorio.library.domain.repositories.registration.AuthorRepository;
import org.primefaces.model.SortOrder;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import static br.eti.arthurgregorio.library.application.components.NavigationManager.PageType.*;

/**
 * The {@link Author} maintenance controller
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 14/11/2018
 */
@Named
@ViewScoped
public class AuthorBean extends FormBean<Author> {

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
        this.value = this.authorRepository.findById(id).orElseGet(Author::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeNavigationManager() {
        this.navigation.addPage(LIST_PAGE, "listAuthors.xhtml");
        this.navigation.addPage(ADD_PAGE, "formAuthor.xhtml");
        this.navigation.addPage(UPDATE_PAGE, "formAuthor.xhtml");
        this.navigation.addPage(DETAIL_PAGE, "detailAuthor.xhtml");
        this.navigation.addPage(DELETE_PAGE, "detailAuthor.xhtml");
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
    public Page<Author> load(int first, int pageSize, String sortField, SortOrder sortOrder) {
        return this.authorRepository.findAllBy(this.filter.getValue(), this.filter.getEntityStatusValue(), first, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void doSave() {
        this.authorRepository.save(this.value);
        this.value = new Author();
        this.addInfo(true, "saved");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void doUpdate() {
        this.authorRepository.save(this.value);
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
        this.authorRepository.attachAndRemove(this.value);
        this.addInfoAndKeep("deleted");
        return this.changeToListing();
    }
}
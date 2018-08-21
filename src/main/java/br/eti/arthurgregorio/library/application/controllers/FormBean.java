package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.application.components.NavigationManager;
import br.eti.arthurgregorio.library.application.components.ViewState;
import br.eti.arthurgregorio.library.application.components.table.LazyDataProvider;
import br.eti.arthurgregorio.library.application.components.table.LazyFilter;
import br.eti.arthurgregorio.library.application.components.table.LazyModel;
import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;

import java.util.List;

import static br.eti.arthurgregorio.library.application.components.NavigationManager.PageType.*;
import static br.eti.arthurgregorio.library.application.components.NavigationManager.Parameter.of;

/**
 * The abstract form controller, this class hold all the common functionalities that a single form will have.
 * 
 * This class already implement the lazy loading support for primefaces with  the implementation of the
 * {@link LazyDataProvider}
 *
 * @param <T> the type to be manipulated with this controller, needs to be a class of the domain model child of the
 * {@link PersistentEntity}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/03/2018
 */
public abstract class FormBean<T extends PersistentEntity> extends AbstractBean 
        implements LazyDataProvider<T> {

    @Getter
    @Setter
    protected T value;

    @Getter
    protected List<T> data;

    @Getter
    protected ViewState viewState;

    @Getter
    protected final LazyFilter filter;
    @Getter
    protected final LazyDataModel<T> dataModel;

    protected final NavigationManager navigation;

    /**
     * create the bean and initialize the default data
     */
    public FormBean() {

        this.dataModel = new LazyModel<>(this);

        this.filter = LazyFilter.getInstance();
        this.navigation = NavigationManager.getInstance();

        this.initializeNavigationManager();
    }

    /**
     *
     */
    protected abstract void initializeNavigationManager();

    /**
     *
     * @param id
     * @param viewState
     */
    public abstract void initialize(long id, String viewState);

    /**
     *
     */
    public abstract void doSave();

    /**
     *
     */
    public abstract void doUpdate();

    /**
     *
     * @return
     */
    public abstract String doDelete();
    
    /**
     *
     */
    public void initialize() {
        this.viewState = ViewState.LISTING;
    }

    /**
     *
     */
    public void updateListing() {
        this.updateComponent("itemsListing");
    }

    /**
     *
     */
    public void clearFilters() {
        this.filter.clear();
    }

    /**
     * @return
     */
    public String changeToListing() {
        return this.navigation.to(LIST_PAGE);
    }

    /**
     * @return
     */
    public String changeToAdd() {
        return this.navigation.to(ADD_PAGE);
    }

    /**
     * @param id
     * @return
     */
    public String changeToEdit(long id) {
        return this.navigation.to(UPDATE_PAGE, of("id", id));
    }

    /**
     *
     */
    public void changeToDetail() {
        this.navigation.redirect(DETAIL_PAGE, of("id", this.value.getId()));
    }

    /**
     *
     * @param id
     * @return
     */
    public String changeToDelete(long id) {
        return this.navigation.to(DELETE_PAGE, of("id", id));
    }
}

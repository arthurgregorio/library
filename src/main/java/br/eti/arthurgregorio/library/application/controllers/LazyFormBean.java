package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.application.components.table.LazyDataProvider;
import br.eti.arthurgregorio.library.application.components.table.LazyFilter;
import br.eti.arthurgregorio.library.application.components.table.LazyModel;
import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import lombok.Getter;
import org.primefaces.model.LazyDataModel;

import java.util.List;

/**
 * An implementation of the {@link FormBean} with support for lazy loading
 *
 * @param <T> the type to be manipulated by this controller, must be a domain entity child of {@link PersistentEntity}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
public abstract class LazyFormBean<T extends PersistentEntity> extends FormBean<T> implements LazyDataProvider<T> {

    @Getter
    protected List<T> data;

    @Getter
    protected final LazyFilter filter;
    @Getter
    protected final LazyDataModel<T> dataModel;

    /**
     * Create the bean and initialize the default data
     */
    public LazyFormBean() {
        this.dataModel = new LazyModel<>(this);
        this.filter = LazyFilter.getInstance();
    }

    /**
     * Clear the form filters
     */
    public void clearFilters() {
        this.filter.clear();
    }
}
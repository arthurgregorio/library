package br.eti.arthurgregorio.library.application.components.table;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

import java.util.List;

/**
 * The data provider for better use of lazy data model with Primefaces
 *
 * @param <T> the type of data for the provider to provide
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 19/03/2018
 */
public interface LazyDataProvider<T extends PersistentEntity> {

    /**
     * This is the simple method to retrieve data with lazy loading
     *
     * @param first the start of the pagination
     * @param pageSize the maximum size of the page
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @return the page to be paginated on the {@link LazyDataModel}
     */
    Page<T> load(int first, int pageSize, String sortField, SortOrder sortOrder);
    
    /**
     * Used to retrieve data from database with multi-sorting from data component
     *
     * If you don't want to use multi-sorting, there's no need to implement this method because it already have a
     * default implementation with empty list return
     *
     * @param first the start of the pagination
     * @param pageSize the maximum size of the page
     * @param sortFields the fields to sort
     * @return the page to be paginated on the {@link LazyDataModel}
     */
    default public Page<T> load(int first, int pageSize, List<SortMeta> sortFields) {
        return Page.empty();
    }
}

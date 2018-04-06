package br.eti.arthurgregorio.library.application.components.table;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import java.util.Collections;
import java.util.List;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 * The data provider for better use of lazy datamodel with Primefaces
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
     * Used to retrieve data from databse with multisorting from data component
     * 
     * If you don't want to use multisorting, theres no need to implement this
     * method because it already have a default implementation with empty list
     * return
     * 
     * @param first
     * @param pageSize
     * @param sortFields
     * @return 
     */
    default public List<T> load(int first, int pageSize, List<SortMeta> sortFields) {
        return Collections.emptyList();
    }
    
    /**
     * This is the simple method to retrieve data with lazy loading
     * 
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder 
     * @return 
     */
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder);
}

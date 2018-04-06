package br.eti.arthurgregorio.library.domain.model.entities;

import java.time.LocalDateTime;
import org.apache.shiro.SecurityUtils;

/**
 * The listener to add more info to the revision of the audited entities
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/12/2017
 */
public class RevisionListener implements org.hibernate.envers.RevisionListener {

    /**
     * {@inheritDoc }
     * 
     * @param revisionEntity 
     */
    @Override
    public void newRevision(Object revisionEntity) {
        
        final Revision revision = (Revision) revisionEntity;
        
        revision.setCreatedOn(LocalDateTime.now());
        revision.setCreatedBy(this.getLoggedUser());
    }
    
    /**
     * Get the username of the logged user
     * 
     * @return the username of the logged user
     */
    private String getLoggedUser() {
        try {
            return String.valueOf(SecurityUtils.getSubject().getPrincipal());
        } catch (Exception ex) {
            return "unknow";
        }
    }
}

package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.domain.services.RecoverPasswordService;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 * The controller of the recover password process
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 29/12/2017
 */
@Named
@ViewScoped
public class RecoverPasswordBean extends AbstractBean {

    @Getter
    @Setter
    private String email;
    
    @Inject
    private RecoverPasswordService recoverPasswordService;
    
    /**
     * Call the service to reset the user password
     */
    public void recoverPassword() {
        
        this.recoverPasswordService.recover(this.email);
        
        this.closeDialog("dialogRecoverPassword");
        this.addInfoAndKeep("recover-password.email-sent");
        this.temporizeHiding("messages");
    }
    
    /**
     * Open the recover password dialog
     */
    public void showRecoverPassDialog() {
        this.email = null;
        this.updateAndOpenDialog("recoverPasswordDialog", "dialogRecoverPassword"); 
    }
}

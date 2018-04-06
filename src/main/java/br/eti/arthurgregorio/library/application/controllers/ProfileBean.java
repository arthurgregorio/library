package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.domain.model.entities.security.User;
import br.eti.arthurgregorio.library.domain.services.UserAccountService;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 * The bean to control all the user preferences in their profile
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/03/2018
 */
@Named
@ToString
@ViewScoped
public class ProfileBean extends AbstractBean {

    @Inject
    private UserSessionBean userSessionBean;
    
    @Inject
    private UserAccountService userAccountService;
    
    @Getter
    private PasswordChangeDTO passwordChangeDTO;
    
    /**
     * 
     */
    public void changePassword() {
        
        final User principal = this.userSessionBean.getPrincipal();
        
        this.userAccountService.changePasswordForCurrentUser(
                this.passwordChangeDTO, principal);
        
        this.passwordChangeDTO = new PasswordChangeDTO();
        this.addInfo(true, "profile.password-changed");
    }
    
    /**
     * 
     */
    public void showChangePasswordPopup() {
        this.passwordChangeDTO = new PasswordChangeDTO();
        this.updateAndOpenDialog("changePasswordDialog", "dialogChangePassword");
    }
    
    /**
     * A simple DTO to transfer the data through the layers of the system
     */
    public class PasswordChangeDTO {
        
        @Getter
        @Setter
        @NotBlank(message = "{profile.actual-password}")
        private String actualPassword;
        @Getter
        @Setter
        @NotBlank(message = "{profile.new-password}")
        private String newPassword;
        @Getter
        @Setter
        @NotBlank(message = "{profile.new-password-confirmation}")
        private String newPasswordConfirmation;

        /**
         * @return check if the new password is matching with the confirmation
         */
        public boolean isNewPassMatching() {
            return this.newPassword.equals(newPasswordConfirmation);
        }
    }
}

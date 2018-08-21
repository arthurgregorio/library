package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.domain.model.entities.tools.Profile;
import br.eti.arthurgregorio.library.domain.model.entities.tools.ThemeType;
import br.eti.arthurgregorio.library.domain.model.entities.tools.User;
import br.eti.arthurgregorio.library.domain.services.UserAccountService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

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

    @Getter
    private Profile profile;

    @Inject
    private UserSessionBean userSessionBean;

    @Inject
    private UserAccountService userAccountService;

    @Getter
    private PasswordChangeDTO passwordChangeDTO;

    /**
     * Initialize this bean with the current user preferences
     */
    @PostConstruct
    public void initialize() {
        this.passwordChangeDTO = new PasswordChangeDTO();
        this.profile = this.userSessionBean.getPrincipal().getProfile();
    }

    /**
     * Method used to dynamically change the user interface theme
     *
     * @param themeType the selected theme
     */
    public void changeTheme(ThemeType themeType) {

        // replace the theme for the new one
        this.executeScript("$(\"body\").removeClass('"+ this.profile.getActiveTheme().getValue() + "')");
        this.executeScript("$(\"body\").addClass('" + themeType.getValue() + "')");

        // update the user profile with the theme and save
        this.profile.setActiveTheme(themeType);
        this.profile = this.userAccountService.updateUserProfile(this.profile);
    }

    /**
     * Use this method to get the current theme of the user preferences
     *
     * @return the current selected theme
     */
    public String getCurrentTheme() {
        return this.profile.getActiveTheme().getValue();
    }

    /**
     * Return the current theme color name
     *
     * @return the color name
     */
    public String getCurrentThemeColorName() {
        return this.profile.getActiveTheme().getColorName();
    }

    /**
     * FIXME ver pq nao esta trocando a senha
     */
    public void changePassword() {

        final User principal = this.userSessionBean.getPrincipal();

        this.userAccountService.changePassword(this.passwordChangeDTO, principal);

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

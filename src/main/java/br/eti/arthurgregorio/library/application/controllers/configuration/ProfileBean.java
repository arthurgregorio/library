package br.eti.arthurgregorio.library.application.controllers.configuration;

import br.eti.arthurgregorio.library.application.components.ui.AbstractBean;
import br.eti.arthurgregorio.library.application.controllers.UserSessionBean;
import br.eti.arthurgregorio.library.domain.entities.configuration.Profile;
import br.eti.arthurgregorio.library.domain.entities.configuration.ThemeType;
import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.services.UserAccountService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;

/**
 * The bean to control all the user preferences in their profile
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/03/2018
 */
@Named
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
        this.profile = this.userSessionBean.getPrincipal().getProfile();
    }

    /**
     * Update the user profile
     */
    public void updateProfile() {
        this.profile = this.userAccountService.updateUserProfile(this.profile);
        this.addInfo(true, "info.profile.updated");
    }

    /**
     * Method used to dynamically change the user interface theme
     *
     * @param themeType the selected theme
     */
    public void changeTheme(ThemeType themeType) {
        this.profile.setActiveTheme(themeType);
        this.executeScript("changeSkin('" + this.profile.getActiveTheme().getValue() + "')");
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
     * Start the password changing process
     */
    public void changePassword() {

        final User principal = this.userSessionBean.getPrincipal();

        this.userAccountService.changePassword(this.passwordChangeDTO, principal);

        this.passwordChangeDTO = new PasswordChangeDTO();
        this.addInfo(true, "profile.password-changed");
    }

    /**
     * Open the dialog to change de password
     */
    public void showChangePasswordDialog() {
        this.passwordChangeDTO = new PasswordChangeDTO();
        this.updateAndOpenDialog("changePasswordDialog", "dialogChangePassword");
    }

    /**
     * A simple DTO to transfer the password change data through the application layers
     */
    public class PasswordChangeDTO {

        @Getter
        @Setter
        @NotBlank(message = "{change-password.actual-password}")
        private String actualPassword;
        @Getter
        @Setter
        @NotBlank(message = "{change-password.new-password}")
        private String newPassword;
        @Getter
        @Setter
        @NotBlank(message = "{change-password.new-password-confirmation}")
        private String newPasswordConfirmation;

        /**
         * Method used to check if the passwords informed are matching
         *
         * @return true if is, false otherwise
         */
        public boolean isNewPassMatching() {
            return this.newPassword.equals(this.newPasswordConfirmation);
        }
    }
}
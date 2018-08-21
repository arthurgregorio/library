package br.eti.arthurgregorio.library.application.controllers.tools;

import br.eti.arthurgregorio.library.application.components.ViewState;
import br.eti.arthurgregorio.library.application.components.table.Page;
import br.eti.arthurgregorio.library.application.controllers.FormBean;
import br.eti.arthurgregorio.library.domain.model.entities.tools.Group;
import br.eti.arthurgregorio.library.domain.model.entities.tools.StoreType;
import br.eti.arthurgregorio.library.domain.model.entities.tools.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.tools.GroupRepository;
import br.eti.arthurgregorio.library.domain.repositories.tools.UserRepository;
import br.eti.arthurgregorio.library.domain.services.UserAccountService;
import br.eti.arthurgregorio.library.infrastructure.utilities.Configurations;
import br.eti.arthurgregorio.shiroee.config.ldap.LdapUser;
import br.eti.arthurgregorio.shiroee.config.ldap.LdapUserProvider;
import lombok.Getter;
import org.primefaces.model.SortOrder;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import static br.eti.arthurgregorio.library.application.components.NavigationManager.PageType.*;

/**
 * The controller for the user accounts operations
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/01/2018
 */
@Named
@ViewScoped
public class UserBean extends FormBean<User> {

    @Getter
    private List<Group> groups;

    @Inject
    private UserRepository userRepository;
    @Inject
    private GroupRepository groupRepository;

    @Inject
    private LdapUserProvider ldapUserProvider;

    @Inject
    private UserAccountService localAccountService;

    /**
     *
     */
    @Override
    public void initialize() {
        this.viewState = ViewState.LISTING;
    }

    /**
     *
     * @param id
     * @param viewState
     */
    @Override
    public void initialize(long id, String viewState) {

        this.viewState = ViewState.valueOf(viewState);

        this.groups = this.groupRepository.findAllActive();
        this.value = this.userRepository.findOptionalById(id)
                .orElseGet(User::new);
    }

    /**
     *
     */
    @Override
    protected void initializeNavigationManager() {
        this.navigation.addPage(LIST_PAGE, "listUsers.xhtml");
        this.navigation.addPage(ADD_PAGE, "formUser.xhtml");
        this.navigation.addPage(UPDATE_PAGE, "formUser.xhtml");
        this.navigation.addPage(DETAIL_PAGE, "detailUser.xhtml");
        this.navigation.addPage(DELETE_PAGE, "detailUser.xhtml");
    }

    /**
     *
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @Override
    public Page<User> load(int first, int pageSize, String sortField, SortOrder sortOrder) {
        return this.userRepository.findAllBy(this.filter.getValue(),
                this.filter.getEntityStatusValue(), first, pageSize);
    }

    /**
     *
     */
    @Override
    public void doSave() {
        this.localAccountService.save(this.value);
        this.value = new User();
        this.addInfo(true, "saved");
    }

    /**
     *
     */
    @Override
    public void doUpdate() {
        this.localAccountService.update(this.value);
        this.addInfo(true, "updated");
    }

    /**
     *
     * @return
     */
    @Override
    public String doDelete() {
        this.localAccountService.delete(this.value);
        this.addInfoAndKeep("deleted");
        return this.changeToListing();
    }

    /**
     * Method to find a given username on the LDAP repository and attach them to
     * a local account for binding pourpouses
     */
    public void findUserOnLdap() {

        final boolean ldapEnable = Configurations.getAsBoolean("ldap.enabled");

        if (!ldapEnable) {
            throw new BusinessLogicException("error.user.ldap-not-enabled");
        }

        final String username = this.value.getUsername();

        final LdapUser userDetails = this.ldapUserProvider
                .search(username)
                .orElseThrow(() -> new BusinessLogicException(
                        "error.user.not-found-ldap", username));

        this.value.setUsername(userDetails.getSAMAccountName());
        this.value.setEmail(userDetails.getMail());
        this.value.setName(userDetails.getName());
    }

    /**
     * @return
     */
    public StoreType[] getStoreTypeValues() {
        return StoreType.values();
    }
}

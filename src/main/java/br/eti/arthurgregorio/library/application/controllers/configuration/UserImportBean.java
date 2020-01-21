package br.eti.arthurgregorio.library.application.controllers.configuration;

import br.eti.arthurgregorio.library.application.components.ui.AbstractBean;
import br.eti.arthurgregorio.library.domain.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.configuration.GroupRepository;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import br.eti.arthurgregorio.library.domain.services.AccountService;
import br.eti.arthurgregorio.library.infrastructure.misc.Configurations;
import br.eti.arthurgregorio.library.infrastructure.shiro.ldap.LdapObjectMapper;
import br.eti.arthurgregorio.library.infrastructure.shiro.ldap.LdapRepository;
import br.eti.arthurgregorio.library.infrastructure.shiro.ldap.LdapSearchOption;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.3.0, 12/08/2019
 */
@Named
@ViewScoped
public class UserImportBean extends AbstractBean {

    @Getter
    private boolean ldapEnabled;

    @Getter
    @Setter
    private String filter;
    @Getter
    @Setter
    private Group selectedGroup;
    @Getter
    @Setter
    private LdapSearchOption ldapSearchOption;

    @Setter
    @Getter
    private List<User> selectedUsers;

    @Getter
    private List<User> users;
    @Getter
    private List<Group> groups;

    @Inject
    private UserRepository userRepository;
    @Inject
    private LdapRepository ldapRepository;
    @Inject
    private GroupRepository groupRepository;

    @Inject
    private AccountService accountService;

    /**
     *
     */
    public void initialize() {

        this.ldapEnabled = Configurations.getAsBoolean("ldap.enabled");

        this.users = new ArrayList<>();
        this.selectedUsers = new ArrayList<>();
        this.ldapSearchOption = LdapSearchOption.BY_EMAIL;
        this.groups = this.groupRepository.findAllActive();
    }

    /**
     *
     */
    public void search() {
        this.users = this.ldapRepository.listBy(this.ldapSearchOption, this.filter, new LdapObjectMapper<>(), User.class);
        this.users.sort(Comparator.comparing(User::getName));

        // check if the users are already imported
        final List<User> existentUsers = this.userRepository.findAll();

        this.users.forEach(user ->
                existentUsers.stream()
                        .filter(existent -> existent.getUsername().equals(user.getUsername()))
                        .findAny()
                        .ifPresent(existent -> {
                            user.setImported(true);
                            user.setActive(existent.isActive());
                        })
        );

    }

    /**
     *
     */
    public void importSelected() {
        this.accountService.importFromLdap(this.selectedUsers, this.selectedGroup);
        this.selectedUsers.clear();
        this.closeDialog("dialogSelectGroup");
        this.addInfo(true, "info.user-import.imported");
        this.updateComponent("messages");
        this.updateComponent("boxBody");
    }

    /**
     *
     */
    public void showSelectGroupDialog() {

        if (this.selectedUsers == null || this.selectedUsers.isEmpty()) {
            throw new BusinessLogicException("error.import-user.no-users");
        }

        this.selectedGroup = null;
        this.updateAndOpenDialog("selectGroupDialog", "dialogSelectGroup");
    }

    /**
     *
     */
    public void clearFilters() {
        this.filter = null;
        this.ldapSearchOption = null;
        this.users.clear();
        this.selectedUsers.clear();
    }

    /**
     *
     * @return
     */
    public LdapSearchOption[] getLdapSearchOptions() {
        return LdapSearchOption.values();
    }
}

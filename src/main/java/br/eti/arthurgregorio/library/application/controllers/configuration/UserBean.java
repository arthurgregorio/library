package br.eti.arthurgregorio.library.application.controllers.configuration;

import br.eti.arthurgregorio.library.application.cdi.qualifier.UserCreated;
import br.eti.arthurgregorio.library.application.cdi.qualifier.UserUpdated;
import br.eti.arthurgregorio.library.application.components.ui.LazyFormBean;
import br.eti.arthurgregorio.library.application.components.ui.NavigationManager;
import br.eti.arthurgregorio.library.application.components.ui.ViewState;
import br.eti.arthurgregorio.library.application.components.ui.table.Page;
import br.eti.arthurgregorio.library.domain.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.entities.configuration.StoreType;
import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.logics.configuration.user.UserDeletingLogic;
import br.eti.arthurgregorio.library.domain.logics.configuration.user.UserSavingLogic;
import br.eti.arthurgregorio.library.domain.logics.configuration.user.UserUpdatingLogic;
import br.eti.arthurgregorio.library.domain.repositories.configuration.GroupRepository;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import lombok.Getter;
import org.primefaces.model.SortOrder;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;

/**
 * The controller for the user accounts operations
 *
 * @author Arthur Gregorio
 *
 * @version 1.1.0
 * @since 1.0.0, 09/01/2018
 */
@Named
@ViewScoped
public class UserBean extends LazyFormBean<User> {

    @Getter
    private List<Group> groups;

    @Inject
    private UserRepository userRepository;
    @Inject
    private GroupRepository groupRepository;

    @Any
    @Inject
    private Instance<UserSavingLogic> userSavingValidators;
    @Any
    @Inject
    private Instance<UserUpdatingLogic> userUpdatingValidators;
    @Any
    @Inject
    private Instance<UserDeletingLogic> userDeletingValidators;

    @Inject
    @UserCreated
    private Event<User> userCreatedEvent;
    @Inject
    @UserUpdated
    private Event<User> userUpdatedEvent;

    /**
     * {@inheritDoc}
     *
     * @param id
     * @param viewState
     */
    @Override
    public void initialize(long id, ViewState viewState) {
        this.viewState = viewState;
        this.groups = this.groupRepository.findAllActive();
        this.value = this.userRepository.findById(id).orElseGet(User::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initializeNavigationManager() {
        this.navigation.addPage(NavigationManager.PageType.LIST_PAGE, "listUsers.xhtml");
        this.navigation.addPage(NavigationManager.PageType.ADD_PAGE, "formUser.xhtml");
        this.navigation.addPage(NavigationManager.PageType.UPDATE_PAGE, "formUser.xhtml");
        this.navigation.addPage(NavigationManager.PageType.DETAIL_PAGE, "detailUser.xhtml");
        this.navigation.addPage(NavigationManager.PageType.DELETE_PAGE, "detailUser.xhtml");
    }

    /**
     * {@inheritDoc}
     *
     * @param first
     * @param pageSize
     * @param sortField
     * @param sortOrder
     * @return
     */
    @Override
    public Page<User> load(int first, int pageSize, String sortField, SortOrder sortOrder) {
        return this.userRepository.findAllBy(this.filter.getValue(), this.filter.getEntityStatusValue(), first, pageSize);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void doSave() {
        this.userSavingValidators.forEach(validator -> validator.run(this.value));
        this.userCreatedEvent.fire(this.userRepository.save(this.value));
        this.value = new User();
        this.addInfo(true, "saved");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void doUpdate() {
        this.userUpdatingValidators.forEach(validator -> validator.run(this.value));
        this.userRepository.saveAndFlushAndRefresh(this.value);
        this.addInfo(true, "updated");
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    @Transactional
    public String doDelete() {
        this.userDeletingValidators.forEach(validator -> validator.run(this.value));
        this.userRepository.attachAndRemove(this.value);
        this.addInfoAndKeep("deleted");
        return this.changeToListing();
    }

    /**
     * Get the possible values for the storage place of an {@link User}
     *
     * @return an array with {@link StoreType} values
     */
    public StoreType[] getStoreTypes() {
        return StoreType.values();
    }
}

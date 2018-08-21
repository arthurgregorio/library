package br.eti.arthurgregorio.library.application.controllers.tools;

import br.eti.arthurgregorio.library.application.components.ViewState;
import br.eti.arthurgregorio.library.application.components.table.Page;
import br.eti.arthurgregorio.library.application.controllers.FormBean;
import br.eti.arthurgregorio.library.domain.model.entities.tools.Authorization;
import br.eti.arthurgregorio.library.domain.model.entities.tools.Grant;
import br.eti.arthurgregorio.library.domain.model.entities.tools.Group;
import br.eti.arthurgregorio.library.domain.model.entities.tools.Permissions;
import br.eti.arthurgregorio.library.domain.repositories.tools.GroupRepository;
import br.eti.arthurgregorio.library.domain.services.UserAccountService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.SortOrder;
import org.primefaces.model.TreeNode;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.eti.arthurgregorio.library.application.components.NavigationManager.PageType.*;

/**
 * The controller for the user groups operations
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/01/2018
 */
@Named
@ViewScoped
public class GroupBean extends FormBean<Group> {

    @Getter
    private TreeNode treeRoot;
    @Getter
    @Setter
    private TreeNode[] selectedAuthorizations;

    @Inject
    private Permissions permissions;

    @Inject
    private GroupRepository groupRepository;

    @Inject
    private UserAccountService userAccountService;

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

        // capturamos o estado da tela 
        this.viewState = ViewState.valueOf(viewState);

        this.data = this.groupRepository.findAllActive();
        this.value = this.groupRepository.findOptionalById(id)
                .orElseGet(Group::new);

        // cria e seleciona as permissoes na tree
        this.createAuthorizationsTree();
        this.selectAuthorizations();
    }

    /**
     *
     */
    @Override
    protected void initializeNavigationManager() {
        this.navigation.addPage(LIST_PAGE, "listGroups.xhtml");
        this.navigation.addPage(ADD_PAGE, "formGroup.xhtml");
        this.navigation.addPage(UPDATE_PAGE, "formGroup.xhtml");
        this.navigation.addPage(DETAIL_PAGE, "detailGroup.xhtml");
        this.navigation.addPage(DELETE_PAGE, "detailGroup.xhtml");
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
    public Page<Group> load(int first, int pageSize, String sortField, SortOrder sortOrder) {
        return this.groupRepository.findAllBy(this.filter.getValue(),
                this.filter.getEntityStatusValue(), first, pageSize);
    }

    /**
     *
     */
    @Override
    public void doSave() {
        this.userAccountService.save(this.value, this.parseAuthorizations());
        this.value = new Group();
        this.unselectAuthorizations();
        this.addInfo(true, "saved");
    }

    /**
     *
     */
    @Override
    public void doUpdate() {
        this.userAccountService.update(this.value, this.parseAuthorizations());
        this.addInfo(true, "updated");
    }

    /**
     *
     * @return
     */
    @Override
    public String doDelete() {
        this.userAccountService.delete(this.value);
        this.addInfoAndKeep("deleted");
        return this.changeToListing();
    }

    /**
     * This method parse the authorizations selected on the tree by the user to
     * the objects of the domain model
     *
     * @return the list of authorizations
     */
    private List<Authorization> parseAuthorizations() {

        final List<Authorization> authorizations = new ArrayList<>();

        for (TreeNode root : this.selectedAuthorizations) {

            final String[] nodeName = String.valueOf(root.getData()).split(":");

            if (nodeName.length > 1) {

                final String functionality = nodeName[0];
                final String permission = nodeName[1];

                authorizations.add(new Authorization(functionality, permission));
            }
        }
        return authorizations;
    }

    /**
     * Create the authorizations tree
     */
    private void createAuthorizationsTree() {

        this.treeRoot = new DefaultTreeNode("permissions");

        final List<Authorization> authorizations
                = this.permissions.toAuthorizationList();

        authorizations.stream()
                .map(Authorization::getFunctionality)
                .distinct()
                .forEach(functionality -> {

                    final TreeNode functionalityNode
                            = new DefaultTreeNode(functionality, this.treeRoot);

                    authorizations.stream()
                            .filter(authz -> authz.isFunctionality(functionality))
                            .map(Authorization::getFullPermission)
                            .forEach(authz -> {
                                functionalityNode.getChildren().add(
                                        new DefaultTreeNode(authz, functionalityNode));
                            });

                    this.treeRoot.getChildren().add(functionalityNode);
                });
    }

    /**
     * Remove all the selections on the authorizations tree
     */
    private void unselectAuthorizations() {

        this.treeRoot.getChildren()
                .stream()
                .peek(root -> root.setSelected(false))
                .map(TreeNode::getChildren)
                .forEach(childs -> {
                    if (!childs.isEmpty()) {
                        childs.stream().forEach(child -> {
                            child.setSelected(false);
                        });
                    }
                });
    }

    /**
     * Select all the nods with a given set of values to be marked
     */
    private void selectAuthorizations() {

        final Set<TreeNode> selected = new HashSet<>();

        for (Grant grant : this.value.getGrants()) {

            for (TreeNode root : this.treeRoot.getChildren()) {

                final String functionality = String.valueOf(root.getData());

                if (grant.getAuthorization().isFunctionality(functionality)) {
                    root.setSelected(true);
                    selected.add(root);
                }

                if (!root.getChildren().isEmpty()) {
                    for (TreeNode childNode : root.getChildren()) {

                        final String permission = String.valueOf(childNode.getData());

                        if (grant.getAuthorization().isPermission(permission)) {
                            childNode.setSelected(true);
                            selected.add(childNode);
                            break;
                        }
                    }
                }
            }
        }
        this.selectedAuthorizations = selected.toArray(new TreeNode[]{});
    }

    /**
     * Helper method to prevent rewriting of the i18n code on each authorization
     *
     * @param nodeDescription the node description
     * @return the part of the nod to bem parsed in the i18n
     */
    public String split(String nodeDescription) {
        final String splited[] = nodeDescription.split(":");
        return splited.length > 1 ? splited[1] : splited[0];
    }
}

package ui.users.register;

import eapli.framework.infrastructure.authz.domain.model.Role;
import ecourseusermanagement.application.register.RegisterUserController;
import ui.components.AbstractUI;
import ui.menu.*;
import ui.users.register.manager.RegisterManagerAction;
import ui.users.register.student.RegisterStudentAction;
import ui.users.register.teacher.RegisterTeacherAction;


public class RegisterUserUI extends AbstractUI {

    private final RegisterUserController controller = new RegisterUserController();

    @Override
    protected boolean doShow() {
        final Menu menu = buildRolesSelectionMenu();
        final var renderer = new MenuRenderer(menu);
        return renderer.render(MenuRenderer.PullOutActions.BACK.message());
    }

    private Menu buildRolesSelectionMenu() {
        final Menu rolesSelectionMenu = new Menu();
        // Get the roles
        Role[] roles = controller.existingRoles();
        // Add the roles to the menu
        rolesSelectionMenu.addItem(1, "Register " + getPrintableRole(roles[0]), new RegisterStudentAction());
        rolesSelectionMenu.addItem(2, "Register " + getPrintableRole(roles[1]), new RegisterTeacherAction());
        rolesSelectionMenu.addItem(3, "Register " + getPrintableRole(roles[2]), new RegisterManagerAction());


        // add the back option

        return rolesSelectionMenu;
    }

    private String getPrintableRole(Role role) {
        return role.toString().substring(0, 1).toUpperCase() + role.toString().substring(1).toLowerCase();
    }

    @Override
    public String headline() {
        return "Register User";
    }
}

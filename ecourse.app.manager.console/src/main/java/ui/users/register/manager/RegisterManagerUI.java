package ui.users.register.manager;

import ecourseusermanagement.application.register.RegisterManagerController;
import ecourseusermanagement.domain.ecourseuserbuilders.ECourseUserBuilderFactory;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.Sleeper;

public class RegisterManagerUI extends AbstractUI {

    private final RegisterManagerController controller = new RegisterManagerController(PersistenceContext.repositories()
            .eCourseUsers(), new ECourseUserBuilderFactory());

    @Override
    protected boolean doShow() {
        System.out.println("Please provide the teacher details\n");
        try {
            final String username = Console.readNonEmptyLine("Username: ",
                    "Please provide a username");
            System.out.println("_________________________________________");
            final String password = Console.readPassword("Password: ", "Please provide a password");
            System.out.println("_________________________________________");
            final String name = Console.readNonEmptyLine("Name: ", "Please provide a name");
            System.out.println("_________________________________________");
            final String lastName = Console.readLine("Last Name: ");
            System.out.println("_________________________________________");
            final String email = Console.readLine("Email: ");


            controller.registerManager(username, password, name, lastName, email);
            System.out.println();
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null) {
                errorMessage("Error creating the user. Please make  that you properly respect the password policy: "
                        + "6 characters minimum, at least one Capital letter, one number.");
            } else {
                errorMessage(e.getMessage());
            }
            Sleeper.sleep(1700);
            return false;
        } catch (Exception e) {
            errorMessage("Error registering manager! Common cause: Email already exists!");
            Sleeper.sleep(1700);
            return false;
        }
        successMessage("Manager registered successfully");
        Sleeper.sleep(1700);
        return false;
    }

    @Override
    public String headline() {
        return "Register Manager";
    }
}

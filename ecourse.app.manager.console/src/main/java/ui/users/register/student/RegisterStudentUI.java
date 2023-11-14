package ui.users.register.student;

import ecourseusermanagement.application.register.RegisterStudentController;
import ecourseusermanagement.domain.UserBirthdate;
import ecourseusermanagement.domain.UserTaxNumber;
import ecourseusermanagement.domain.ecourseuserbuilders.ECourseUserBuilderFactory;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.Sleeper;

import java.text.Normalizer;

public class RegisterStudentUI extends AbstractUI {

    private final RegisterStudentController controller = new RegisterStudentController(PersistenceContext.repositories()
            .eCourseUsers(), new ECourseUserBuilderFactory());

    @Override
    protected boolean doShow() {
        System.out.println("Please provide the student details\n");
        try {
            final String username =
                    Console.readNonEmptyLine("Username: ", "Please provide a username");
            System.out.println("_________________________________________");
            final String password = Console.readPassword("Password: ", "Please provide a password");
            System.out.println("_________________________________________");
            String firstName = Console.readNonEmptyLine("First name: ", "Please provide a name");
            // if name contains any accent, remove it
            firstName = Normalizer.normalize(firstName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            System.out.println("_________________________________________");
            String lastName = Console.readNonEmptyLine("Last Name: ", "Please provide a last name");
            // if last name contains any accent, remove it
            lastName = Normalizer.normalize(lastName, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            System.out.println("_________________________________________");
            final String email = Console.readLine("Email: ");
            System.out.println("_________________________________________");
            final UserBirthdate birthdate =
                    new UserBirthdate(Console.readDate("Birthdate (DD-MM-YYYY): ", "dd-MM-yyyy"));
            System.out.println("_________________________________________");
            final UserTaxNumber taxNumber =
                    new UserTaxNumber(
                            Console.readNonEmptyLine("Tax Number: ", "Please provide a tax number"));


            controller.registerStudent(username, password, firstName, lastName, email, birthdate, taxNumber);
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
            errorMessage("Error registering student! Common cause: Email already exists!");
            Sleeper.sleep(1700);
            return false;
        }
        successMessage("Student registered successfully");
        Sleeper.sleep(1700);

        return false;
    }

    @Override
    public String headline() {
        return "Register Student";
    }
}

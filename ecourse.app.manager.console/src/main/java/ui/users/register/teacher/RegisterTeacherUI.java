package ui.users.register.teacher;

import ecourseusermanagement.application.register.RegisterTeacherController;
import ecourseusermanagement.domain.UserAcronym;
import ecourseusermanagement.domain.UserBirthdate;
import ecourseusermanagement.domain.UserTaxNumber;
import ecourseusermanagement.domain.ecourseuserbuilders.ECourseUserBuilderFactory;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.Sleeper;

import java.text.Normalizer;

public class RegisterTeacherUI extends AbstractUI {

    private final RegisterTeacherController controller = new RegisterTeacherController(PersistenceContext.repositories()
            .eCourseUsers(), new ECourseUserBuilderFactory());

    @Override
    protected boolean doShow() {
        System.out.println("Please provide the teacher details\n");
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
            System.out.println("_________________________________________");
            final UserAcronym acronym =
                    new UserAcronym(
                            Console.readNonEmptyLine("Acronym: ", "Please provide an acronym"));


            controller.registerTeacher(username, password, firstName, lastName, email, acronym, birthdate, taxNumber);
            System.out.println();
        } catch (IllegalArgumentException e) {
            if (e.getMessage() == null) {
                errorMessage("Error creating the user. Please make  that you properly respect the password policy: "
                        + "6 characters minimum, at least one Capital letter, one number.");
            }else {
                errorMessage(e.getMessage());
            }
            Sleeper.sleep(1700);
            return false;
        } catch (Exception e) {
            errorMessage("Error registering teacher! Common cause: Email already exists!");
            Sleeper.sleep(1700);
            return false;
        }
        successMessage("Teacher registered successfully");
        Sleeper.sleep(1700);

        return false;
    }

    @Override
    public String headline() {
        return "Register Teacher";
    }
}

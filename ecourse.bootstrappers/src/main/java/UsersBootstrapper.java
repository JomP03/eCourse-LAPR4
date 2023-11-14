import eapli.framework.actions.Action;
import ecourseusermanagement.application.register.RegisterManagerController;
import ecourseusermanagement.application.register.RegisterStudentController;
import ecourseusermanagement.application.register.RegisterTeacherController;
import ecourseusermanagement.domain.UserAcronym;
import ecourseusermanagement.domain.UserBirthdate;
import ecourseusermanagement.domain.UserTaxNumber;
import ecourseusermanagement.domain.ecourseuserbuilders.ECourseUserBuilderFactory;
import persistence.PersistenceContext;

import java.time.LocalDate;

public class UsersBootstrapper implements Action {

    private final RegisterStudentController studentController =
            new RegisterStudentController(PersistenceContext.repositories().eCourseUsers(),
                    new ECourseUserBuilderFactory());
    private final RegisterTeacherController teacherController =
            new RegisterTeacherController(PersistenceContext.repositories().eCourseUsers(),
                    new ECourseUserBuilderFactory());
    private final RegisterManagerController managerController =
            new RegisterManagerController(PersistenceContext.repositories().eCourseUsers(),
                    new ECourseUserBuilderFactory());


    @Override
    public boolean execute() {
        bootstrapStudents();
        bootstrapTeachers();
        bootstrapManagers();
        return true;
    }

    private void bootstrapStudents() {
        registerStudent("mariaferreira", "MariaFerreira1", "Maria", "Ferreira",
                "mariaferreira@gmail.com", new UserBirthdate(LocalDate.of(2005, 4, 14)),
                new UserTaxNumber("278125107"));
        registerStudent("pedroalves", "PedroAlves1", "Pedro", "Alves",
                "pedroalves@gmail.com", new UserBirthdate(LocalDate.of(2003, 10, 8)),
                new UserTaxNumber("201066793"));
        registerStudent("anapereira", "AnaPereira1", "Ana", "Pereira",
                "anapereira@gmail.com", new UserBirthdate(LocalDate.of(2006, 1, 30)),
                new UserTaxNumber("221654321"));
        registerStudent("luissantos", "LuisSantos1", "Luis", "Santos",
                "luissantos@gmail.com", new UserBirthdate(LocalDate.of(2002, 7, 23)),
                new UserTaxNumber("234106913"));
        registerStudent("andreoliveira", "AndreOliveira1", "Andre", "Oliveira",
                "andreoliveira@gmail.com", new UserBirthdate(LocalDate.of(2004, 12, 5)),
                new UserTaxNumber("229509622"));
        registerStudent("carlalopes", "CarlaLopes1", "Carla", "Lopes",
                "carlalopes@gmail.com", new UserBirthdate(LocalDate.of(2003, 2, 28)),
                new UserTaxNumber("251512282"));
        registerStudent("davidsilva", "DavidSilva1", "David", "Silva",
                "davidsilva@gmail.com", new UserBirthdate(LocalDate.of(2005, 8, 10)),
                new UserTaxNumber("263540227"));
        registerStudent("eduardomartins", "EduardoMartins1", "Eduardo", "Martins",
                "eduardomartins@gmail.com", new UserBirthdate(LocalDate.of(2006, 6, 14)),
                new UserTaxNumber("291374085"));
        registerStudent("filipemoreira", "FilipeMoreira1", "Filipe", "Moreira",
                "filipemoreira@gmail.com", new UserBirthdate(LocalDate.of(2003, 3, 16)),
                new UserTaxNumber("223272949"));
        registerStudent("gabrieloliveira", "GabrielOliveira1", "Gabriel", "Oliveira",
                "gabrieloliveira@gmail.com",
                new UserBirthdate(LocalDate.of(2005, 11, 23)),
                new UserTaxNumber("281601879"));
        registerStudent("helenapereira", "HelenaPereira1", "Helena", "Pereira",
                "helenapereira@gmail.com", new UserBirthdate(LocalDate.of(2004, 9, 8)),
                new UserTaxNumber("206625375"));
        registerStudent("isabelsilva", "IsabelSilva1", "Isabel", "Silva",
                "isabelsilva@gmail.com", new UserBirthdate(LocalDate.of(2003, 7, 30)),
                new UserTaxNumber("205463681"));
        registerStudent("joaoricardo", "JoaoRicardo1", "Joao", "Ricardo",
                "joaoricardo@gmail.com", new UserBirthdate(LocalDate.of(2006, 2, 15)),
                new UserTaxNumber("213351161"));
        registerStudent("karenlopes", "KarenLopes1", "Karen", "Lopes",
                "karenlopes@gmail.com", new UserBirthdate(LocalDate.of(2005, 4, 18)),
                new UserTaxNumber("210662271"));
    }

    private void bootstrapTeachers() {
        registerTeacher("johndoe", "JohnDoe1", "John", "Doe",
                "johndoe@gmail.com", new UserAcronym("JDE"),
                new UserBirthdate(LocalDate.of(1985, 6, 20)), new UserTaxNumber("282369821"));
        registerTeacher("janedoe", "JaneDoe1", "Jane", "Doe",
                "janedoe@gmail.com", new UserAcronym("JND"),
                new UserBirthdate(LocalDate.of(1990, 9, 10)), new UserTaxNumber("204359139"));
        registerTeacher("mariagarcia", "MariaGarcia1", "Maria", "Garcia",
                "mariagarcia@gmail.com", new UserAcronym("MRG"),
                new UserBirthdate(LocalDate.of(1982, 2, 5)), new UserTaxNumber("209323760"));
        registerTeacher("paulsmith", "PaulSmith1", "Paul", "Smith",
                "paulsmith@gmail.com", new UserAcronym("PSM"),
                new UserBirthdate(LocalDate.of(1978, 11, 12)), new UserTaxNumber("298979586"));
        registerTeacher("lucassilva", "LucasSilva1", "Lucas", "Silva",
                "lucassilva@gmail.com", new UserAcronym("LCS"),
                new UserBirthdate(LocalDate.of(1987, 7, 23)), new UserTaxNumber("238455882"));
        registerTeacher("mariafernandes", "MariaFernandes1", "Maria", "Fernandes",
                "mariafernandes@gmail.com", new UserAcronym("MFS"),
                new UserBirthdate(LocalDate.of(1984, 4, 14)), new UserTaxNumber("210656140"));
        registerTeacher("josealmeida", "JoseAlmeida1", "Jose", "Almeida",
                "josealmeida@gmail.com", new UserAcronym("JAL"),
                new UserBirthdate(LocalDate.of(1979, 10, 8)), new UserTaxNumber("218276877"));
        registerTeacher("anasantos", "AnaSantos1", "Ana", "Santos",
                "anasantos@gmail.com", new UserAcronym("AST"),
                new UserBirthdate(LocalDate.of(1995, 1, 30)), new UserTaxNumber("288108361"));
    }

    private void bootstrapManagers() {
        registerManager("adamsmith", "AdamSmith1", "Adam",
                "Smith", "adamsmith@gmail.com");
        registerManager("elizabethjohnson", "ElizabethJohnson1", "Elizabeth",
                "Johnson", "elizabethjohnson@gmail.com");
        registerManager("williamjackson", "WilliamJackson1", "William",
                "Jackson", "williamjackson@gmail.com");
        registerManager("katherinewalker", "KatherineWalker1", "Katherine",
                "Walker", "katherinewalker@gmail.com");
        registerManager("davidwilliams", "DavidWilliams1", "David",
                "Williams", "davidwilliams@gmail.com");
        registerManager("jessicaharris", "JessicaHarris1", "Jessica",
                "Harris", "jessicaharris@gmail.com");
        registerManager("peterdavis", "PeterDavis1", "Peter",
                "Davis", "peterdavis@gmail.com");
        registerManager("samanthasmith", "SamanthaSmith1", "Samantha",
                "Smith", "samanthasmith@gmail.com");
        registerManager("olivertaylor", "OliverTaylor1", "Oliver",
                "Taylor", "olivertaylor@gmail.com");
        registerManager("isabellamartin", "IsabellaMartin1", "Isabella",
                "Martin", "isabellamartin@gmail.com");
        registerManager("georgeclark", "GeorgeClark1", "George",
                "Clark", "georgeclark@gmail.com");
        registerManager("victorialee", "VictoriaLee1", "Victoria",
                "Lee", "victorialee@gmail.com");
    }

    private void registerStudent(String username, String pwd, String firstName, String lastName, String email,
                                 UserBirthdate birthdate, UserTaxNumber taxNumber) {
        studentController.registerStudent(username, pwd, firstName, lastName, email, birthdate, taxNumber);
    }

    private void registerTeacher(String username, String pwd, String firstName, String lastName, String email,
                                 UserAcronym userAcronym, UserBirthdate birthdate, UserTaxNumber taxNumber) {
        teacherController.registerTeacher(username, pwd, firstName, lastName, email, userAcronym, birthdate, taxNumber);
    }

    private void registerManager(String username, String pwd, String firstName, String lastName, String email) {
        managerController.registerManager(username, pwd, firstName, lastName, email);
    }
}

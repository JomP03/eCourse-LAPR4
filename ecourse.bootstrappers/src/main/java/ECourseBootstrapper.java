/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and
 * associated documentation files (the "Software"), to deal in the Software
 * without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish,
 * distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom
 * the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import boardmanagement.domain.Board;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.strings.util.Strings;
import eapli.framework.validations.Invariants;
import persistence.PersistenceContext;
import usermanagement.domain.ECourseRoles;
import usermanagement.domain.UserBuilderHelper;

public class ECourseBootstrapper implements Action {

    private static final String SUPERUSER_PWD = "superuserA1";
    private static final String SUPERUSER = "superuser";

    private final AuthorizationService authzSvc = AuthzRegistry.authorizationService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();
    private final UserRepository userRepository = PersistenceContext.repositories().users();

    @Override
    public boolean execute() {
        // Bootstrap actions:
        final Action [] bootstrapActions = {
                new UsersBootstrapper(),
                new CourseBootstrapper(),
                new CourseTeachersBootstrapper(),
                new RecurrentClassBootstrapper(),
                new EnrollmentRequestBootstrapper(),
                new EnrolledStudentBootstrapper(),
                new BoardBootstrapper(),
                new QuestionBootstrapper(),
                new MeetingBootstrapper(),
                new FormativeExamBootstrapper(),
                new AutomatedExamBootstrapper(),
                new TakenExamBootstrapper(),
                };

        registerSuperUser(userRepository);
        authenticateForBootstrapping();
        // execute all bootstrapping
        var ret = true;
        for (final Action boot : bootstrapActions) {
            System.out.println("Bootstrapping " + nameOfEntity(boot) + "...");
            ret &= boot.execute();
        }
        return ret;
    }

    /**
     * Register a superuser directly in the persistence layer as we need to
     * circumvent authorizations in the Application Layer.
     */
    public static boolean registerSuperUser(final UserRepository userRepository) {
        final SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        userBuilder.withUsername(SUPERUSER).withPassword(SUPERUSER_PWD).withName("super", "user")
                .withEmail("su@email.org").withRoles(ECourseRoles.SUPERUSER);
        final SystemUser newUser = userBuilder.build();

        try {
            final SystemUser superUser = userRepository.save(newUser);
            assert superUser != null;
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            return false;
        }
    }

    /**
     * authenticate a superuser to be able to register new users
     *
     */
    protected void authenticateForBootstrapping() {
        authenticationService.authenticate(SUPERUSER, SUPERUSER_PWD);
        Invariants.ensure(authzSvc.hasSession());
    }

    private String nameOfEntity(final Action boot) {
        final String name = boot.getClass().getSimpleName();
        return Strings.left(name, name.length() - "Bootstrapper".length());
    }

}

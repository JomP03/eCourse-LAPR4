/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package authz;


import eapli.framework.infrastructure.authz.domain.model.Role;
import auth.CredentialHandler;
import ecourseusermanagement.application.UserSessionService;
import ecourseusermanagement.repositories.IeCourseUserRepository;
import persistence.PersistenceContext;
import ui.components.AbstractUI;
import ui.components.Console;
import ui.components.ListSelector;

import java.util.List;

/**
 * UI for user login action.
 *
 * @author nuno 21/03/16.
 */
@SuppressWarnings("squid:S106")
public class LoginUI extends AbstractUI {

	private Role onlyWithThis;
	private static final int DEFAULT_MAX_ATTEMPTS = 3;
	private final int maxAttempts;

	private final CredentialHandler credentialHandler;
	private final IeCourseUserRepository userRepo = PersistenceContext.repositories().eCourseUsers();
	private final UserSessionService userSessionService = new UserSessionService(userRepo);

	public LoginUI(CredentialHandler credentialHandler, final Role onlyWithThis) {
		this.onlyWithThis = onlyWithThis;
		maxAttempts = DEFAULT_MAX_ATTEMPTS;
		this.credentialHandler = credentialHandler;
	}

	@Override
	protected boolean doShow() {
		var attempt = 1;
		while (attempt <= maxAttempts) {
			final String userName = Console.readNonEmptyLine("Username: ", "Please provide a username");
			System.out.println();
			final String password = Console.readPassword("Password: ", "Please provide a password");
			System.out.println();
			if (credentialHandler.authenticated(userName, password, onlyWithThis)) {
				try {
					userSessionService.getLoggedUser().get().email();
					return true;
				} catch (Exception e) {
					System.out.println("User is disabled. Please contact your system administrator.");
					System.out.println();
					ListSelector<String> wantAnotherUser = new ListSelector<>("Do you have another user?",
							List.of("Yes", "No"));
					wantAnotherUser.showAndSelect();
					if (wantAnotherUser.getSelectedOption() == 2) {
						return false;
					}
				}
			} else {
				System.out.printf("Wrong username or password. You have %d attempts left.%n%n»»»»»»»»»%n",
						maxAttempts - attempt);
				attempt++;
			}
		}
		System.out.println("Sorry, we are unable to authenticate you. Please contact your system administrator.");
		return false;
	}

	@Override
	public String headline() {
		return "Login";
	}
}

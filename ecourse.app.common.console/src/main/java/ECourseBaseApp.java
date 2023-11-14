/*
 * Copyright (c) 2013-2023 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import appsettings.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eapli.framework.infrastructure.pubsub.EventDispatcher;
import eapli.framework.infrastructure.pubsub.PubSubRegistry;
import eapli.framework.infrastructure.pubsub.impl.inprocess.service.InProcessPubSub;


@SuppressWarnings("squid:S106")
public abstract class ECourseBaseApp {

	protected static final String SEPARATOR_HR = "===============================================";

	/**
	 * @param args the command line arguments
	 */
	public void run(final String[] args) {
		configureFunctionalServices();
		printHeader();

		try {
			setupEventHandlers();

			doMain(args);

			printFooter();
		} catch (final Exception e) {
			System.out.println("An unexpected error occurred. Please contact the IT support.");
			System.out.println("Error details:");
			System.out.println(e.getMessage());
		} finally {
			clearEventHandlers();
		}

		// exiting the application, closing all threads
		System.exit(0);
	}

	protected void printFooter() {
		System.out.println();
		System.out.println(SEPARATOR_HR);
		System.out.println(appGoodbye());
		System.out.println(SEPARATOR_HR);
	}

	protected void printHeader() {
		System.out.println(SEPARATOR_HR);
		System.out.println(appTitle() + " " + Application.VERSION);
		System.out.println(Application.COPYRIGHT);
		System.out.println(SEPARATOR_HR);
	}

	private final void clearEventHandlers() {
		try {
			doClearEventHandlers(PubSubRegistry.dispatcher());

			PubSubRegistry.dispatcher().shutdown();
		} catch (final Exception e) {
		}
	}

	private final void setupEventHandlers() {
		doSetupEventHandlers(PubSubRegistry.dispatcher());
	}

	protected void configureFunctionalServices() {
		configureAuthz();
		configurePubSub();
	}

	protected void configurePubSub() {
		PubSubRegistry.configure(InProcessPubSub.dispatcher(), InProcessPubSub.publisher());
	}

	protected abstract void configureAuthz();

	protected abstract void doMain(final String[] args);

	protected abstract String appTitle();

	protected abstract String appGoodbye();

	protected void doClearEventHandlers(final EventDispatcher dispatcher) {
		// nothing to do
	}

	protected abstract void doSetupEventHandlers(EventDispatcher dispatcher);
}

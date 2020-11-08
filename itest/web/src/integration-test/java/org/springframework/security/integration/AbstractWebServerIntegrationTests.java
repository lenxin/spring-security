package org.springframework.security.integration;

import org.junit.After;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.support.XmlWebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

/**
 * Base class which allows the application to be started with a particular Spring
 * application context. Subclasses override the <tt>getContextConfigLocations</tt> method
 * to return a list of context file names which is passed to the
 * <tt>ContextLoaderListener</tt> when starting up the webapp.
 *
 * @author Luke Taylor
 */
public abstract class AbstractWebServerIntegrationTests {

	protected ConfigurableApplicationContext context;

	@After
	public void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	protected final MockMvc createMockMvc(String... configLocations) {
		if (this.context != null) {
			throw new IllegalStateException("context is already loaded");
		}

		XmlWebApplicationContext context = new XmlWebApplicationContext();
		context.setConfigLocations(configLocations);
		context.setServletContext(new MockServletContext());
		context.refresh();
		this.context = context;

		// @formatter:off
		return MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
		// @formatter:on
	}

}

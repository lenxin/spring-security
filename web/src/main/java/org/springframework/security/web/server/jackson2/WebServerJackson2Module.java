package org.springframework.security.web.server.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.server.csrf.DefaultCsrfToken;

/**
 * Jackson module for spring-security-web-flux. This module register
 * {@link DefaultCsrfServerTokenMixin} If no default typing enabled by default then it'll
 * enable it because typing info is needed to properly serialize/deserialize objects. In
 * order to use this module just add this module into your ObjectMapper configuration.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new WebServerJackson2Module());
 * </pre> <b>Note: use {@link SecurityJackson2Modules#getModules(ClassLoader)} to get list
 * of all security modules.</b>
 *
 * @author Boris Finkelshteyn
 * @since 5.1
 * @see SecurityJackson2Modules
 */
public class WebServerJackson2Module extends SimpleModule {

	private static final String NAME = WebServerJackson2Module.class.getName();

	private static final Version VERSION = new Version(1, 0, 0, null, null, null);

	public WebServerJackson2Module() {
		super(NAME, VERSION);
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(DefaultCsrfToken.class, DefaultCsrfServerTokenMixin.class);
	}

}

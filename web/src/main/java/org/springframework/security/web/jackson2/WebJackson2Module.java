package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;

/**
 * Jackson module for spring-security-web. This module register
 * {@link DefaultCsrfTokenMixin} and {@link PreAuthenticatedAuthenticationTokenMixin}. If
 * no default typing enabled by default then it'll enable it because typing info is needed
 * to properly serialize/deserialize objects. In order to use this module just add this
 * module into your ObjectMapper configuration.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new WebJackson2Module());
 * </pre> <b>Note: use {@link SecurityJackson2Modules#getModules(ClassLoader)} to get list
 * of all security modules.</b>
 *
 * @author Jitendra Singh
 * @since 4.2
 * @see SecurityJackson2Modules
 */
public class WebJackson2Module extends SimpleModule {

	public WebJackson2Module() {
		super(WebJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(DefaultCsrfToken.class, DefaultCsrfTokenMixin.class);
		context.setMixInAnnotations(PreAuthenticatedAuthenticationToken.class,
				PreAuthenticatedAuthenticationTokenMixin.class);
	}

}

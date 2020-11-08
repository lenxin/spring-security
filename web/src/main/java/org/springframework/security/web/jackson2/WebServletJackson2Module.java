package org.springframework.security.web.jackson2;

import javax.servlet.http.Cookie;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.SavedCookie;

/**
 * Jackson module for spring-security-web related to servlet. This module register
 * {@link CookieMixin}, {@link SavedCookieMixin}, {@link DefaultSavedRequestMixin} and
 * {@link WebAuthenticationDetailsMixin}. If no default typing enabled by default then
 * it'll enable it because typing info is needed to properly serialize/deserialize
 * objects. In order to use this module just add this module into your ObjectMapper
 * configuration.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new WebServletJackson2Module());
 * </pre> <b>Note: use {@link SecurityJackson2Modules#getModules(ClassLoader)} to get list
 * of all security modules.</b>
 *
 * @author Boris Finkelshteyn
 * @since 5.1
 * @see SecurityJackson2Modules
 */
public class WebServletJackson2Module extends SimpleModule {

	public WebServletJackson2Module() {
		super(WebJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(Cookie.class, CookieMixin.class);
		context.setMixInAnnotations(SavedCookie.class, SavedCookieMixin.class);
		context.setMixInAnnotations(DefaultSavedRequest.class, DefaultSavedRequestMixin.class);
		context.setMixInAnnotations(WebAuthenticationDetails.class, WebAuthenticationDetailsMixin.class);
	}

}

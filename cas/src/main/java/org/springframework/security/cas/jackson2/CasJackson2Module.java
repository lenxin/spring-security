package org.springframework.security.cas.jackson2;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.jasig.cas.client.authentication.AttributePrincipalImpl;
import org.jasig.cas.client.validation.AssertionImpl;

import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 * Jackson module for spring-security-cas. This module register
 * {@link AssertionImplMixin}, {@link AttributePrincipalImplMixin} and
 * {@link CasAuthenticationTokenMixin}. If no default typing enabled by default then it'll
 * enable it because typing info is needed to properly serialize/deserialize objects. In
 * order to use this module just add this module into your ObjectMapper configuration.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new CasJackson2Module());
 * </pre> <b>Note: use {@link SecurityJackson2Modules#getModules(ClassLoader)} to get list
 * of all security modules on the classpath.</b>
 *
 * @author Jitendra Singh.
 * @since 4.2
 * @see org.springframework.security.jackson2.SecurityJackson2Modules
 */
public class CasJackson2Module extends SimpleModule {

	public CasJackson2Module() {
		super(CasJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(AssertionImpl.class, AssertionImplMixin.class);
		context.setMixInAnnotations(AttributePrincipalImpl.class, AttributePrincipalImplMixin.class);
		context.setMixInAnnotations(CasAuthenticationToken.class, CasAuthenticationTokenMixin.class);
	}

}

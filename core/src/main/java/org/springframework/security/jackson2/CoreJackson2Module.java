package org.springframework.security.jackson2;

import java.util.Collections;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Jackson module for spring-security-core. This module register
 * {@link AnonymousAuthenticationTokenMixin}, {@link RememberMeAuthenticationTokenMixin},
 * {@link SimpleGrantedAuthorityMixin}, {@link UnmodifiableSetMixin}, {@link UserMixin}
 * and {@link UsernamePasswordAuthenticationTokenMixin}. If no default typing enabled by
 * default then it'll enable it because typing info is needed to properly
 * serialize/deserialize objects. In order to use this module just add this module into
 * your ObjectMapper configuration.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new CoreJackson2Module());
 * </pre> <b>Note: use {@link SecurityJackson2Modules#getModules(ClassLoader)} to get list
 * of all security modules.</b>
 *
 * @author Jitendra Singh.
 * @see SecurityJackson2Modules
 * @since 4.2
 */
@SuppressWarnings("serial")
public class CoreJackson2Module extends SimpleModule {

	public CoreJackson2Module() {
		super(CoreJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
	}

	@Override
	public void setupModule(SetupContext context) {
		SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
		context.setMixInAnnotations(AnonymousAuthenticationToken.class, AnonymousAuthenticationTokenMixin.class);
		context.setMixInAnnotations(RememberMeAuthenticationToken.class, RememberMeAuthenticationTokenMixin.class);
		context.setMixInAnnotations(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class);
		context.setMixInAnnotations(Collections.<Object>unmodifiableSet(Collections.emptySet()).getClass(),
				UnmodifiableSetMixin.class);
		context.setMixInAnnotations(Collections.<Object>unmodifiableList(Collections.emptyList()).getClass(),
				UnmodifiableListMixin.class);
		context.setMixInAnnotations(User.class, UserMixin.class);
		context.setMixInAnnotations(UsernamePasswordAuthenticationToken.class,
				UsernamePasswordAuthenticationTokenMixin.class);
		context.setMixInAnnotations(BadCredentialsException.class, BadCredentialsExceptionMixin.class);
	}

}

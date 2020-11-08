package org.springframework.security.authentication;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

/**
 * An {@link AuthenticationProvider} implementation that validates
 * {@link RememberMeAuthenticationToken}s.
 * <p>
 * To be successfully validated, the {@link RememberMeAuthenticationToken#getKeyHash()}
 * must match this class' {@link #getKey()}.
 */
public class RememberMeAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private String key;

	public RememberMeAuthenticationProvider(String key) {
		Assert.hasLength(key, "key must have a length");
		this.key = key;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.messages, "A message source must be set");
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (!supports(authentication.getClass())) {
			return null;
		}
		if (this.key.hashCode() != ((RememberMeAuthenticationToken) authentication).getKeyHash()) {
			throw new BadCredentialsException(this.messages.getMessage("RememberMeAuthenticationProvider.incorrectKey",
					"The presented RememberMeAuthenticationToken does not contain the expected key"));
		}
		return authentication;
	}

	public String getKey() {
		return this.key;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (RememberMeAuthenticationToken.class.isAssignableFrom(authentication));
	}

}

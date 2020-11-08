package org.springframework.security.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.util.Assert;

/**
 * @author Luke Taylor
 */
public class AccountStatusUserDetailsChecker implements UserDetailsChecker, MessageSourceAware {

	private final Log logger = LogFactory.getLog(getClass());

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	@Override
	public void check(UserDetails user) {
		if (!user.isAccountNonLocked()) {
			this.logger.debug("Failed to authenticate since user account is locked");
			throw new LockedException(
					this.messages.getMessage("AccountStatusUserDetailsChecker.locked", "User account is locked"));
		}
		if (!user.isEnabled()) {
			this.logger.debug("Failed to authenticate since user account is disabled");
			throw new DisabledException(
					this.messages.getMessage("AccountStatusUserDetailsChecker.disabled", "User is disabled"));
		}
		if (!user.isAccountNonExpired()) {
			this.logger.debug("Failed to authenticate since user account is expired");
			throw new AccountExpiredException(
					this.messages.getMessage("AccountStatusUserDetailsChecker.expired", "User account has expired"));
		}
		if (!user.isCredentialsNonExpired()) {
			this.logger.debug("Failed to authenticate since user account credentials have expired");
			throw new CredentialsExpiredException(this.messages
					.getMessage("AccountStatusUserDetailsChecker.credentialsExpired", "User credentials have expired"));
		}
	}

	/**
	 * @since 5.2
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		Assert.notNull(messageSource, "messageSource cannot be null");
		this.messages = new MessageSourceAccessor(messageSource);
	}

}

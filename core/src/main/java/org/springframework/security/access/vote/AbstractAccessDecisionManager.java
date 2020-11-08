package org.springframework.security.access.vote;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.util.Assert;

/**
 * Abstract implementation of {@link AccessDecisionManager}.
 *
 * <p>
 * Handles configuration of a bean context defined list of {@link AccessDecisionVoter}s
 * and the access control behaviour if all voters abstain from voting (defaults to deny
 * access).
 */
public abstract class AbstractAccessDecisionManager
		implements AccessDecisionManager, InitializingBean, MessageSourceAware {

	protected final Log logger = LogFactory.getLog(getClass());

	private List<AccessDecisionVoter<?>> decisionVoters;

	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private boolean allowIfAllAbstainDecisions = false;

	protected AbstractAccessDecisionManager(List<AccessDecisionVoter<?>> decisionVoters) {
		Assert.notEmpty(decisionVoters, "A list of AccessDecisionVoters is required");
		this.decisionVoters = decisionVoters;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notEmpty(this.decisionVoters, "A list of AccessDecisionVoters is required");
		Assert.notNull(this.messages, "A message source must be set");
	}

	protected final void checkAllowIfAllAbstainDecisions() {
		if (!this.isAllowIfAllAbstainDecisions()) {
			throw new AccessDeniedException(
					this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}
	}

	public List<AccessDecisionVoter<?>> getDecisionVoters() {
		return this.decisionVoters;
	}

	public boolean isAllowIfAllAbstainDecisions() {
		return this.allowIfAllAbstainDecisions;
	}

	public void setAllowIfAllAbstainDecisions(boolean allowIfAllAbstainDecisions) {
		this.allowIfAllAbstainDecisions = allowIfAllAbstainDecisions;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		for (AccessDecisionVoter<?> voter : this.decisionVoters) {
			if (voter.supports(attribute)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Iterates through all <code>AccessDecisionVoter</code>s and ensures each can support
	 * the presented class.
	 * <p>
	 * If one or more voters cannot support the presented class, <code>false</code> is
	 * returned.
	 * @param clazz the type of secured object being presented
	 * @return true if this type is supported
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		for (AccessDecisionVoter<?> voter : this.decisionVoters) {
			if (!voter.supports(clazz)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [DecisionVoters=" + this.decisionVoters
				+ ", AllowIfAllAbstainDecisions=" + this.allowIfAllAbstainDecisions + "]";
	}

}

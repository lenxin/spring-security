package org.springframework.security.access.vote;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

/**
 * Simple concrete implementation of
 * {@link org.springframework.security.access.AccessDecisionManager} that uses a
 * consensus-based approach.
 * <p>
 * "Consensus" here means majority-rule (ignoring abstains) rather than unanimous
 * agreement (ignoring abstains). If you require unanimity, please see
 * {@link UnanimousBased}.
 */
public class ConsensusBased extends AbstractAccessDecisionManager {

	private boolean allowIfEqualGrantedDeniedDecisions = true;

	public ConsensusBased(List<AccessDecisionVoter<?>> decisionVoters) {
		super(decisionVoters);
	}

	/**
	 * This concrete implementation simply polls all configured
	 * {@link AccessDecisionVoter}s and upon completion determines the consensus of
	 * granted against denied responses.
	 * <p>
	 * If there were an equal number of grant and deny votes, the decision will be based
	 * on the {@link #isAllowIfEqualGrantedDeniedDecisions()} property (defaults to true).
	 * <p>
	 * If every <code>AccessDecisionVoter</code> abstained from voting, the decision will
	 * be based on the {@link #isAllowIfAllAbstainDecisions()} property (defaults to
	 * false).
	 * @param authentication the caller invoking the method
	 * @param object the secured object
	 * @param configAttributes the configuration attributes associated with the method
	 * being invoked
	 * @throws AccessDeniedException if access is denied
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException {
		int grant = 0;
		int deny = 0;
		for (AccessDecisionVoter voter : getDecisionVoters()) {
			int result = voter.vote(authentication, object, configAttributes);
			switch (result) {
			case AccessDecisionVoter.ACCESS_GRANTED:
				grant++;
				break;
			case AccessDecisionVoter.ACCESS_DENIED:
				deny++;
				break;
			default:
				break;
			}
		}
		if (grant > deny) {
			return;
		}
		if (deny > grant) {
			throw new AccessDeniedException(
					this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}
		if ((grant == deny) && (grant != 0)) {
			if (this.allowIfEqualGrantedDeniedDecisions) {
				return;
			}
			throw new AccessDeniedException(
					this.messages.getMessage("AbstractAccessDecisionManager.accessDenied", "Access is denied"));
		}
		// To get this far, every AccessDecisionVoter abstained
		checkAllowIfAllAbstainDecisions();
	}

	public boolean isAllowIfEqualGrantedDeniedDecisions() {
		return this.allowIfEqualGrantedDeniedDecisions;
	}

	public void setAllowIfEqualGrantedDeniedDecisions(boolean allowIfEqualGrantedDeniedDecisions) {
		this.allowIfEqualGrantedDeniedDecisions = allowIfEqualGrantedDeniedDecisions;
	}

}

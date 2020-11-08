package org.springframework.security.access.vote;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

/**
 * Implementation of an {@link AccessDecisionVoter} for unit testing.
 * <p>
 * If the {@link ConfigAttribute#getAttribute()} has a value of <code>DENY_FOR_SURE</code>
 * , the voter will vote to deny access.
 * </p>
 * <p>
 * All comparisons are case sensitive.
 * </p>
 *
 * @author Ben Alex
 */
public class DenyVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return "DENY_FOR_SURE".equals(attribute.getAttribute());
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		Iterator<ConfigAttribute> iter = attributes.iterator();
		while (iter.hasNext()) {
			ConfigAttribute attribute = iter.next();
			if (this.supports(attribute)) {
				return ACCESS_DENIED;
			}
		}
		return ACCESS_ABSTAIN;
	}

}

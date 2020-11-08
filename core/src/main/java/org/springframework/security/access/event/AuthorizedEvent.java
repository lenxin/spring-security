package org.springframework.security.access.event;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * Event indicating a secure object was invoked successfully.
 * <P>
 * Published just before the secure object attempts to proceed.
 * </p>
 *
 * @author Ben Alex
 */
public class AuthorizedEvent extends AbstractAuthorizationEvent {

	private final Authentication authentication;

	private final Collection<ConfigAttribute> configAttributes;

	/**
	 * Construct the event.
	 * @param secureObject the secure object
	 * @param attributes that apply to the secure object
	 * @param authentication that successfully called the secure object
	 *
	 */
	public AuthorizedEvent(Object secureObject, Collection<ConfigAttribute> attributes, Authentication authentication) {
		super(secureObject);
		Assert.isTrue(attributes != null && authentication != null, "All parameters are required and cannot be null");
		this.configAttributes = attributes;
		this.authentication = authentication;
	}

	public Authentication getAuthentication() {
		return this.authentication;
	}

	public Collection<ConfigAttribute> getConfigAttributes() {
		return this.configAttributes;
	}

}

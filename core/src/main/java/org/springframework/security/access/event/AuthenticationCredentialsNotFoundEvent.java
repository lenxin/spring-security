package org.springframework.security.access.event;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.util.Assert;

/**
 * Indicates a secure object invocation failed because the <code>Authentication</code>
 * could not be obtained from the <code>SecurityContextHolder</code>.
 *
 * @author Ben Alex
 */
public class AuthenticationCredentialsNotFoundEvent extends AbstractAuthorizationEvent {

	private final AuthenticationCredentialsNotFoundException credentialsNotFoundException;

	private final Collection<ConfigAttribute> configAttribs;

	/**
	 * Construct the event.
	 * @param secureObject the secure object
	 * @param attributes that apply to the secure object
	 * @param credentialsNotFoundException exception returned to the caller (contains
	 * reason)
	 *
	 */
	public AuthenticationCredentialsNotFoundEvent(Object secureObject, Collection<ConfigAttribute> attributes,
			AuthenticationCredentialsNotFoundException credentialsNotFoundException) {
		super(secureObject);
		Assert.isTrue(attributes != null && credentialsNotFoundException != null,
				"All parameters are required and cannot be null");
		this.configAttribs = attributes;
		this.credentialsNotFoundException = credentialsNotFoundException;
	}

	public Collection<ConfigAttribute> getConfigAttributes() {
		return this.configAttribs;
	}

	public AuthenticationCredentialsNotFoundException getCredentialsNotFoundException() {
		return this.credentialsNotFoundException;
	}

}

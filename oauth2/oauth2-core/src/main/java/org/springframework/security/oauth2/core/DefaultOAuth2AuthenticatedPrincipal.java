package org.springframework.security.oauth2.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.Assert;

/**
 * A domain object that wraps the attributes of an OAuth 2.0 token.
 *
 * @author Clement Ng
 * @author Josh Cummings
 * @since 5.2
 */
public final class DefaultOAuth2AuthenticatedPrincipal implements OAuth2AuthenticatedPrincipal, Serializable {

	private final Map<String, Object> attributes;

	private final Collection<GrantedAuthority> authorities;

	private final String name;

	/**
	 * Constructs an {@code DefaultOAuth2AuthenticatedPrincipal} using the provided
	 * parameters.
	 * @param attributes the attributes of the OAuth 2.0 token
	 * @param authorities the authorities of the OAuth 2.0 token
	 */
	public DefaultOAuth2AuthenticatedPrincipal(Map<String, Object> attributes,
			Collection<GrantedAuthority> authorities) {
		this(null, attributes, authorities);
	}

	/**
	 * Constructs an {@code DefaultOAuth2AuthenticatedPrincipal} using the provided
	 * parameters.
	 * @param name the name attached to the OAuth 2.0 token
	 * @param attributes the attributes of the OAuth 2.0 token
	 * @param authorities the authorities of the OAuth 2.0 token
	 */
	public DefaultOAuth2AuthenticatedPrincipal(String name, Map<String, Object> attributes,
			Collection<GrantedAuthority> authorities) {
		Assert.notEmpty(attributes, "attributes cannot be empty");
		this.attributes = Collections.unmodifiableMap(attributes);
		this.authorities = (authorities != null) ? Collections.unmodifiableCollection(authorities)
				: AuthorityUtils.NO_AUTHORITIES;
		this.name = (name != null) ? name : (String) this.attributes.get("sub");
	}

	/**
	 * Gets the attributes of the OAuth 2.0 token in map form.
	 * @return a {@link Map} of the attribute's objects keyed by the attribute's names
	 */
	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getName() {
		return this.name;
	}

}

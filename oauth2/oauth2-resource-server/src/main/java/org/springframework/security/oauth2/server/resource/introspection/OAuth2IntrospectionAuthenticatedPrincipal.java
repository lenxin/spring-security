package org.springframework.security.oauth2.server.resource.introspection;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

/**
 * A domain object that wraps the attributes of OAuth 2.0 Token Introspection.
 *
 * @author David Kovac
 * @since 5.4
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc7662#section-2.2">Introspection Response</a>
 */
public final class OAuth2IntrospectionAuthenticatedPrincipal
		implements OAuth2IntrospectionClaimAccessor, OAuth2AuthenticatedPrincipal, Serializable {

	private final OAuth2AuthenticatedPrincipal delegate;

	/**
	 * Constructs an {@code OAuth2IntrospectionAuthenticatedPrincipal} using the provided
	 * parameters.
	 * @param attributes the attributes of the OAuth 2.0 Token Introspection
	 * @param authorities the authorities of the OAuth 2.0 Token Introspection
	 */
	public OAuth2IntrospectionAuthenticatedPrincipal(Map<String, Object> attributes,
			Collection<GrantedAuthority> authorities) {
		this.delegate = new DefaultOAuth2AuthenticatedPrincipal(attributes, authorities);
	}

	/**
	 * Constructs an {@code OAuth2IntrospectionAuthenticatedPrincipal} using the provided
	 * parameters.
	 * @param name the name attached to the OAuth 2.0 Token Introspection
	 * @param attributes the attributes of the OAuth 2.0 Token Introspection
	 * @param authorities the authorities of the OAuth 2.0 Token Introspection
	 */
	public OAuth2IntrospectionAuthenticatedPrincipal(String name, Map<String, Object> attributes,
			Collection<GrantedAuthority> authorities) {
		this.delegate = new DefaultOAuth2AuthenticatedPrincipal(name, attributes, authorities);
	}

	/**
	 * Gets the attributes of the OAuth 2.0 Token Introspection in map form.
	 * @return a {@link Map} of the attribute's objects keyed by the attribute's names
	 */
	@Override
	public Map<String, Object> getAttributes() {
		return this.delegate.getAttributes();
	}

	/**
	 * Get the {@link Collection} of {@link GrantedAuthority}s associated with this OAuth
	 * 2.0 Token Introspection
	 * @return the OAuth 2.0 Token Introspection authorities
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.delegate.getAuthorities();
	}

	@Override
	public String getName() {
		return this.delegate.getName();
	}

	@Override
	public Map<String, Object> getClaims() {
		return getAttributes();
	}

}

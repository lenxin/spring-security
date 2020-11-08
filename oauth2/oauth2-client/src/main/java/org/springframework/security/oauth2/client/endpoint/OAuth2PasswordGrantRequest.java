package org.springframework.security.oauth2.client.endpoint;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

/**
 * An OAuth 2.0 Resource Owner Password Credentials Grant request that holds the resource
 * owner's credentials.
 *
 * @author Joe Grandja
 * @since 5.2
 * @see AbstractOAuth2AuthorizationGrantRequest
 * @see <a target="_blank" href=
 * "https://tools.ietf.org/html/rfc6749#section-1.3.3">Section 1.3.3 Resource Owner
 * Password Credentials</a>
 */
public class OAuth2PasswordGrantRequest extends AbstractOAuth2AuthorizationGrantRequest {

	private final ClientRegistration clientRegistration;

	private final String username;

	private final String password;

	/**
	 * Constructs an {@code OAuth2PasswordGrantRequest} using the provided parameters.
	 * @param clientRegistration the client registration
	 * @param username the resource owner's username
	 * @param password the resource owner's password
	 */
	public OAuth2PasswordGrantRequest(ClientRegistration clientRegistration, String username, String password) {
		super(AuthorizationGrantType.PASSWORD);
		Assert.notNull(clientRegistration, "clientRegistration cannot be null");
		Assert.isTrue(AuthorizationGrantType.PASSWORD.equals(clientRegistration.getAuthorizationGrantType()),
				"clientRegistration.authorizationGrantType must be AuthorizationGrantType.PASSWORD");
		Assert.hasText(username, "username cannot be empty");
		Assert.hasText(password, "password cannot be empty");
		this.clientRegistration = clientRegistration;
		this.username = username;
		this.password = password;
	}

	/**
	 * Returns the {@link ClientRegistration client registration}.
	 * @return the {@link ClientRegistration}
	 */
	public ClientRegistration getClientRegistration() {
		return this.clientRegistration;
	}

	/**
	 * Returns the resource owner's username.
	 * @return the resource owner's username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Returns the resource owner's password.
	 * @return the resource owner's password
	 */
	public String getPassword() {
		return this.password;
	}

}

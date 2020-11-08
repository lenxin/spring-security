package org.springframework.security.oauth2.client;

import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * This exception is thrown when an OAuth 2.0 Client is required to obtain authorization
 * from the Resource Owner.
 *
 * @author Joe Grandja
 * @since 5.1
 * @see OAuth2AuthorizedClient
 */
public class ClientAuthorizationRequiredException extends ClientAuthorizationException {

	private static final String CLIENT_AUTHORIZATION_REQUIRED_ERROR_CODE = "client_authorization_required";

	/**
	 * Constructs a {@code ClientAuthorizationRequiredException} using the provided
	 * parameters.
	 * @param clientRegistrationId the identifier for the client's registration
	 */
	public ClientAuthorizationRequiredException(String clientRegistrationId) {
		super(new OAuth2Error(CLIENT_AUTHORIZATION_REQUIRED_ERROR_CODE,
				"Authorization required for Client Registration Id: " + clientRegistrationId, null),
				clientRegistrationId);
	}

}

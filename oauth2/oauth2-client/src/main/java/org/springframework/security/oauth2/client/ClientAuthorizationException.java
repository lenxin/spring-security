package org.springframework.security.oauth2.client;

import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.util.Assert;

/**
 * This exception is thrown on the client side when an attempt to authenticate or
 * authorize an OAuth 2.0 client fails.
 *
 * @author Phil Clay
 * @since 5.3
 * @see OAuth2AuthorizedClient
 */
public class ClientAuthorizationException extends OAuth2AuthorizationException {

	private final String clientRegistrationId;

	/**
	 * Constructs a {@code ClientAuthorizationException} using the provided parameters.
	 * @param error the {@link OAuth2Error OAuth 2.0 Error}
	 * @param clientRegistrationId the identifier for the client's registration
	 */
	public ClientAuthorizationException(OAuth2Error error, String clientRegistrationId) {
		this(error, clientRegistrationId, error.toString());
	}

	/**
	 * Constructs a {@code ClientAuthorizationException} using the provided parameters.
	 * @param error the {@link OAuth2Error OAuth 2.0 Error}
	 * @param clientRegistrationId the identifier for the client's registration
	 * @param message the exception message
	 */
	public ClientAuthorizationException(OAuth2Error error, String clientRegistrationId, String message) {
		super(error, message);
		Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
		this.clientRegistrationId = clientRegistrationId;
	}

	/**
	 * Constructs a {@code ClientAuthorizationException} using the provided parameters.
	 * @param error the {@link OAuth2Error OAuth 2.0 Error}
	 * @param clientRegistrationId the identifier for the client's registration
	 * @param cause the root cause
	 */
	public ClientAuthorizationException(OAuth2Error error, String clientRegistrationId, Throwable cause) {
		this(error, clientRegistrationId, error.toString(), cause);
	}

	/**
	 * Constructs a {@code ClientAuthorizationException} using the provided parameters.
	 * @param error the {@link OAuth2Error OAuth 2.0 Error}
	 * @param clientRegistrationId the identifier for the client's registration
	 * @param message the exception message
	 * @param cause the root cause
	 */
	public ClientAuthorizationException(OAuth2Error error, String clientRegistrationId, String message,
			Throwable cause) {
		super(error, message, cause);
		Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
		this.clientRegistrationId = clientRegistrationId;
	}

	/**
	 * Returns the identifier for the client's registration.
	 * @return the identifier for the client's registration
	 */
	public String getClientRegistrationId() {
		return this.clientRegistrationId;
	}

}

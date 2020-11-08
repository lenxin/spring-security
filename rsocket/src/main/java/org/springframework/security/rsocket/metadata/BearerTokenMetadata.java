package org.springframework.security.rsocket.metadata;

import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

/**
 * Represents a bearer token that has been encoded into a {@link Payload#metadata()}.
 *
 * @author Rob Winch
 * @since 5.2
 */
public class BearerTokenMetadata {

	/**
	 * Represents a bearer token which is encoded as a String.
	 *
	 * See <a href="https://github.com/rsocket/rsocket/issues/272">rsocket/rsocket#272</a>
	 * @deprecated Basic did not evolve into the standard. Instead use Simple
	 * Authentication
	 * MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString())
	 */
	@Deprecated
	public static final MimeType BEARER_AUTHENTICATION_MIME_TYPE = new MediaType("message",
			"x.rsocket.authentication.bearer.v0");

	private final String token;

	public BearerTokenMetadata(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

}

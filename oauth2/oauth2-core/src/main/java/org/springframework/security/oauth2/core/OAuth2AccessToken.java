package org.springframework.security.oauth2.core;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * An implementation of an {@link AbstractOAuth2Token} representing an OAuth 2.0 Access
 * Token.
 *
 * <p>
 * An access token is a credential that represents an authorization granted by the
 * resource owner to the client. It is primarily used by the client to access protected
 * resources on either a resource server or the authorization server that originally
 * issued the access token.
 *
 * @author Joe Grandja
 * @since 5.0
 * @see <a target="_blank" href="https://tools.ietf.org/html/rfc6749#section-1.4">Section
 * 1.4 Access Token</a>
 */
public class OAuth2AccessToken extends AbstractOAuth2Token {

	private final TokenType tokenType;

	private final Set<String> scopes;

	/**
	 * Constructs an {@code OAuth2AccessToken} using the provided parameters.
	 * @param tokenType the token type
	 * @param tokenValue the token value
	 * @param issuedAt the time at which the token was issued
	 * @param expiresAt the expiration time on or after which the token MUST NOT be
	 * accepted
	 */
	public OAuth2AccessToken(TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt) {
		this(tokenType, tokenValue, issuedAt, expiresAt, Collections.emptySet());
	}

	/**
	 * Constructs an {@code OAuth2AccessToken} using the provided parameters.
	 * @param tokenType the token type
	 * @param tokenValue the token value
	 * @param issuedAt the time at which the token was issued
	 * @param expiresAt the expiration time on or after which the token MUST NOT be
	 * accepted
	 * @param scopes the scope(s) associated to the token
	 */
	public OAuth2AccessToken(TokenType tokenType, String tokenValue, Instant issuedAt, Instant expiresAt,
			Set<String> scopes) {
		super(tokenValue, issuedAt, expiresAt);
		Assert.notNull(tokenType, "tokenType cannot be null");
		this.tokenType = tokenType;
		this.scopes = Collections.unmodifiableSet((scopes != null) ? scopes : Collections.emptySet());
	}

	/**
	 * Returns the {@link TokenType token type}.
	 * @return the {@link TokenType}
	 */
	public TokenType getTokenType() {
		return this.tokenType;
	}

	/**
	 * Returns the scope(s) associated to the token.
	 * @return the scope(s) associated to the token
	 */
	public Set<String> getScopes() {
		return this.scopes;
	}

	/**
	 * Access Token Types.
	 *
	 * @see <a target="_blank" href=
	 * "https://tools.ietf.org/html/rfc6749#section-7.1">Section 7.1 Access Token
	 * Types</a>
	 */
	public static final class TokenType implements Serializable {

		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

		public static final TokenType BEARER = new TokenType("Bearer");

		private final String value;

		private TokenType(String value) {
			Assert.hasText(value, "value cannot be empty");
			this.value = value;
		}

		/**
		 * Returns the value of the token type.
		 * @return the value of the token type
		 */
		public String getValue() {
			return this.value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || this.getClass() != obj.getClass()) {
				return false;
			}
			TokenType that = (TokenType) obj;
			return this.getValue().equalsIgnoreCase(that.getValue());
		}

		@Override
		public int hashCode() {
			return this.getValue().hashCode();
		}

	}

}

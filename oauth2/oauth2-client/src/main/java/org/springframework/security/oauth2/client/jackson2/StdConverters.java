package org.springframework.security.oauth2.client.jackson2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.StdConverter;

import org.springframework.security.oauth2.core.AuthenticationMethod;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * {@code StdConverter} implementations.
 *
 * @author Joe Grandja
 * @since 5.3
 */
abstract class StdConverters {

	static final class AccessTokenTypeConverter extends StdConverter<JsonNode, OAuth2AccessToken.TokenType> {

		@Override
		public OAuth2AccessToken.TokenType convert(JsonNode jsonNode) {
			String value = JsonNodeUtils.findStringValue(jsonNode, "value");
			if (OAuth2AccessToken.TokenType.BEARER.getValue().equalsIgnoreCase(value)) {
				return OAuth2AccessToken.TokenType.BEARER;
			}
			return null;
		}

	}

	static final class ClientAuthenticationMethodConverter extends StdConverter<JsonNode, ClientAuthenticationMethod> {

		@Override
		public ClientAuthenticationMethod convert(JsonNode jsonNode) {
			String value = JsonNodeUtils.findStringValue(jsonNode, "value");
			if (ClientAuthenticationMethod.BASIC.getValue().equalsIgnoreCase(value)) {
				return ClientAuthenticationMethod.BASIC;
			}
			if (ClientAuthenticationMethod.POST.getValue().equalsIgnoreCase(value)) {
				return ClientAuthenticationMethod.POST;
			}
			if (ClientAuthenticationMethod.NONE.getValue().equalsIgnoreCase(value)) {
				return ClientAuthenticationMethod.NONE;
			}
			return null;
		}

	}

	static final class AuthorizationGrantTypeConverter extends StdConverter<JsonNode, AuthorizationGrantType> {

		@Override
		public AuthorizationGrantType convert(JsonNode jsonNode) {
			String value = JsonNodeUtils.findStringValue(jsonNode, "value");
			if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equalsIgnoreCase(value)) {
				return AuthorizationGrantType.AUTHORIZATION_CODE;
			}
			if (AuthorizationGrantType.IMPLICIT.getValue().equalsIgnoreCase(value)) {
				return AuthorizationGrantType.IMPLICIT;
			}
			if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equalsIgnoreCase(value)) {
				return AuthorizationGrantType.CLIENT_CREDENTIALS;
			}
			if (AuthorizationGrantType.PASSWORD.getValue().equalsIgnoreCase(value)) {
				return AuthorizationGrantType.PASSWORD;
			}
			return null;
		}

	}

	static final class AuthenticationMethodConverter extends StdConverter<JsonNode, AuthenticationMethod> {

		@Override
		public AuthenticationMethod convert(JsonNode jsonNode) {
			String value = JsonNodeUtils.findStringValue(jsonNode, "value");
			if (AuthenticationMethod.HEADER.getValue().equalsIgnoreCase(value)) {
				return AuthenticationMethod.HEADER;
			}
			if (AuthenticationMethod.FORM.getValue().equalsIgnoreCase(value)) {
				return AuthenticationMethod.FORM;
			}
			if (AuthenticationMethod.QUERY.getValue().equalsIgnoreCase(value)) {
				return AuthenticationMethod.QUERY;
			}
			return null;
		}

	}

}

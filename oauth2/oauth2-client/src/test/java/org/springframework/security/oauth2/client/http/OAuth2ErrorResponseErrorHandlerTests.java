package org.springframework.security.oauth2.client.http;

import org.junit.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link OAuth2ErrorResponseErrorHandler}.
 *
 * @author Joe Grandja
 */
public class OAuth2ErrorResponseErrorHandlerTests {

	private OAuth2ErrorResponseErrorHandler errorHandler = new OAuth2ErrorResponseErrorHandler();

	@Test
	public void handleErrorWhenErrorResponseBodyThenHandled() {
		// @formatter:off
		String errorResponse = "{\n"
				+ "   \"error\": \"unauthorized_client\",\n"
				+ "   \"error_description\": \"The client is not authorized\"\n"
				+ "}\n";
		// @formatter:on
		MockClientHttpResponse response = new MockClientHttpResponse(errorResponse.getBytes(), HttpStatus.BAD_REQUEST);
		assertThatExceptionOfType(OAuth2AuthorizationException.class)
				.isThrownBy(() -> this.errorHandler.handleError(response))
				.withMessage("[unauthorized_client] The client is not authorized");
	}

	@Test
	public void handleErrorWhenErrorResponseWwwAuthenticateHeaderThenHandled() {
		String wwwAuthenticateHeader = "Bearer realm=\"auth-realm\" error=\"insufficient_scope\" error_description=\"The access token expired\"";
		MockClientHttpResponse response = new MockClientHttpResponse(new byte[0], HttpStatus.BAD_REQUEST);
		response.getHeaders().add(HttpHeaders.WWW_AUTHENTICATE, wwwAuthenticateHeader);
		assertThatExceptionOfType(OAuth2AuthorizationException.class)
				.isThrownBy(() -> this.errorHandler.handleError(response))
				.withMessage("[insufficient_scope] The access token expired");
	}

}

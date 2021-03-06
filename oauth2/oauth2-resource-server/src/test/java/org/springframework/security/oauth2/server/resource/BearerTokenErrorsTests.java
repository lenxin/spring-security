package org.springframework.security.oauth2.server.resource;

import org.junit.Test;

import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class BearerTokenErrorsTests {

	@Test
	public void invalidRequestWhenMessageGivenThenBearerTokenErrorReturned() {
		String message = "message";
		BearerTokenError error = BearerTokenErrors.invalidRequest(message);
		assertThat(error.getErrorCode()).isSameAs(BearerTokenErrorCodes.INVALID_REQUEST);
		assertThat(error.getDescription()).isSameAs(message);
		assertThat(error.getHttpStatus()).isSameAs(HttpStatus.BAD_REQUEST);
		assertThat(error.getUri()).isEqualTo("https://tools.ietf.org/html/rfc6750#section-3.1");
	}

	@Test
	public void invalidRequestWhenInvalidMessageGivenThenDefaultBearerTokenErrorReturned() {
		String message = "has \"invalid\" chars";
		BearerTokenError error = BearerTokenErrors.invalidRequest(message);
		assertThat(error.getErrorCode()).isSameAs(BearerTokenErrorCodes.INVALID_REQUEST);
		assertThat(error.getDescription()).isEqualTo("Invalid request");
		assertThat(error.getHttpStatus()).isSameAs(HttpStatus.BAD_REQUEST);
		assertThat(error.getUri()).isEqualTo("https://tools.ietf.org/html/rfc6750#section-3.1");
	}

	@Test
	public void invalidTokenWhenMessageGivenThenBearerTokenErrorReturned() {
		String message = "message";
		BearerTokenError error = BearerTokenErrors.invalidToken(message);
		assertThat(error.getErrorCode()).isSameAs(BearerTokenErrorCodes.INVALID_TOKEN);
		assertThat(error.getDescription()).isSameAs(message);
		assertThat(error.getHttpStatus()).isSameAs(HttpStatus.UNAUTHORIZED);
		assertThat(error.getUri()).isEqualTo("https://tools.ietf.org/html/rfc6750#section-3.1");
	}

	@Test
	public void invalidTokenWhenInvalidMessageGivenThenDefaultBearerTokenErrorReturned() {
		String message = "has \"invalid\" chars";
		BearerTokenError error = BearerTokenErrors.invalidToken(message);
		assertThat(error.getErrorCode()).isSameAs(BearerTokenErrorCodes.INVALID_TOKEN);
		assertThat(error.getDescription()).isEqualTo("Invalid token");
		assertThat(error.getHttpStatus()).isSameAs(HttpStatus.UNAUTHORIZED);
		assertThat(error.getUri()).isEqualTo("https://tools.ietf.org/html/rfc6750#section-3.1");
	}

	@Test
	public void insufficientScopeWhenMessageGivenThenBearerTokenErrorReturned() {
		String message = "message";
		String scope = "scope";
		BearerTokenError error = BearerTokenErrors.insufficientScope(message, scope);
		assertThat(error.getErrorCode()).isSameAs(BearerTokenErrorCodes.INSUFFICIENT_SCOPE);
		assertThat(error.getDescription()).isSameAs(message);
		assertThat(error.getHttpStatus()).isSameAs(HttpStatus.FORBIDDEN);
		assertThat(error.getScope()).isSameAs(scope);
		assertThat(error.getUri()).isEqualTo("https://tools.ietf.org/html/rfc6750#section-3.1");
	}

	@Test
	public void insufficientScopeWhenInvalidMessageGivenThenDefaultBearerTokenErrorReturned() {
		String message = "has \"invalid\" chars";
		BearerTokenError error = BearerTokenErrors.insufficientScope(message, "scope");
		assertThat(error.getErrorCode()).isSameAs(BearerTokenErrorCodes.INSUFFICIENT_SCOPE);
		assertThat(error.getDescription()).isSameAs("Insufficient scope");
		assertThat(error.getHttpStatus()).isSameAs(HttpStatus.FORBIDDEN);
		assertThat(error.getScope()).isNull();
		assertThat(error.getUri()).isEqualTo("https://tools.ietf.org/html/rfc6750#section-3.1");
	}

}

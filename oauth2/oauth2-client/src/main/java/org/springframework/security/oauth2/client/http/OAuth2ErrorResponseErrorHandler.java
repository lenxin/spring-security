package org.springframework.security.oauth2.client.http;

import java.io.IOException;

import com.nimbusds.oauth2.sdk.token.BearerTokenError;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.core.OAuth2AuthorizationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.http.converter.OAuth2ErrorHttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * A {@link ResponseErrorHandler} that handles an {@link OAuth2Error OAuth 2.0 Error}.
 *
 * @author Joe Grandja
 * @since 5.1
 * @see ResponseErrorHandler
 * @see OAuth2Error
 */
public class OAuth2ErrorResponseErrorHandler implements ResponseErrorHandler {

	private final OAuth2ErrorHttpMessageConverter oauth2ErrorConverter = new OAuth2ErrorHttpMessageConverter();

	private final ResponseErrorHandler defaultErrorHandler = new DefaultResponseErrorHandler();

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return this.defaultErrorHandler.hasError(response);
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (!HttpStatus.BAD_REQUEST.equals(response.getStatusCode())) {
			this.defaultErrorHandler.handleError(response);
		}
		// A Bearer Token Error may be in the WWW-Authenticate response header
		// See https://tools.ietf.org/html/rfc6750#section-3
		OAuth2Error oauth2Error = this.readErrorFromWwwAuthenticate(response.getHeaders());
		if (oauth2Error == null) {
			oauth2Error = this.oauth2ErrorConverter.read(OAuth2Error.class, response);
		}
		throw new OAuth2AuthorizationException(oauth2Error);
	}

	private OAuth2Error readErrorFromWwwAuthenticate(HttpHeaders headers) {
		String wwwAuthenticateHeader = headers.getFirst(HttpHeaders.WWW_AUTHENTICATE);
		if (!StringUtils.hasText(wwwAuthenticateHeader)) {
			return null;
		}
		BearerTokenError bearerTokenError = getBearerToken(wwwAuthenticateHeader);
		String errorCode = (bearerTokenError.getCode() != null) ? bearerTokenError.getCode()
				: OAuth2ErrorCodes.SERVER_ERROR;
		String errorDescription = bearerTokenError.getDescription();
		String errorUri = (bearerTokenError.getURI() != null) ? bearerTokenError.getURI().toString() : null;
		return new OAuth2Error(errorCode, errorDescription, errorUri);
	}

	private BearerTokenError getBearerToken(String wwwAuthenticateHeader) {
		try {
			return BearerTokenError.parse(wwwAuthenticateHeader);
		}
		catch (Exception ex) {
			return null;
		}
	}

}

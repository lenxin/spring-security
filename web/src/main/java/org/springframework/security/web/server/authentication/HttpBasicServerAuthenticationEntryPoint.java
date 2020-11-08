package org.springframework.security.web.server.authentication;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * Prompts a user for HTTP Basic authentication.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class HttpBasicServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

	private static final String WWW_AUTHENTICATE = "WWW-Authenticate";

	private static final String DEFAULT_REALM = "Realm";

	private static String WWW_AUTHENTICATE_FORMAT = "Basic realm=\"%s\"";

	private String headerValue = createHeaderValue(DEFAULT_REALM);

	@Override
	public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
		return Mono.fromRunnable(() -> {
			ServerHttpResponse response = exchange.getResponse();
			response.setStatusCode(HttpStatus.UNAUTHORIZED);
			response.getHeaders().set(WWW_AUTHENTICATE, this.headerValue);
		});
	}

	/**
	 * Sets the realm to be used
	 * @param realm the realm. Default is "Realm"
	 */
	public void setRealm(String realm) {
		this.headerValue = createHeaderValue(realm);
	}

	private static String createHeaderValue(String realm) {
		Assert.notNull(realm, "realm cannot be null");
		return String.format(WWW_AUTHENTICATE_FORMAT, realm);
	}

}

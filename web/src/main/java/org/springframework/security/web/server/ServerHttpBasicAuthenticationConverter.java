package org.springframework.security.web.server;

import java.util.Base64;
import java.util.function.Function;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * Converts from a {@link ServerWebExchange} to an {@link Authentication} that can be
 * authenticated.
 *
 * @author Rob Winch
 * @since 5.0
 * @deprecated Use
 * {@link org.springframework.security.web.server.authentication.ServerHttpBasicAuthenticationConverter}
 * instead.
 */
@Deprecated
public class ServerHttpBasicAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

	public static final String BASIC = "Basic ";

	@Override
	@Deprecated
	public Mono<Authentication> apply(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (!StringUtils.startsWithIgnoreCase(authorization, "basic ")) {
			return Mono.empty();
		}
		String credentials = (authorization.length() <= BASIC.length()) ? ""
				: authorization.substring(BASIC.length(), authorization.length());
		String decoded = new String(base64Decode(credentials));
		String[] parts = decoded.split(":", 2);
		if (parts.length != 2) {
			return Mono.empty();
		}
		return Mono.just(new UsernamePasswordAuthenticationToken(parts[0], parts[1]));
	}

	private byte[] base64Decode(String value) {
		try {
			return Base64.getDecoder().decode(value);
		}
		catch (Exception ex) {
			return new byte[0];
		}
	}

}

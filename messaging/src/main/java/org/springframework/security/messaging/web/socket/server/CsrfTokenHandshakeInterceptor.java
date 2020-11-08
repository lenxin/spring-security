package org.springframework.security.messaging.web.socket.server;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Copies a CsrfToken from the HttpServletRequest's attributes to the WebSocket
 * attributes. This is used as the expected CsrfToken when validating connection requests
 * to ensure only the same origin connects.
 *
 * @author Rob Winch
 * @since 4.0
 */
public final class CsrfTokenHandshakeInterceptor implements HandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		HttpServletRequest httpRequest = ((ServletServerHttpRequest) request).getServletRequest();
		CsrfToken token = (CsrfToken) httpRequest.getAttribute(CsrfToken.class.getName());
		if (token == null) {
			return true;
		}
		attributes.put(CsrfToken.class.getName(), token);
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
	}

}

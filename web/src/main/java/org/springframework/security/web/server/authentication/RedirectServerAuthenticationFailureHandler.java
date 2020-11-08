package org.springframework.security.web.server.authentication;

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.util.Assert;

/**
 * Performs a redirect to a specified location.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class RedirectServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {

	private final URI location;

	private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

	/**
	 * Creates an instance
	 * @param location the location to redirect to (i.e. "/login?failed")
	 */
	public RedirectServerAuthenticationFailureHandler(String location) {
		Assert.notNull(location, "location cannot be null");
		this.location = URI.create(location);
	}

	/**
	 * Sets the RedirectStrategy to use.
	 * @param redirectStrategy the strategy to use. Default is DefaultRedirectStrategy.
	 */
	public void setRedirectStrategy(ServerRedirectStrategy redirectStrategy) {
		Assert.notNull(redirectStrategy, "redirectStrategy cannot be null");
		this.redirectStrategy = redirectStrategy;
	}

	@Override
	public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
		return this.redirectStrategy.sendRedirect(webFilterExchange.getExchange(), this.location);
	}

}

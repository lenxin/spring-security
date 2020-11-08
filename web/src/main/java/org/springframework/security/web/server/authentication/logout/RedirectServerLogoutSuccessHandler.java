package org.springframework.security.web.server.authentication.logout;

import java.net.URI;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.DefaultServerRedirectStrategy;
import org.springframework.security.web.server.ServerRedirectStrategy;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.util.Assert;

/**
 * Performs a redirect on log out success.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class RedirectServerLogoutSuccessHandler implements ServerLogoutSuccessHandler {

	public static final String DEFAULT_LOGOUT_SUCCESS_URL = "/login?logout";

	private URI logoutSuccessUrl = URI.create(DEFAULT_LOGOUT_SUCCESS_URL);

	private ServerRedirectStrategy redirectStrategy = new DefaultServerRedirectStrategy();

	@Override
	public Mono<Void> onLogoutSuccess(WebFilterExchange exchange, Authentication authentication) {
		return this.redirectStrategy.sendRedirect(exchange.getExchange(), this.logoutSuccessUrl);
	}

	/**
	 * The URL to redirect to after successfully logging out.
	 * @param logoutSuccessUrl the url to redirect to. Default is "/login?logout".
	 */
	public void setLogoutSuccessUrl(URI logoutSuccessUrl) {
		Assert.notNull(logoutSuccessUrl, "logoutSuccessUrl cannot be null");
		this.logoutSuccessUrl = logoutSuccessUrl;
	}

}

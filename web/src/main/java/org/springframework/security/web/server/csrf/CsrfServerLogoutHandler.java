package org.springframework.security.web.server.csrf;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.logout.ServerLogoutHandler;
import org.springframework.util.Assert;

/**
 * {@link CsrfServerLogoutHandler} is in charge of removing the {@link CsrfToken} upon
 * logout. A new {@link CsrfToken} will then be generated by the framework upon the next
 * request.
 *
 * @author Eric Deandrea
 * @since 5.1
 */
public class CsrfServerLogoutHandler implements ServerLogoutHandler {

	private final ServerCsrfTokenRepository csrfTokenRepository;

	/**
	 * Creates a new instance
	 * @param csrfTokenRepository The {@link ServerCsrfTokenRepository} to use
	 */
	public CsrfServerLogoutHandler(ServerCsrfTokenRepository csrfTokenRepository) {
		Assert.notNull(csrfTokenRepository, "csrfTokenRepository cannot be null");
		this.csrfTokenRepository = csrfTokenRepository;
	}

	/**
	 * Clears the {@link CsrfToken}
	 * @param exchange the exchange
	 * @param authentication the {@link Authentication}
	 * @return A completion notification (success or error)
	 */
	@Override
	public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
		return this.csrfTokenRepository.saveToken(exchange.getExchange(), null);
	}

}

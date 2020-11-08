package org.springframework.security.web.server.authentication.logout;

import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.util.Assert;

/**
 * A {@link ServerLogoutHandler} which removes the SecurityContext using the provided
 * {@link ServerSecurityContextRepository}
 *
 * @author Rob Winch
 * @since 5.0
 */
public class SecurityContextServerLogoutHandler implements ServerLogoutHandler {

	private ServerSecurityContextRepository securityContextRepository = new WebSessionServerSecurityContextRepository();

	@Override
	public Mono<Void> logout(WebFilterExchange exchange, Authentication authentication) {
		return this.securityContextRepository.save(exchange.getExchange(), null);
	}

	/**
	 * Sets the {@link ServerSecurityContextRepository} that should be used for logging
	 * out. Default is {@link WebSessionServerSecurityContextRepository}
	 * @param securityContextRepository the {@link ServerSecurityContextRepository} to
	 * use.
	 */
	public void setSecurityContextRepository(ServerSecurityContextRepository securityContextRepository) {
		Assert.notNull(securityContextRepository, "securityContextRepository cannot be null");
		this.securityContextRepository = securityContextRepository;
	}

}

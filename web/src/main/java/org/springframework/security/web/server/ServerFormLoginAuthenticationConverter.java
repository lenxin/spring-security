package org.springframework.security.web.server;

import java.util.function.Function;

import reactor.core.publisher.Mono;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

/**
 * Converts a ServerWebExchange into a UsernamePasswordAuthenticationToken from the form
 * data HTTP parameters.
 *
 * @author Rob Winch
 * @since 5.0
 * @deprecated use
 * {@link org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter}
 * instead.
 */
@Deprecated
public class ServerFormLoginAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {

	private String usernameParameter = "username";

	private String passwordParameter = "password";

	@Override
	@Deprecated
	public Mono<Authentication> apply(ServerWebExchange exchange) {
		return exchange.getFormData().map((data) -> createAuthentication(data));
	}

	private UsernamePasswordAuthenticationToken createAuthentication(MultiValueMap<String, String> data) {
		String username = data.getFirst(this.usernameParameter);
		String password = data.getFirst(this.passwordParameter);
		return new UsernamePasswordAuthenticationToken(username, password);
	}

	/**
	 * The parameter name of the form data to extract the username
	 * @param usernameParameter the username HTTP parameter
	 */
	public void setUsernameParameter(String usernameParameter) {
		Assert.notNull(usernameParameter, "usernameParameter cannot be null");
		this.usernameParameter = usernameParameter;
	}

	/**
	 * The parameter name of the form data to extract the password
	 * @param passwordParameter the password HTTP parameter
	 */
	public void setPasswordParameter(String passwordParameter) {
		Assert.notNull(passwordParameter, "passwordParameter cannot be null");
		this.passwordParameter = passwordParameter;
	}

}

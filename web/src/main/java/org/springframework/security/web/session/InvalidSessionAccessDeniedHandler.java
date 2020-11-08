package org.springframework.security.web.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.Assert;

/**
 * An adapter of {@link InvalidSessionStrategy} to {@link AccessDeniedHandler}
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class InvalidSessionAccessDeniedHandler implements AccessDeniedHandler {

	private final InvalidSessionStrategy invalidSessionStrategy;

	/**
	 * Creates a new instance
	 * @param invalidSessionStrategy the {@link InvalidSessionStrategy} to delegate to
	 */
	public InvalidSessionAccessDeniedHandler(InvalidSessionStrategy invalidSessionStrategy) {
		Assert.notNull(invalidSessionStrategy, "invalidSessionStrategy cannot be null");
		this.invalidSessionStrategy = invalidSessionStrategy;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		this.invalidSessionStrategy.onInvalidSessionDetected(request, response);
	}

}

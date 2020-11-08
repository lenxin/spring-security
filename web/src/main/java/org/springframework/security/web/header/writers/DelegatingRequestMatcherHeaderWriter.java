package org.springframework.security.web.header.writers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * Delegates to the provided {@link HeaderWriter} when
 * {@link RequestMatcher#matches(HttpServletRequest)} returns true.
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class DelegatingRequestMatcherHeaderWriter implements HeaderWriter {

	private final RequestMatcher requestMatcher;

	private final HeaderWriter delegateHeaderWriter;

	/**
	 * Creates a new instance
	 * @param requestMatcher the {@link RequestMatcher} to use. If returns true, the
	 * delegateHeaderWriter will be invoked.
	 * @param delegateHeaderWriter the {@link HeaderWriter} to invoke if the
	 * {@link RequestMatcher} returns true.
	 */
	public DelegatingRequestMatcherHeaderWriter(RequestMatcher requestMatcher, HeaderWriter delegateHeaderWriter) {
		Assert.notNull(requestMatcher, "requestMatcher cannot be null");
		Assert.notNull(delegateHeaderWriter, "delegateHeaderWriter cannot be null");
		this.requestMatcher = requestMatcher;
		this.delegateHeaderWriter = delegateHeaderWriter;
	}

	@Override
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		if (this.requestMatcher.matches(request)) {
			this.delegateHeaderWriter.writeHeaders(request, response);
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + " [requestMatcher=" + this.requestMatcher + ", delegateHeaderWriter="
				+ this.delegateHeaderWriter + "]";
	}

}

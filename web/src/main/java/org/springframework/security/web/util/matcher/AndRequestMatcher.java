package org.springframework.security.web.util.matcher;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.util.Assert;

/**
 * {@link RequestMatcher} that will return true if all of the passed in
 * {@link RequestMatcher} instances match.
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class AndRequestMatcher implements RequestMatcher {

	private final Log logger = LogFactory.getLog(getClass());

	private final List<RequestMatcher> requestMatchers;

	/**
	 * Creates a new instance
	 * @param requestMatchers the {@link RequestMatcher} instances to try
	 */
	public AndRequestMatcher(List<RequestMatcher> requestMatchers) {
		Assert.notEmpty(requestMatchers, "requestMatchers must contain a value");
		Assert.isTrue(!requestMatchers.contains(null), "requestMatchers cannot contain null values");
		this.requestMatchers = requestMatchers;
	}

	/**
	 * Creates a new instance
	 * @param requestMatchers the {@link RequestMatcher} instances to try
	 */
	public AndRequestMatcher(RequestMatcher... requestMatchers) {
		this(Arrays.asList(requestMatchers));
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		for (RequestMatcher matcher : this.requestMatchers) {
			if (!matcher.matches(request)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "And " + this.requestMatchers;
	}

}

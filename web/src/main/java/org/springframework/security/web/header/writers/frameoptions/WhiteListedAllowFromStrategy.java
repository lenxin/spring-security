package org.springframework.security.web.header.writers.frameoptions;

import java.util.Collection;

import org.springframework.util.Assert;

/**
 * Implementation which checks the supplied origin against a list of allowed origins.
 *
 * @author Marten Deinum
 * @since 3.2
 * @deprecated ALLOW-FROM is an obsolete directive that no longer works in modern
 * browsers. Instead use Content-Security-Policy with the <a href=
 * "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/frame-ancestors">frame-ancestors</a>
 * directive.
 */
@Deprecated
public final class WhiteListedAllowFromStrategy extends AbstractRequestParameterAllowFromStrategy {

	private final Collection<String> allowed;

	/**
	 * Creates a new instance
	 * @param allowed the origins that are allowed.
	 */
	public WhiteListedAllowFromStrategy(Collection<String> allowed) {
		Assert.notEmpty(allowed, "Allowed origins cannot be empty.");
		this.allowed = allowed;
	}

	@Override
	protected boolean allowed(String allowFromOrigin) {
		return this.allowed.contains(allowFromOrigin);
	}

}

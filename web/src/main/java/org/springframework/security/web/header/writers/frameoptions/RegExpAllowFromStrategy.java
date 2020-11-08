package org.springframework.security.web.header.writers.frameoptions;

import java.util.regex.Pattern;

import org.springframework.util.Assert;

/**
 * Implementation which uses a regular expression to validate the supplied origin. If the
 * value of the HTTP parameter matches the pattern, then the result will be ALLOW-FROM
 * &lt;paramter-value&gt;.
 *
 * @author Marten Deinum
 * @since 3.2
 * @deprecated ALLOW-FROM is an obsolete directive that no longer works in modern
 * browsers. Instead use Content-Security-Policy with the <a href=
 * "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/frame-ancestors">frame-ancestors</a>
 * directive.
 */
@Deprecated
public final class RegExpAllowFromStrategy extends AbstractRequestParameterAllowFromStrategy {

	private final Pattern pattern;

	/**
	 * Creates a new instance
	 * @param pattern the Pattern to compare against the HTTP parameter value. If the
	 * pattern matches, the domain will be allowed, else denied.
	 */
	public RegExpAllowFromStrategy(String pattern) {
		Assert.hasText(pattern, "Pattern cannot be empty.");
		this.pattern = Pattern.compile(pattern);
	}

	@Override
	protected boolean allowed(String allowFromOrigin) {
		return this.pattern.matcher(allowFromOrigin).matches();
	}

}

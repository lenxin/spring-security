package org.springframework.security.web.header.writers.frameoptions;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

/**
 * Simple implementation of the {@code AllowFromStrategy}
 *
 * @deprecated ALLOW-FROM is an obsolete directive that no longer works in modern
 * browsers. Instead use Content-Security-Policy with the <a href=
 * "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/frame-ancestors">frame-ancestors</a>
 * directive.
 */
@Deprecated
public final class StaticAllowFromStrategy implements AllowFromStrategy {

	private final URI uri;

	public StaticAllowFromStrategy(URI uri) {
		this.uri = uri;
	}

	@Override
	public String getAllowFromValue(HttpServletRequest request) {
		return this.uri.toString();
	}

}

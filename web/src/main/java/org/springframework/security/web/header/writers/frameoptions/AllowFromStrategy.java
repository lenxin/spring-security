package org.springframework.security.web.header.writers.frameoptions;

import javax.servlet.http.HttpServletRequest;

/**
 * Strategy interfaces used by the {@code FrameOptionsHeaderWriter} to determine the
 * actual value to use for the X-Frame-Options header when using the ALLOW-FROM directive.
 *
 * @author Marten Deinum
 * @since 3.2
 * @deprecated ALLOW-FROM is an obsolete directive that no longer works in modern
 * browsers. Instead use Content-Security-Policy with the <a href=
 * "https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Security-Policy/frame-ancestors">frame-ancestors</a>
 * directive.
 */
@Deprecated
public interface AllowFromStrategy {

	/**
	 * Gets the value for ALLOW-FROM excluding the ALLOW-FROM. For example, the result
	 * might be "https://example.com/".
	 * @param request the {@link HttpServletRequest}
	 * @return the value for ALLOW-FROM or null if no header should be added for this
	 * request.
	 */
	String getAllowFromValue(HttpServletRequest request);

}

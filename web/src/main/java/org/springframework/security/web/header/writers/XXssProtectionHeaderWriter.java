package org.springframework.security.web.header.writers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.header.HeaderWriter;

/**
 * Renders the <a href=
 * "https://blogs.msdn.com/b/ieinternals/archive/2011/01/31/controlling-the-internet-explorer-xss-filter-with-the-x-xss-protection-http-header.aspx"
 * >X-XSS-Protection header</a>.
 *
 * @author Rob Winch
 * @author Ankur Pathak
 * @since 3.2
 */
public final class XXssProtectionHeaderWriter implements HeaderWriter {

	private static final String XSS_PROTECTION_HEADER = "X-XSS-Protection";

	private boolean enabled;

	private boolean block;

	private String headerValue;

	/**
	 * Create a new instance
	 */
	public XXssProtectionHeaderWriter() {
		this.enabled = true;
		this.block = true;
		updateHeaderValue();
	}

	@Override
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		if (!response.containsHeader(XSS_PROTECTION_HEADER)) {
			response.setHeader(XSS_PROTECTION_HEADER, this.headerValue);
		}
	}

	/**
	 * If true, will contain a value of 1. For example:
	 *
	 * <pre>
	 * X-XSS-Protection: 1
	 * </pre>
	 *
	 * or if {@link #setBlock(boolean)} is true
	 *
	 *
	 * <pre>
	 * X-XSS-Protection: 1; mode=block
	 * </pre>
	 *
	 * If false, will explicitly disable specify that X-XSS-Protection is disabled. For
	 * example:
	 *
	 * <pre>
	 * X-XSS-Protection: 0
	 * </pre>
	 * @param enabled the new value
	 */
	public void setEnabled(boolean enabled) {
		if (!enabled) {
			setBlock(false);
		}
		this.enabled = enabled;
		updateHeaderValue();
	}

	/**
	 * If false, will not specify the mode as blocked. In this instance, any content will
	 * be attempted to be fixed. If true, the content will be replaced with "#".
	 * @param block the new value
	 */
	public void setBlock(boolean block) {
		if (!this.enabled && block) {
			throw new IllegalArgumentException("Cannot set block to true with enabled false");
		}
		this.block = block;
		updateHeaderValue();
	}

	private void updateHeaderValue() {
		if (!this.enabled) {
			this.headerValue = "0";
			return;
		}
		this.headerValue = "1";
		if (this.block) {
			this.headerValue += "; mode=block";
		}
	}

	@Override
	public String toString() {
		return getClass().getName() + " [headerValue=" + this.headerValue + "]";
	}

}

package org.springframework.security.web.authentication.logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.Assert;

/**
 * @author Rafiullah Hamedy
 * @since 5.2
 */
public final class HeaderWriterLogoutHandler implements LogoutHandler {

	private final HeaderWriter headerWriter;

	/**
	 * Constructs a new instance using the passed {@link HeaderWriter} implementation
	 * @param headerWriter
	 * @throws IllegalArgumentException if headerWriter is null.
	 */
	public HeaderWriterLogoutHandler(HeaderWriter headerWriter) {
		Assert.notNull(headerWriter, "headerWriter cannot be null");
		this.headerWriter = headerWriter;
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		this.headerWriter.writeHeaders(request, response);
	}

}

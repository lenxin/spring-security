package org.springframework.security.oauth2.server.resource.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

/**
 * Generic resolver extracting pre-authenticated JWT identity from a custom header.
 *
 * @author Elena Felder
 * @since 5.2
 */
public class HeaderBearerTokenResolver implements BearerTokenResolver {

	private String header;

	public HeaderBearerTokenResolver(String header) {
		Assert.hasText(header, "header cannot be empty");
		this.header = header;
	}

	@Override
	public String resolve(HttpServletRequest request) {
		return request.getHeader(this.header);
	}

}

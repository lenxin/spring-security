package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

class ELRequestMatcherContext {

	private final HttpServletRequest request;

	ELRequestMatcherContext(HttpServletRequest request) {
		this.request = request;
	}

	public boolean hasIpAddress(String ipAddress) {
		return (new IpAddressMatcher(ipAddress).matches(this.request));
	}

	public boolean hasHeader(String headerName, String value) {
		String header = this.request.getHeader(headerName);
		return StringUtils.hasText(header) && header.contains(value);
	}

}

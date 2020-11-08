package org.springframework.security;

import javax.servlet.ServletRequest;

import org.springframework.security.web.PortResolver;

/**
 * Always returns the constructor-specified HTTP and HTTPS ports.
 *
 * @author Ben Alex
 */
public class MockPortResolver implements PortResolver {

	private int http = 80;

	private int https = 443;

	public MockPortResolver(int http, int https) {
		this.http = http;
		this.https = https;
	}

	@Override
	public int getServerPort(ServletRequest request) {
		if ((request.getScheme() != null) && request.getScheme().equals("https")) {
			return this.https;
		}
		else {
			return this.http;
		}
	}

}

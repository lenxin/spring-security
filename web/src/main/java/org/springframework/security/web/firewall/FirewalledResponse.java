package org.springframework.security.web.firewall;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.util.Assert;

/**
 * @author Luke Taylor
 * @author Eddú Meléndez
 * @author Gabriel Lavoie
 * @author Luke Butters
 */
class FirewalledResponse extends HttpServletResponseWrapper {

	private static final String LOCATION_HEADER = "Location";

	private static final String SET_COOKIE_HEADER = "Set-Cookie";

	FirewalledResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		// TODO: implement pluggable validation, instead of simple blocklist.
		// SEC-1790. Prevent redirects containing CRLF
		validateCrlf(LOCATION_HEADER, location);
		super.sendRedirect(location);
	}

	@Override
	public void setHeader(String name, String value) {
		validateCrlf(name, value);
		super.setHeader(name, value);
	}

	@Override
	public void addHeader(String name, String value) {
		validateCrlf(name, value);
		super.addHeader(name, value);
	}

	@Override
	public void addCookie(Cookie cookie) {
		if (cookie != null) {
			validateCrlf(SET_COOKIE_HEADER, cookie.getName());
			validateCrlf(SET_COOKIE_HEADER, cookie.getValue());
			validateCrlf(SET_COOKIE_HEADER, cookie.getPath());
			validateCrlf(SET_COOKIE_HEADER, cookie.getDomain());
			validateCrlf(SET_COOKIE_HEADER, cookie.getComment());
		}
		super.addCookie(cookie);
	}

	void validateCrlf(String name, String value) {
		Assert.isTrue(!hasCrlf(name) && !hasCrlf(value), () -> "Invalid characters (CR/LF) in header " + name);
	}

	private boolean hasCrlf(String value) {
		return value != null && (value.indexOf('\n') != -1 || value.indexOf('\r') != -1);
	}

}

package org.springframework.security.web.savedrequest;

import java.io.Serializable;

import javax.servlet.http.Cookie;

/**
 * Stores off the values of a cookie in a serializable holder
 *
 * @author Ray Krueger
 */
public class SavedCookie implements Serializable {

	private final java.lang.String name;

	private final java.lang.String value;

	private final java.lang.String comment;

	private final java.lang.String domain;

	private final int maxAge;

	private final java.lang.String path;

	private final boolean secure;

	private final int version;

	public SavedCookie(String name, String value, String comment, String domain, int maxAge, String path,
			boolean secure, int version) {
		this.name = name;
		this.value = value;
		this.comment = comment;
		this.domain = domain;
		this.maxAge = maxAge;
		this.path = path;
		this.secure = secure;
		this.version = version;
	}

	public SavedCookie(Cookie cookie) {
		this(cookie.getName(), cookie.getValue(), cookie.getComment(), cookie.getDomain(), cookie.getMaxAge(),
				cookie.getPath(), cookie.getSecure(), cookie.getVersion());
	}

	public String getName() {
		return this.name;
	}

	public String getValue() {
		return this.value;
	}

	public String getComment() {
		return this.comment;
	}

	public String getDomain() {
		return this.domain;
	}

	public int getMaxAge() {
		return this.maxAge;
	}

	public String getPath() {
		return this.path;
	}

	public boolean isSecure() {
		return this.secure;
	}

	public int getVersion() {
		return this.version;
	}

	public Cookie getCookie() {
		Cookie cookie = new Cookie(getName(), getValue());
		if (getComment() != null) {
			cookie.setComment(getComment());
		}
		if (getDomain() != null) {
			cookie.setDomain(getDomain());
		}
		if (getPath() != null) {
			cookie.setPath(getPath());
		}
		cookie.setVersion(getVersion());
		cookie.setMaxAge(getMaxAge());
		cookie.setSecure(isSecure());
		return cookie;
	}

}

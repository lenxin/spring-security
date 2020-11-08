package org.springframework.security.web.util;

import org.springframework.util.Assert;

/**
 * Internal class for building redirect URLs.
 *
 * Could probably make more use of the classes in java.net for this.
 *
 * @author Luke Taylor
 * @since 2.0
 */
public class RedirectUrlBuilder {

	private String scheme;

	private String serverName;

	private int port;

	private String contextPath;

	private String servletPath;

	private String pathInfo;

	private String query;

	public void setScheme(String scheme) {
		Assert.isTrue("http".equals(scheme) || "https".equals(scheme), () -> "Unsupported scheme '" + scheme + "'");
		this.scheme = scheme;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getUrl() {
		StringBuilder sb = new StringBuilder();
		Assert.notNull(this.scheme, "scheme cannot be null");
		Assert.notNull(this.serverName, "serverName cannot be null");
		sb.append(this.scheme).append("://").append(this.serverName);
		// Append the port number if it's not standard for the scheme
		if (this.port != (this.scheme.equals("http") ? 80 : 443)) {
			sb.append(":").append(this.port);
		}
		if (this.contextPath != null) {
			sb.append(this.contextPath);
		}
		if (this.servletPath != null) {
			sb.append(this.servletPath);
		}
		if (this.pathInfo != null) {
			sb.append(this.pathInfo);
		}
		if (this.query != null) {
			sb.append("?").append(this.query);
		}
		return sb.toString();
	}

}

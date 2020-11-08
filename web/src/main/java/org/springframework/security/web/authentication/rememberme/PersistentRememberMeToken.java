package org.springframework.security.web.authentication.rememberme;

import java.util.Date;

/**
 * @author Luke Taylor
 */
public class PersistentRememberMeToken {

	private final String username;

	private final String series;

	private final String tokenValue;

	private final Date date;

	public PersistentRememberMeToken(String username, String series, String tokenValue, Date date) {
		this.username = username;
		this.series = series;
		this.tokenValue = tokenValue;
		this.date = date;
	}

	public String getUsername() {
		return this.username;
	}

	public String getSeries() {
		return this.series;
	}

	public String getTokenValue() {
		return this.tokenValue;
	}

	public Date getDate() {
		return this.date;
	}

}

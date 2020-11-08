package org.springframework.security.web.authentication.rememberme;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Simple <tt>PersistentTokenRepository</tt> implementation backed by a Map. Intended for
 * testing only.
 *
 * @author Luke Taylor
 */
public class InMemoryTokenRepositoryImpl implements PersistentTokenRepository {

	private final Map<String, PersistentRememberMeToken> seriesTokens = new HashMap<>();

	@Override
	public synchronized void createNewToken(PersistentRememberMeToken token) {
		PersistentRememberMeToken current = this.seriesTokens.get(token.getSeries());
		if (current != null) {
			throw new DataIntegrityViolationException("Series Id '" + token.getSeries() + "' already exists!");
		}
		this.seriesTokens.put(token.getSeries(), token);
	}

	@Override
	public synchronized void updateToken(String series, String tokenValue, Date lastUsed) {
		PersistentRememberMeToken token = getTokenForSeries(series);
		PersistentRememberMeToken newToken = new PersistentRememberMeToken(token.getUsername(), series, tokenValue,
				new Date());
		// Store it, overwriting the existing one.
		this.seriesTokens.put(series, newToken);
	}

	@Override
	public synchronized PersistentRememberMeToken getTokenForSeries(String seriesId) {
		return this.seriesTokens.get(seriesId);
	}

	@Override
	public synchronized void removeUserTokens(String username) {
		Iterator<String> series = this.seriesTokens.keySet().iterator();
		while (series.hasNext()) {
			String seriesId = series.next();
			PersistentRememberMeToken token = this.seriesTokens.get(seriesId);
			if (username.equals(token.getUsername())) {
				series.remove();
			}
		}
	}

}

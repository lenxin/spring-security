package org.springframework.security.web.authentication.rememberme;

import java.util.Date;

/**
 * The abstraction used by {@link PersistentTokenBasedRememberMeServices} to store the
 * persistent login tokens for a user.
 *
 * @author Luke Taylor
 * @since 2.0
 * @see JdbcTokenRepositoryImpl
 * @see InMemoryTokenRepositoryImpl
 */
public interface PersistentTokenRepository {

	void createNewToken(PersistentRememberMeToken token);

	void updateToken(String series, String tokenValue, Date lastUsed);

	PersistentRememberMeToken getTokenForSeries(String seriesId);

	void removeUserTokens(String username);

}

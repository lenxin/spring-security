package org.springframework.security.oauth2.core.endpoint;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * A {@link Converter} that converts the provided {@link OAuth2AccessTokenResponse} to a
 * {@code Map} representation of the OAuth 2.0 Access Token Response parameters.
 *
 * @author Joe Grandja
 * @author Nikita Konev
 * @since 5.3
 */
public final class OAuth2AccessTokenResponseMapConverter
		implements Converter<OAuth2AccessTokenResponse, Map<String, String>> {

	@Override
	public Map<String, String> convert(OAuth2AccessTokenResponse tokenResponse) {
		Map<String, String> parameters = new HashMap<>();
		parameters.put(OAuth2ParameterNames.ACCESS_TOKEN, tokenResponse.getAccessToken().getTokenValue());
		parameters.put(OAuth2ParameterNames.TOKEN_TYPE, tokenResponse.getAccessToken().getTokenType().getValue());
		parameters.put(OAuth2ParameterNames.EXPIRES_IN, String.valueOf(getExpiresIn(tokenResponse)));
		if (!CollectionUtils.isEmpty(tokenResponse.getAccessToken().getScopes())) {
			parameters.put(OAuth2ParameterNames.SCOPE,
					StringUtils.collectionToDelimitedString(tokenResponse.getAccessToken().getScopes(), " "));
		}
		if (tokenResponse.getRefreshToken() != null) {
			parameters.put(OAuth2ParameterNames.REFRESH_TOKEN, tokenResponse.getRefreshToken().getTokenValue());
		}
		if (!CollectionUtils.isEmpty(tokenResponse.getAdditionalParameters())) {
			for (Map.Entry<String, Object> entry : tokenResponse.getAdditionalParameters().entrySet()) {
				parameters.put(entry.getKey(), entry.getValue().toString());
			}
		}
		return parameters;
	}

	private long getExpiresIn(OAuth2AccessTokenResponse tokenResponse) {
		if (tokenResponse.getAccessToken().getExpiresAt() != null) {
			return ChronoUnit.SECONDS.between(Instant.now(), tokenResponse.getAccessToken().getExpiresAt());
		}
		return -1;
	}

}

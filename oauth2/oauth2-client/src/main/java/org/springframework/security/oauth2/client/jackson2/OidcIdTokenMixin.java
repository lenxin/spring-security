package org.springframework.security.oauth2.client.jackson2;

import java.time.Instant;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;

/**
 * This mixin class is used to serialize/deserialize {@link OidcIdToken}.
 *
 * @author Joe Grandja
 * @since 5.3
 * @see OidcIdToken
 * @see OAuth2ClientJackson2Module
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class OidcIdTokenMixin {

	@JsonCreator
	OidcIdTokenMixin(@JsonProperty("tokenValue") String tokenValue, @JsonProperty("issuedAt") Instant issuedAt,
			@JsonProperty("expiresAt") Instant expiresAt, @JsonProperty("claims") Map<String, Object> claims) {
	}

}

package org.springframework.security.oauth2.client.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;

/**
 * This mixin class is used to serialize/deserialize {@link OAuth2AuthorizedClient}.
 *
 * @author Joe Grandja
 * @since 5.3
 * @see OAuth2AuthorizedClient
 * @see OAuth2ClientJackson2Module
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class OAuth2AuthorizedClientMixin {

	@JsonCreator
	OAuth2AuthorizedClientMixin(@JsonProperty("clientRegistration") ClientRegistration clientRegistration,
			@JsonProperty("principalName") String principalName,
			@JsonProperty("accessToken") OAuth2AccessToken accessToken,
			@JsonProperty("refreshToken") OAuth2RefreshToken refreshToken) {
	}

}

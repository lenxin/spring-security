package org.springframework.security.oauth2.client.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * This mixin class is used to serialize/deserialize {@link OAuth2Error} as part of
 * {@link org.springframework.security.oauth2.core.OAuth2AuthenticationException}.
 *
 * @author Dennis Neufeld
 * @since 5.3.4
 * @see OAuth2Error
 * @see OAuth2AuthenticationExceptionMixin
 * @see OAuth2ClientJackson2Module
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class OAuth2ErrorMixin {

	@JsonCreator
	OAuth2ErrorMixin(@JsonProperty("errorCode") String errorCode, @JsonProperty("description") String description,
			@JsonProperty("uri") String uri) {
	}

}

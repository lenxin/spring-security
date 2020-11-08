package org.springframework.security.oauth2.client.jackson2;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

/**
 * This mixin class is used to serialize/deserialize {@link DefaultOAuth2User}.
 *
 * @author Joe Grandja
 * @since 5.3
 * @see DefaultOAuth2User
 * @see OAuth2ClientJackson2Module
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class DefaultOAuth2UserMixin {

	@JsonCreator
	DefaultOAuth2UserMixin(@JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities,
			@JsonProperty("attributes") Map<String, Object> attributes,
			@JsonProperty("nameAttributeKey") String nameAttributeKey) {
	}

}

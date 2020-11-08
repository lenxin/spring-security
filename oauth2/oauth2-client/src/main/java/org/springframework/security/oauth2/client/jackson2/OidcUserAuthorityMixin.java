package org.springframework.security.oauth2.client.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

/**
 * This mixin class is used to serialize/deserialize {@link OidcUserAuthority}.
 *
 * @author Joe Grandja
 * @since 5.3
 * @see OidcUserAuthority
 * @see OAuth2ClientJackson2Module
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(value = { "attributes" }, ignoreUnknown = true)
abstract class OidcUserAuthorityMixin {

	@JsonCreator
	OidcUserAuthorityMixin(@JsonProperty("authority") String authority, @JsonProperty("idToken") OidcIdToken idToken,
			@JsonProperty("userInfo") OidcUserInfo userInfo) {
	}

}

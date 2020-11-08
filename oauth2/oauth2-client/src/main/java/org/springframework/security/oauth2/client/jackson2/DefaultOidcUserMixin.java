package org.springframework.security.oauth2.client.jackson2;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

/**
 * This mixin class is used to serialize/deserialize {@link DefaultOidcUser}.
 *
 * @author Joe Grandja
 * @since 5.3
 * @see DefaultOidcUser
 * @see OAuth2ClientJackson2Module
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(value = { "attributes" }, ignoreUnknown = true)
abstract class DefaultOidcUserMixin {

	@JsonCreator
	DefaultOidcUserMixin(@JsonProperty("authorities") Collection<? extends GrantedAuthority> authorities,
			@JsonProperty("idToken") OidcIdToken idToken, @JsonProperty("userInfo") OidcUserInfo userInfo,
			@JsonProperty("nameAttributeKey") String nameAttributeKey) {
	}

}

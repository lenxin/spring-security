package org.springframework.security.oauth2.server.resource.authentication;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

/**
 * Test instances of {@link BearerTokenAuthentication}
 *
 * @author Josh Cummings
 */
public final class TestBearerTokenAuthentications {

	private TestBearerTokenAuthentications() {
	}

	public static BearerTokenAuthentication bearer() {
		Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("SCOPE_USER");
		OAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(
				Collections.singletonMap("sub", "user"), authorities);
		OAuth2AccessToken token = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, "token", Instant.now(),
				Instant.now().plusSeconds(86400), new HashSet<>(Arrays.asList("USER")));
		return new BearerTokenAuthentication(principal, token, authorities);
	}

}

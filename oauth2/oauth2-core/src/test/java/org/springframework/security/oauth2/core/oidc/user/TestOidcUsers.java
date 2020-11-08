package org.springframework.security.oauth2.core.oidc.user;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

/**
 * @author Joe Grandja
 */
public final class TestOidcUsers {

	private TestOidcUsers() {
	}

	public static DefaultOidcUser create() {
		OidcIdToken idToken = idToken();
		OidcUserInfo userInfo = userInfo();
		return new DefaultOidcUser(authorities(idToken, userInfo), idToken, userInfo);
	}

	private static OidcIdToken idToken() {
		Instant issuedAt = Instant.now();
		Instant expiresAt = issuedAt.plusSeconds(3600);
		// @formatter:off
		return OidcIdToken.withTokenValue("id-token")
				.issuedAt(issuedAt)
				.expiresAt(expiresAt)
				.subject("subject")
				.issuer("http://localhost/issuer")
				.audience(Collections.unmodifiableSet(new LinkedHashSet<>(Collections.singletonList("client"))))
				.authorizedParty("client")
				.build();
		// @formatter:on
	}

	private static OidcUserInfo userInfo() {
		return OidcUserInfo.builder().subject("subject").name("full name").build();
	}

	private static Collection<? extends GrantedAuthority> authorities(OidcIdToken idToken, OidcUserInfo userInfo) {
		return new LinkedHashSet<>(Arrays.asList(new OidcUserAuthority(idToken, userInfo),
				new SimpleGrantedAuthority("SCOPE_read"), new SimpleGrantedAuthority("SCOPE_write")));
	}

}

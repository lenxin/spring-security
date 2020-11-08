package org.springframework.security.oauth2.server.resource.authentication;

import java.util.Arrays;

import org.junit.Test;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link JwtBearerTokenAuthenticationConverter}
 *
 * @author Josh Cummings
 */
public class JwtBearerTokenAuthenticationConverterTests {

	private final JwtBearerTokenAuthenticationConverter converter = new JwtBearerTokenAuthenticationConverter();

	@Test
	public void convertWhenJwtThenBearerTokenAuthentication() {
		// @formatter:off
		Jwt jwt = Jwt.withTokenValue("token-value")
				.claim("claim", "value")
				.header("header", "value")
				.build();
		// @formatter:on
		AbstractAuthenticationToken token = this.converter.convert(jwt);
		assertThat(token).isInstanceOf(BearerTokenAuthentication.class);
		BearerTokenAuthentication bearerToken = (BearerTokenAuthentication) token;
		assertThat(bearerToken.getToken().getTokenValue()).isEqualTo("token-value");
		assertThat(bearerToken.getTokenAttributes()).containsOnlyKeys("claim");
		assertThat(bearerToken.getAuthorities()).isEmpty();
	}

	@Test
	public void convertWhenJwtWithScopeAttributeThenBearerTokenAuthentication() {
		// @formatter:off
		Jwt jwt = Jwt.withTokenValue("token-value")
				.claim("scope", "message:read message:write")
				.header("header", "value")
				.build();
		// @formatter:on
		AbstractAuthenticationToken token = this.converter.convert(jwt);
		assertThat(token).isInstanceOf(BearerTokenAuthentication.class);
		BearerTokenAuthentication bearerToken = (BearerTokenAuthentication) token;
		assertThat(bearerToken.getAuthorities()).containsExactly(new SimpleGrantedAuthority("SCOPE_message:read"),
				new SimpleGrantedAuthority("SCOPE_message:write"));
	}

	@Test
	public void convertWhenJwtWithScpAttributeThenBearerTokenAuthentication() {
		// @formatter:off
		Jwt jwt = Jwt.withTokenValue("token-value")
				.claim("scp", Arrays.asList("message:read", "message:write"))
				.header("header", "value")
				.build();
		// @formatter:on
		AbstractAuthenticationToken token = this.converter.convert(jwt);
		assertThat(token).isInstanceOf(BearerTokenAuthentication.class);
		BearerTokenAuthentication bearerToken = (BearerTokenAuthentication) token;
		assertThat(bearerToken.getAuthorities()).containsExactly(new SimpleGrantedAuthority("SCOPE_message:read"),
				new SimpleGrantedAuthority("SCOPE_message:write"));
	}

}

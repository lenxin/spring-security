package org.springframework.security.oauth2.server.resource.authentication;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.TestJwts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link JwtAuthenticationConverter}
 *
 * @author Josh Cummings
 * @author Evgeniy Cheban
 */
public class JwtAuthenticationConverterTests {

	JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

	@Test
	public void convertWhenDefaultGrantedAuthoritiesConverterSet() {
		Jwt jwt = TestJwts.jwt().claim("scope", "message:read message:write").build();
		AbstractAuthenticationToken authentication = this.jwtAuthenticationConverter.convert(jwt);
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		assertThat(authorities).containsExactly(new SimpleGrantedAuthority("SCOPE_message:read"),
				new SimpleGrantedAuthority("SCOPE_message:write"));
	}

	@Test
	public void whenSettingNullGrantedAuthoritiesConverter() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(null))
				.withMessage("jwtGrantedAuthoritiesConverter cannot be null");
	}

	@Test
	public void convertWithOverriddenGrantedAuthoritiesConverter() {
		Jwt jwt = TestJwts.jwt().claim("scope", "message:read message:write").build();
		Converter<Jwt, Collection<GrantedAuthority>> grantedAuthoritiesConverter = (token) -> Arrays
				.asList(new SimpleGrantedAuthority("blah"));
		this.jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		AbstractAuthenticationToken authentication = this.jwtAuthenticationConverter.convert(jwt);
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		assertThat(authorities).containsExactly(new SimpleGrantedAuthority("blah"));
	}

	@Test
	public void whenSettingNullPrincipalClaimName() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.jwtAuthenticationConverter.setPrincipalClaimName(null))
				.withMessage("principalClaimName cannot be empty");
		// @formatter:on
	}

	@Test
	public void whenSettingEmptyPrincipalClaimName() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.jwtAuthenticationConverter.setPrincipalClaimName(""))
				.withMessage("principalClaimName cannot be empty");
		// @formatter:on
	}

	@Test
	public void whenSettingBlankPrincipalClaimName() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.jwtAuthenticationConverter.setPrincipalClaimName(" "))
				.withMessage("principalClaimName cannot be empty");
		// @formatter:on
	}

	@Test
	public void convertWhenPrincipalClaimNameSet() {
		this.jwtAuthenticationConverter.setPrincipalClaimName("user_id");
		Jwt jwt = TestJwts.jwt().claim("user_id", "100").build();
		AbstractAuthenticationToken authentication = this.jwtAuthenticationConverter.convert(jwt);
		assertThat(authentication.getName()).isEqualTo("100");
	}

}

package org.springframework.security.oauth2.server.resource.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Test;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.TestJwts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ReactiveJwtGrantedAuthoritiesConverterAdapter}
 *
 * @author Eric Deandrea
 * @since 5.2
 */
public class ReactiveJwtGrantedAuthoritiesConverterAdapterTests {

	@Test
	public void convertWithGrantedAuthoritiesConverter() {
		Jwt jwt = TestJwts.jwt().claim("scope", "message:read message:write").build();
		Converter<Jwt, Collection<GrantedAuthority>> grantedAuthoritiesConverter = (token) -> Arrays
				.asList(new SimpleGrantedAuthority("blah"));
		Collection<GrantedAuthority> authorities = new ReactiveJwtGrantedAuthoritiesConverterAdapter(
				grantedAuthoritiesConverter).convert(jwt).toStream().collect(Collectors.toList());
		assertThat(authorities).containsExactly(new SimpleGrantedAuthority("blah"));
	}

	@Test
	public void whenConstructingWithInvalidConverter() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new ReactiveJwtGrantedAuthoritiesConverterAdapter(null))
				.withMessage("grantedAuthoritiesConverter cannot be null");
		// @formatter:on
	}

}

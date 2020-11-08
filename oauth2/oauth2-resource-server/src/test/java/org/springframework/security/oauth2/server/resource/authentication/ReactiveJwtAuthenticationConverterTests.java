package org.springframework.security.oauth2.server.resource.authentication;

import java.util.Collection;

import org.junit.Test;
import reactor.core.publisher.Flux;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.TestJwts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link ReactiveJwtAuthenticationConverter}
 *
 * @author Eric Deandrea
 * @since 5.2
 */
public class ReactiveJwtAuthenticationConverterTests {

	ReactiveJwtAuthenticationConverter jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();

	@Test
	public void convertWhenDefaultGrantedAuthoritiesConverterSet() {
		Jwt jwt = TestJwts.jwt().claim("scope", "message:read message:write").build();
		AbstractAuthenticationToken authentication = this.jwtAuthenticationConverter.convert(jwt).block();
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
		Converter<Jwt, Flux<GrantedAuthority>> grantedAuthoritiesConverter = (token) -> Flux
				.just(new SimpleGrantedAuthority("blah"));
		this.jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
		AbstractAuthenticationToken authentication = this.jwtAuthenticationConverter.convert(jwt).block();
		Collection<GrantedAuthority> authorities = authentication.getAuthorities();
		assertThat(authorities).containsExactly(new SimpleGrantedAuthority("blah"));
	}

}

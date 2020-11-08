package org.springframework.security.authentication;

import java.util.Arrays;

import org.junit.Test;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Josh Cummings
 */
public class TestingAuthenticationTokenTests {

	@Test
	public void constructorWhenNoAuthoritiesThenUnauthenticated() {
		TestingAuthenticationToken unauthenticated = new TestingAuthenticationToken("principal", "credentials");
		assertThat(unauthenticated.isAuthenticated()).isFalse();
	}

	@Test
	public void constructorWhenArityAuthoritiesThenAuthenticated() {
		TestingAuthenticationToken authenticated = new TestingAuthenticationToken("principal", "credentials",
				"authority");
		assertThat(authenticated.isAuthenticated()).isTrue();
	}

	@Test
	public void constructorWhenCollectionAuthoritiesThenAuthenticated() {
		TestingAuthenticationToken authenticated = new TestingAuthenticationToken("principal", "credentials",
				Arrays.asList(new SimpleGrantedAuthority("authority")));
		assertThat(authenticated.isAuthenticated()).isTrue();
	}

}

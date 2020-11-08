package org.springframework.security.oauth2.client.authentication;

import java.util.Collection;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link OAuth2AuthenticationToken}.
 *
 * @author Joe Grandja
 */
public class OAuth2AuthenticationTokenTests {

	private OAuth2User principal;

	private Collection<? extends GrantedAuthority> authorities;

	private String authorizedClientRegistrationId;

	@Before
	public void setUp() {
		this.principal = mock(OAuth2User.class);
		this.authorities = Collections.emptyList();
		this.authorizedClientRegistrationId = "client-registration-1";
	}

	@Test
	public void constructorWhenPrincipalIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new OAuth2AuthenticationToken(null, this.authorities, this.authorizedClientRegistrationId));
	}

	@Test
	public void constructorWhenAuthoritiesIsNullThenCreated() {
		new OAuth2AuthenticationToken(this.principal, null, this.authorizedClientRegistrationId);
	}

	@Test
	public void constructorWhenAuthoritiesIsEmptyThenCreated() {
		new OAuth2AuthenticationToken(this.principal, Collections.emptyList(), this.authorizedClientRegistrationId);
	}

	@Test
	public void constructorWhenAuthorizedClientRegistrationIdIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OAuth2AuthenticationToken(this.principal, this.authorities, null));
	}

	@Test
	public void constructorWhenAllParametersProvidedAndValidThenCreated() {
		OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(this.principal, this.authorities,
				this.authorizedClientRegistrationId);
		assertThat(authentication.getPrincipal()).isEqualTo(this.principal);
		assertThat(authentication.getCredentials()).isEqualTo("");
		assertThat(authentication.getAuthorities()).isEqualTo(this.authorities);
		assertThat(authentication.getAuthorizedClientRegistrationId()).isEqualTo(this.authorizedClientRegistrationId);
		assertThat(authentication.isAuthenticated()).isEqualTo(true);
	}

}

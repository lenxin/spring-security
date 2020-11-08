package org.springframework.security.oauth2.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link DefaultOAuth2AuthenticatedPrincipal}
 *
 * @author Josh Cummings
 */
public class DefaultOAuth2AuthenticatedPrincipalTests {

	String name = "test-subject";

	Map<String, Object> attributes = Collections.singletonMap("sub", this.name);

	Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("SCOPE_read");

	@Test
	public void constructorWhenAttributesIsNullOrEmptyThenIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultOAuth2AuthenticatedPrincipal(null, this.authorities));
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultOAuth2AuthenticatedPrincipal(Collections.emptyMap(), this.authorities));
	}

	@Test
	public void constructorWhenAuthoritiesIsNullOrEmptyThenNoAuthorities() {
		Collection<? extends GrantedAuthority> authorities = new DefaultOAuth2AuthenticatedPrincipal(this.attributes,
				null).getAuthorities();
		assertThat(authorities).isEmpty();
		authorities = new DefaultOAuth2AuthenticatedPrincipal(this.attributes, Collections.emptyList())
				.getAuthorities();
		assertThat(authorities).isEmpty();
	}

	@Test
	public void constructorWhenNameIsNullThenFallsbackToSubAttribute() {
		OAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(null, this.attributes,
				this.authorities);
		assertThat(principal.getName()).isEqualTo(this.attributes.get("sub"));
	}

	@Test
	public void getNameWhenInConstructorThenReturns() {
		OAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal("other-subject",
				this.attributes, this.authorities);
		assertThat(principal.getName()).isEqualTo("other-subject");
	}

	@Test
	public void getAttributeWhenGivenKeyThenReturnsValue() {
		OAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(this.attributes,
				this.authorities);
		assertThat((String) principal.getAttribute("sub")).isEqualTo("test-subject");
	}

}

package org.springframework.security.oauth2.core.user;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.SerializationUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link DefaultOAuth2User}.
 *
 * @author Vedran Pavic
 * @author Joe Grandja
 */
public class DefaultOAuth2UserTests {

	private static final SimpleGrantedAuthority AUTHORITY = new SimpleGrantedAuthority("ROLE_USER");

	private static final Set<GrantedAuthority> AUTHORITIES = Collections.singleton(AUTHORITY);

	private static final String ATTRIBUTE_NAME_KEY = "username";

	private static final String USERNAME = "test";

	private static final Map<String, Object> ATTRIBUTES = Collections.singletonMap(ATTRIBUTE_NAME_KEY, USERNAME);

	@Test
	public void constructorWhenAuthoritiesIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultOAuth2User(null, ATTRIBUTES, ATTRIBUTE_NAME_KEY));
	}

	@Test
	public void constructorWhenAuthoritiesIsEmptyThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultOAuth2User(Collections.emptySet(), ATTRIBUTES, ATTRIBUTE_NAME_KEY));
	}

	@Test
	public void constructorWhenAttributesIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultOAuth2User(AUTHORITIES, null, ATTRIBUTE_NAME_KEY));
	}

	@Test
	public void constructorWhenAttributesIsEmptyThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultOAuth2User(AUTHORITIES, Collections.emptyMap(), ATTRIBUTE_NAME_KEY));
	}

	@Test
	public void constructorWhenNameAttributeKeyIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new DefaultOAuth2User(AUTHORITIES, ATTRIBUTES, null));
	}

	@Test
	public void constructorWhenNameAttributeKeyIsInvalidThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultOAuth2User(AUTHORITIES, ATTRIBUTES, "invalid"));
	}

	@Test
	public void constructorWhenAllParametersProvidedAndValidThenCreated() {
		DefaultOAuth2User user = new DefaultOAuth2User(AUTHORITIES, ATTRIBUTES, ATTRIBUTE_NAME_KEY);
		assertThat(user.getName()).isEqualTo(USERNAME);
		assertThat(user.getAuthorities()).hasSize(1);
		assertThat(user.getAuthorities().iterator().next()).isEqualTo(AUTHORITY);
		assertThat(user.getAttributes()).containsOnlyKeys(ATTRIBUTE_NAME_KEY);
	}

	// gh-4917
	@Test
	public void constructorWhenCreatedThenIsSerializable() {
		DefaultOAuth2User user = new DefaultOAuth2User(AUTHORITIES, ATTRIBUTES, ATTRIBUTE_NAME_KEY);
		SerializationUtils.serialize(user);
	}

}

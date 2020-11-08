package org.springframework.security.authentication.anonymous;

import org.junit.Test;

import org.springframework.security.authentication.AnonymousAuthenticationProvider;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link AnonymousAuthenticationProvider}.
 *
 * @author Ben Alex
 */
public class AnonymousAuthenticationProviderTests {

	@Test
	public void testDetectsAnInvalidKey() {
		AnonymousAuthenticationProvider aap = new AnonymousAuthenticationProvider("qwerty");
		AnonymousAuthenticationToken token = new AnonymousAuthenticationToken("WRONG_KEY", "Test",
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"));
		assertThatExceptionOfType(BadCredentialsException.class).isThrownBy(() -> aap.authenticate(token));
	}

	@Test
	public void testDetectsMissingKey() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AnonymousAuthenticationProvider(null));
	}

	@Test
	public void testGettersSetters() {
		AnonymousAuthenticationProvider aap = new AnonymousAuthenticationProvider("qwerty");
		assertThat(aap.getKey()).isEqualTo("qwerty");
	}

	@Test
	public void testIgnoresClassesItDoesNotSupport() {
		AnonymousAuthenticationProvider aap = new AnonymousAuthenticationProvider("qwerty");
		TestingAuthenticationToken token = new TestingAuthenticationToken("user", "password", "ROLE_A");
		assertThat(aap.supports(TestingAuthenticationToken.class)).isFalse();
		// Try it anyway
		assertThat(aap.authenticate(token)).isNull();
	}

	@Test
	public void testNormalOperation() {
		AnonymousAuthenticationProvider aap = new AnonymousAuthenticationProvider("qwerty");
		AnonymousAuthenticationToken token = new AnonymousAuthenticationToken("qwerty", "Test",
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"));
		Authentication result = aap.authenticate(token);
		assertThat(token).isEqualTo(result);
	}

	@Test
	public void testSupports() {
		AnonymousAuthenticationProvider aap = new AnonymousAuthenticationProvider("qwerty");
		assertThat(aap.supports(AnonymousAuthenticationToken.class)).isTrue();
		assertThat(aap.supports(TestingAuthenticationToken.class)).isFalse();
	}

}

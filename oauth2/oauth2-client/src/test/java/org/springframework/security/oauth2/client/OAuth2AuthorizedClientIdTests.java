package org.springframework.security.oauth2.client;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link OAuth2AuthorizedClientId}.
 *
 * @author Vedran Pavic
 */
public class OAuth2AuthorizedClientIdTests {

	@Test
	public void constructorWhenRegistrationIdNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new OAuth2AuthorizedClientId(null, "test-principal"))
				.withMessage("clientRegistrationId cannot be empty");
	}

	@Test
	public void constructorWhenPrincipalNameNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new OAuth2AuthorizedClientId("test-client", null))
				.withMessage("principalName cannot be empty");
	}

	@Test
	public void equalsWhenSameRegistrationIdAndPrincipalThenShouldReturnTrue() {
		OAuth2AuthorizedClientId id1 = new OAuth2AuthorizedClientId("test-client", "test-principal");
		OAuth2AuthorizedClientId id2 = new OAuth2AuthorizedClientId("test-client", "test-principal");
		assertThat(id1.equals(id2)).isTrue();
	}

	@Test
	public void equalsWhenDifferentRegistrationIdAndSamePrincipalThenShouldReturnFalse() {
		OAuth2AuthorizedClientId id1 = new OAuth2AuthorizedClientId("test-client1", "test-principal");
		OAuth2AuthorizedClientId id2 = new OAuth2AuthorizedClientId("test-client2", "test-principal");
		assertThat(id1.equals(id2)).isFalse();
	}

	@Test
	public void equalsWhenSameRegistrationIdAndDifferentPrincipalThenShouldReturnFalse() {
		OAuth2AuthorizedClientId id1 = new OAuth2AuthorizedClientId("test-client", "test-principal1");
		OAuth2AuthorizedClientId id2 = new OAuth2AuthorizedClientId("test-client", "test-principal2");
		assertThat(id1.equals(id2)).isFalse();
	}

	@Test
	public void hashCodeWhenSameRegistrationIdAndPrincipalThenShouldReturnSame() {
		OAuth2AuthorizedClientId id1 = new OAuth2AuthorizedClientId("test-client", "test-principal");
		OAuth2AuthorizedClientId id2 = new OAuth2AuthorizedClientId("test-client", "test-principal");
		assertThat(id1.hashCode()).isEqualTo(id2.hashCode());
	}

	@Test
	public void hashCodeWhenDifferentRegistrationIdAndSamePrincipalThenShouldNotReturnSame() {
		OAuth2AuthorizedClientId id1 = new OAuth2AuthorizedClientId("test-client1", "test-principal");
		OAuth2AuthorizedClientId id2 = new OAuth2AuthorizedClientId("test-client2", "test-principal");
		assertThat(id1.hashCode()).isNotEqualTo(id2.hashCode());
	}

	@Test
	public void hashCodeWhenSameRegistrationIdAndDifferentPrincipalThenShouldNotReturnSame() {
		OAuth2AuthorizedClientId id1 = new OAuth2AuthorizedClientId("test-client", "test-principal1");
		OAuth2AuthorizedClientId id2 = new OAuth2AuthorizedClientId("test-client", "test-principal2");
		assertThat(id1.hashCode()).isNotEqualTo(id2.hashCode());
	}

}

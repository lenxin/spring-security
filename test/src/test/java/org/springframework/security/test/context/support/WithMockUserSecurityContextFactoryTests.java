package org.springframework.security.test.context.support;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class WithMockUserSecurityContextFactoryTests {

	@Mock
	private WithMockUser withUser;

	private WithMockUserSecurityContextFactory factory;

	@Before
	public void setup() {
		this.factory = new WithMockUserSecurityContextFactory();
	}

	@Test
	public void usernameNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.factory.createSecurityContext(this.withUser));
	}

	@Test
	public void valueDefaultsUsername() {
		given(this.withUser.value()).willReturn("valueUser");
		given(this.withUser.password()).willReturn("password");
		given(this.withUser.roles()).willReturn(new String[] { "USER" });
		given(this.withUser.authorities()).willReturn(new String[] {});
		assertThat(this.factory.createSecurityContext(this.withUser).getAuthentication().getName())
				.isEqualTo(this.withUser.value());
	}

	@Test
	public void usernamePrioritizedOverValue() {
		given(this.withUser.username()).willReturn("customUser");
		given(this.withUser.password()).willReturn("password");
		given(this.withUser.roles()).willReturn(new String[] { "USER" });
		given(this.withUser.authorities()).willReturn(new String[] {});
		assertThat(this.factory.createSecurityContext(this.withUser).getAuthentication().getName())
				.isEqualTo(this.withUser.username());
	}

	@Test
	public void rolesWorks() {
		given(this.withUser.value()).willReturn("valueUser");
		given(this.withUser.password()).willReturn("password");
		given(this.withUser.roles()).willReturn(new String[] { "USER", "CUSTOM" });
		given(this.withUser.authorities()).willReturn(new String[] {});
		assertThat(this.factory.createSecurityContext(this.withUser).getAuthentication().getAuthorities())
				.extracting("authority").containsOnly("ROLE_USER", "ROLE_CUSTOM");
	}

	@Test
	public void authoritiesWorks() {
		given(this.withUser.value()).willReturn("valueUser");
		given(this.withUser.password()).willReturn("password");
		given(this.withUser.roles()).willReturn(new String[] { "USER" });
		given(this.withUser.authorities()).willReturn(new String[] { "USER", "CUSTOM" });
		assertThat(this.factory.createSecurityContext(this.withUser).getAuthentication().getAuthorities())
				.extracting("authority").containsOnly("USER", "CUSTOM");
	}

	@Test
	public void authoritiesAndRolesInvalid() {
		given(this.withUser.value()).willReturn("valueUser");
		given(this.withUser.roles()).willReturn(new String[] { "CUSTOM" });
		given(this.withUser.authorities()).willReturn(new String[] { "USER", "CUSTOM" });
		assertThatIllegalStateException().isThrownBy(() -> this.factory.createSecurityContext(this.withUser));
	}

	@Test
	public void rolesWithRolePrefixFails() {
		given(this.withUser.value()).willReturn("valueUser");
		given(this.withUser.roles()).willReturn(new String[] { "ROLE_FAIL" });
		given(this.withUser.authorities()).willReturn(new String[] {});
		assertThatIllegalArgumentException().isThrownBy(() -> this.factory.createSecurityContext(this.withUser));
	}

}

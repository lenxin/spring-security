package org.springframework.security.access.intercept;

import org.junit.Test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests {@link RunAsUserToken}.
 *
 * @author Ben Alex
 */
public class RunAsUserTokenTests {

	@Test
	public void testAuthenticationSetting() {
		RunAsUserToken token = new RunAsUserToken("my_password", "Test", "Password",
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"), UsernamePasswordAuthenticationToken.class);
		assertThat(token.isAuthenticated()).isTrue();
		token.setAuthenticated(false);
		assertThat(!token.isAuthenticated()).isTrue();
	}

	@Test
	public void testGetters() {
		RunAsUserToken token = new RunAsUserToken("my_password", "Test", "Password",
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"), UsernamePasswordAuthenticationToken.class);
		assertThat("Test").isEqualTo(token.getPrincipal());
		assertThat("Password").isEqualTo(token.getCredentials());
		assertThat("my_password".hashCode()).isEqualTo(token.getKeyHash());
		assertThat(UsernamePasswordAuthenticationToken.class).isEqualTo(token.getOriginalAuthentication());
	}

	@Test
	public void testNoArgConstructorDoesntExist() {
		assertThatExceptionOfType(NoSuchMethodException.class)
				.isThrownBy(() -> RunAsUserToken.class.getDeclaredConstructor((Class[]) null));
	}

	@Test
	public void testToString() {
		RunAsUserToken token = new RunAsUserToken("my_password", "Test", "Password",
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"), UsernamePasswordAuthenticationToken.class);
		assertThat(token.toString()
				.lastIndexOf("Original Class: " + UsernamePasswordAuthenticationToken.class.getName().toString()) != -1)
						.isTrue();
	}

	// SEC-1792
	@Test
	public void testToStringNullOriginalAuthentication() {
		RunAsUserToken token = new RunAsUserToken("my_password", "Test", "Password",
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"), null);
		assertThat(token.toString().lastIndexOf("Original Class: null") != -1).isTrue();
	}

}

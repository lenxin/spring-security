package org.springframework.security.authentication;

import org.junit.Test;

import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link UsernamePasswordAuthenticationToken}.
 *
 * @author Ben Alex
 */
public class UsernamePasswordAuthenticationTokenTests {

	@Test
	public void authenticatedPropertyContractIsSatisfied() {
		UsernamePasswordAuthenticationToken grantedToken = new UsernamePasswordAuthenticationToken("Test", "Password",
				AuthorityUtils.NO_AUTHORITIES);
		// check default given we passed some GrantedAuthorty[]s (well, we passed empty
		// list)
		assertThat(grantedToken.isAuthenticated()).isTrue();
		// check explicit set to untrusted (we can safely go from trusted to untrusted,
		// but not the reverse)
		grantedToken.setAuthenticated(false);
		assertThat(!grantedToken.isAuthenticated()).isTrue();
		// Now let's create a UsernamePasswordAuthenticationToken without any
		// GrantedAuthorty[]s (different constructor)
		UsernamePasswordAuthenticationToken noneGrantedToken = new UsernamePasswordAuthenticationToken("Test",
				"Password");
		assertThat(!noneGrantedToken.isAuthenticated()).isTrue();
		// check we're allowed to still set it to untrusted
		noneGrantedToken.setAuthenticated(false);
		assertThat(!noneGrantedToken.isAuthenticated()).isTrue();
		// check denied changing it to trusted
		assertThatIllegalArgumentException().isThrownBy(() -> noneGrantedToken.setAuthenticated(true));
	}

	@Test
	public void gettersReturnCorrectData() {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("Test", "Password",
				AuthorityUtils.createAuthorityList("ROLE_ONE", "ROLE_TWO"));
		assertThat(token.getPrincipal()).isEqualTo("Test");
		assertThat(token.getCredentials()).isEqualTo("Password");
		assertThat(AuthorityUtils.authorityListToSet(token.getAuthorities())).contains("ROLE_ONE");
		assertThat(AuthorityUtils.authorityListToSet(token.getAuthorities())).contains("ROLE_TWO");
	}

	@Test
	public void testNoArgConstructorDoesntExist() throws Exception {
		Class<?> clazz = UsernamePasswordAuthenticationToken.class;
		assertThatExceptionOfType(NoSuchMethodException.class)
				.isThrownBy(() -> clazz.getDeclaredConstructor((Class[]) null));
	}

}

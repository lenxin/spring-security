package org.springframework.security.core.userdetails;

import org.junit.Test;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author TSARDD
 * @since 18-okt-2007
 */
@SuppressWarnings("unchecked")
public class UserDetailsByNameServiceWrapperTests {

	@Test
	public final void testAfterPropertiesSet() {
		UserDetailsByNameServiceWrapper svc = new UserDetailsByNameServiceWrapper();
		assertThatIllegalArgumentException().isThrownBy(svc::afterPropertiesSet);
	}

	@Test
	public final void testGetUserDetails() throws Exception {
		UserDetailsByNameServiceWrapper svc = new UserDetailsByNameServiceWrapper();
		final User user = new User("dummy", "dummy", true, true, true, true, AuthorityUtils.NO_AUTHORITIES);
		svc.setUserDetailsService((name) -> {
			if (user != null && user.getUsername().equals(name)) {
				return user;
			}
			else {
				return null;
			}
		});
		svc.afterPropertiesSet();
		UserDetails result1 = svc.loadUserDetails(new TestingAuthenticationToken("dummy", "dummy"));
		assertThat(result1).as("Result doesn't match original user").isEqualTo(user);
		UserDetails result2 = svc.loadUserDetails(new TestingAuthenticationToken("dummy2", "dummy"));
		assertThat(result2).as("Result should have been null").isNull();
	}

}

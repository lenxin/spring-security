package org.springframework.security.core.authority;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Tests {@link SimpleGrantedAuthority}.
 *
 * @author Ben Alex
 */
public class SimpleGrantedAuthorityTests {

	@Test
	public void equalsBehavesAsExpected() {
		SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("TEST");
		assertThat(auth1).isEqualTo(auth1);
		assertThat(new SimpleGrantedAuthority("TEST")).isEqualTo(auth1);
		assertThat(auth1.equals("TEST")).isFalse();
		SimpleGrantedAuthority auth3 = new SimpleGrantedAuthority("NOT_EQUAL");
		assertThat(!auth1.equals(auth3)).isTrue();
		assertThat(auth1.equals(mock(GrantedAuthority.class))).isFalse();
		assertThat(auth1.equals(222)).isFalse();
	}

	@Test
	public void toStringReturnsAuthorityValue() {
		SimpleGrantedAuthority auth = new SimpleGrantedAuthority("TEST");
		assertThat(auth.toString()).isEqualTo("TEST");
	}

}

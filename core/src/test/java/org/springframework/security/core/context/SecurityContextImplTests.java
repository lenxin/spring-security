package org.springframework.security.core.context;

import org.junit.Test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link SecurityContextImpl}.
 *
 * @author Ben Alex
 */
public class SecurityContextImplTests {

	@Test
	public void testEmptyObjectsAreEquals() {
		SecurityContextImpl obj1 = new SecurityContextImpl();
		SecurityContextImpl obj2 = new SecurityContextImpl();
		assertThat(obj1.equals(obj2)).isTrue();
	}

	@Test
	public void testSecurityContextCorrectOperation() {
		SecurityContext context = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken("rod", "koala");
		context.setAuthentication(auth);
		assertThat(context.getAuthentication()).isEqualTo(auth);
		assertThat(context.toString().lastIndexOf("rod") != -1).isTrue();
	}

}

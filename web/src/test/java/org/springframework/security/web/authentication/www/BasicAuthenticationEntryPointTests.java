package org.springframework.security.web.authentication.www;

import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.DisabledException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link BasicAuthenticationEntryPoint}.
 *
 * @author Ben Alex
 */
public class BasicAuthenticationEntryPointTests {

	@Test
	public void testDetectsMissingRealmName() {
		BasicAuthenticationEntryPoint ep = new BasicAuthenticationEntryPoint();
		assertThatIllegalArgumentException().isThrownBy(ep::afterPropertiesSet)
				.withMessage("realmName must be specified");
	}

	@Test
	public void testGettersSetters() {
		BasicAuthenticationEntryPoint ep = new BasicAuthenticationEntryPoint();
		ep.setRealmName("realm");
		assertThat(ep.getRealmName()).isEqualTo("realm");
	}

	@Test
	public void testNormalOperation() throws Exception {
		BasicAuthenticationEntryPoint ep = new BasicAuthenticationEntryPoint();
		ep.setRealmName("hello");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/some_path");
		MockHttpServletResponse response = new MockHttpServletResponse();
		// ep.afterPropertiesSet();
		ep.commence(request, response, new DisabledException("These are the jokes kid"));
		assertThat(response.getStatus()).isEqualTo(401);
		assertThat(response.getErrorMessage()).isEqualTo(HttpStatus.UNAUTHORIZED.getReasonPhrase());
		assertThat(response.getHeader("WWW-Authenticate")).isEqualTo("Basic realm=\"hello\"");
	}

}

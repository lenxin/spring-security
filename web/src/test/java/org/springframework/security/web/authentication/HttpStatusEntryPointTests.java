package org.springframework.security.web.authentication;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 * @since 4.0
 */
public class HttpStatusEntryPointTests {

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	AuthenticationException authException;

	HttpStatusEntryPoint entryPoint;

	@SuppressWarnings("serial")
	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
		this.authException = new AuthenticationException("") {
		};
		this.entryPoint = new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void constructorNullStatus() {
		assertThatIllegalArgumentException().isThrownBy(() -> new HttpStatusEntryPoint(null));
	}

	@Test
	public void unauthorized() throws Exception {
		this.entryPoint.commence(this.request, this.response, this.authException);
		assertThat(this.response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
	}

}

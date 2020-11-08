package org.springframework.security.web.authentication;

import java.util.HashMap;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Luke Taylor
 */
public class ExceptionMappingAuthenticationFailureHandlerTests {

	@Test
	public void defaultTargetUrlIsUsedIfNoMappingExists() throws Exception {
		ExceptionMappingAuthenticationFailureHandler fh = new ExceptionMappingAuthenticationFailureHandler();
		fh.setDefaultFailureUrl("/failed");
		MockHttpServletResponse response = new MockHttpServletResponse();
		fh.onAuthenticationFailure(new MockHttpServletRequest(), response, new BadCredentialsException(""));
		assertThat(response.getRedirectedUrl()).isEqualTo("/failed");
	}

	@Test
	public void exceptionMapIsUsedIfMappingExists() throws Exception {
		ExceptionMappingAuthenticationFailureHandler fh = new ExceptionMappingAuthenticationFailureHandler();
		HashMap<String, String> mapping = new HashMap<>();
		mapping.put("org.springframework.security.authentication.BadCredentialsException", "/badcreds");
		fh.setExceptionMappings(mapping);
		fh.setDefaultFailureUrl("/failed");
		MockHttpServletResponse response = new MockHttpServletResponse();
		fh.onAuthenticationFailure(new MockHttpServletRequest(), response, new BadCredentialsException(""));
		assertThat(response.getRedirectedUrl()).isEqualTo("/badcreds");
	}

}

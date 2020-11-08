package org.springframework.security.config.http;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Transient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * @author Josh Cummings
 */
public class SessionManagementConfigTransientAuthenticationTests {

	private static final String CONFIG_LOCATION_PREFIX = "classpath:org/springframework/security/config/http/SessionManagementConfigTransientAuthenticationTests";

	@Autowired
	MockMvc mvc;

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Test
	public void postWhenTransientAuthenticationThenNoSessionCreated() throws Exception {
		this.spring.configLocations(this.xml("WithTransientAuthentication")).autowire();
		MvcResult result = this.mvc.perform(post("/login")).andReturn();
		assertThat(result.getRequest().getSession(false)).isNull();
	}

	@Test
	public void postWhenTransientAuthenticationThenAlwaysSessionOverrides() throws Exception {
		this.spring.configLocations(this.xml("CreateSessionAlwaysWithTransientAuthentication")).autowire();
		MvcResult result = this.mvc.perform(post("/login")).andReturn();
		assertThat(result.getRequest().getSession(false)).isNotNull();
	}

	private String xml(String configName) {
		return CONFIG_LOCATION_PREFIX + "-" + configName + ".xml";
	}

	static class TransientAuthenticationProvider implements AuthenticationProvider {

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
			return new SomeTransientAuthentication();
		}

		@Override
		public boolean supports(Class<?> authentication) {
			return true;
		}

	}

	@Transient
	static class SomeTransientAuthentication extends AbstractAuthenticationToken {

		SomeTransientAuthentication() {
			super(null);
		}

		@Override
		public Object getCredentials() {
			return null;
		}

		@Override
		public Object getPrincipal() {
			return null;
		}

	}

}

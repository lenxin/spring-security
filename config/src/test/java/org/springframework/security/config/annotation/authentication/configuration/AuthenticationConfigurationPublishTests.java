package org.springframework.security.config.annotation.authentication.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.MockEventListener;
import org.springframework.security.config.users.AuthenticationTestConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthenticationConfigurationPublishTests {

	@Autowired
	MockEventListener<AuthenticationSuccessEvent> listener;

	AuthenticationManager authenticationManager;

	// gh-4940
	@Test
	public void authenticationEventPublisherBeanUsedByDefault() {
		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("user", "password"));
		assertThat(this.listener.getEvents()).hasSize(1);
	}

	@Autowired
	public void setAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
	}

	@EnableGlobalAuthentication
	@Import(AuthenticationTestConfiguration.class)
	static class Config {

		@Bean
		AuthenticationEventPublisher publisher() {
			return new DefaultAuthenticationEventPublisher();
		}

		@Bean
		MockEventListener<AuthenticationSuccessEvent> eventListener() {
			return new MockEventListener<AuthenticationSuccessEvent>() {
			};
		}

	}

}

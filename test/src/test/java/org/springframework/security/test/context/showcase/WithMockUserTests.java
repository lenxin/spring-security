package org.springframework.security.test.context.showcase;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.test.context.showcase.service.HelloMessageService;
import org.springframework.security.test.context.showcase.service.MessageService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Rob Winch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WithMockUserTests.Config.class)
public class WithMockUserTests {

	@Autowired
	private MessageService messageService;

	@Test
	public void getMessageUnauthenticated() {
		assertThatExceptionOfType(AuthenticationCredentialsNotFoundException.class)
				.isThrownBy(() -> this.messageService.getMessage());
	}

	@Test
	@WithMockUser
	public void getMessageWithMockUser() {
		String message = this.messageService.getMessage();
		assertThat(message).contains("user");
	}

	@Test
	@WithMockUser("customUsername")
	public void getMessageWithMockUserCustomUsername() {
		String message = this.messageService.getMessage();
		assertThat(message).contains("customUsername");
	}

	@Test
	@WithMockUser(username = "admin", roles = { "USER", "ADMIN" })
	public void getMessageWithMockUserCustomUser() {
		String message = this.messageService.getMessage();
		assertThat(message).contains("admin").contains("ROLE_USER").contains("ROLE_ADMIN");
	}

	@Test
	@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
	public void getMessageWithMockUserCustomAuthorities() {
		String message = this.messageService.getMessage();
		assertThat(message).contains("admin").contains("ADMIN").contains("USER").doesNotContain("ROLE_");
	}

	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@ComponentScan(basePackageClasses = HelloMessageService.class)
	static class Config {

		@Autowired
		void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			auth
				.inMemoryAuthentication()
					.withUser("user").password("password").roles("USER");
			// @formatter:on
		}

	}

}

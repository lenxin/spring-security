package org.springframework.security.test.context.showcase;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.showcase.service.HelloMessageService;
import org.springframework.security.test.context.showcase.service.MessageService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Rob Winch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WithUserDetailsTests.Config.class)
public class WithUserDetailsTests {

	@Autowired
	private MessageService messageService;

	@Test
	public void getMessageUnauthenticated() {
		assertThatExceptionOfType(AuthenticationCredentialsNotFoundException.class)
				.isThrownBy(() -> this.messageService.getMessage());
	}

	@Test
	@WithUserDetails
	public void getMessageWithUserDetails() {
		String message = this.messageService.getMessage();
		assertThat(message).contains("user");
		assertThat(getPrincipal()).isInstanceOf(CustomUserDetails.class);
	}

	@Test
	@WithUserDetails("customUsername")
	public void getMessageWithUserDetailsCustomUsername() {
		String message = this.messageService.getMessage();
		assertThat(message).contains("customUsername");
		assertThat(getPrincipal()).isInstanceOf(CustomUserDetails.class);
	}

	@Test
	@WithUserDetails(value = "customUsername", userDetailsServiceBeanName = "myUserDetailsService")
	public void getMessageWithUserDetailsServiceBeanName() {
		String message = this.messageService.getMessage();
		assertThat(message).contains("customUsername");
		assertThat(getPrincipal()).isInstanceOf(CustomUserDetails.class);
	}

	private Object getPrincipal() {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@EnableGlobalMethodSecurity(prePostEnabled = true)
	@ComponentScan(basePackageClasses = HelloMessageService.class)
	static class Config {

		@Autowired
		void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(myUserDetailsService());
		}

		@Bean
		UserDetailsService myUserDetailsService() {
			return new CustomUserDetailsService();
		}

	}

	static class CustomUserDetailsService implements UserDetailsService {

		@Override
		public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
			return new CustomUserDetails("name", username);
		}

	}

}

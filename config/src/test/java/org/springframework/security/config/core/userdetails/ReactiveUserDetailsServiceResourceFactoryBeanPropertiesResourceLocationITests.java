package org.springframework.security.config.core.userdetails;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringRunner.class)
public class ReactiveUserDetailsServiceResourceFactoryBeanPropertiesResourceLocationITests {

	@Autowired
	ReactiveUserDetailsService users;

	@Test
	public void loadUserByUsernameWhenUserFoundThenNotNull() {
		assertThat(this.users.findByUsername("user").block()).isNotNull();
	}

	@Configuration
	static class Config {

		@Bean
		ReactiveUserDetailsServiceResourceFactoryBean userDetailsService() {
			return ReactiveUserDetailsServiceResourceFactoryBean.fromResourceLocation("classpath:users.properties");
		}

	}

}

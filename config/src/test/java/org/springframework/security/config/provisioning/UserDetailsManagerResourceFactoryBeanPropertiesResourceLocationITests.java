package org.springframework.security.config.provisioning;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringRunner.class)
public class UserDetailsManagerResourceFactoryBeanPropertiesResourceLocationITests {

	@Autowired
	UserDetailsManager users;

	@Test
	public void loadUserByUsernameWhenUserFoundThenNotNull() {
		assertThat(this.users.loadUserByUsername("user")).isNotNull();
	}

	@Configuration
	static class Config {

		@Bean
		UserDetailsManagerResourceFactoryBean userDetailsService() {
			return UserDetailsManagerResourceFactoryBean.fromResourceLocation("classpath:users.properties");
		}

	}

}

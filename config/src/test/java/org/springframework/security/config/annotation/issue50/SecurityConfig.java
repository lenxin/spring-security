package org.springframework.security.config.annotation.issue50;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.issue50.domain.User;
import org.springframework.security.config.annotation.issue50.repo.UserRepository;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

/**
 * @author Rob Winch
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserRepository myUserRepository;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		// @formatter:off
		auth
			.authenticationProvider(authenticationProvider());
		// @formatter:on
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers("/*").permitAll();
		// @formatter:on
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		Assert.notNull(this.myUserRepository);
		return new AuthenticationProvider() {
			@Override
			public boolean supports(Class<?> authentication) {
				return true;
			}

			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				Object principal = authentication.getPrincipal();
				String username = String.valueOf(principal);
				User user = SecurityConfig.this.myUserRepository.findByUsername(username);
				if (user == null) {
					throw new UsernameNotFoundException("No user for principal " + principal);
				}
				if (!authentication.getCredentials().equals(user.getPassword())) {
					throw new BadCredentialsException("Invalid password");
				}
				return new TestingAuthenticationToken(principal, null, "ROLE_USER");
			}
		};
	}

}

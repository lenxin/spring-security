package sample;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Rob Winch
 * @since 5.0
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

	@Bean
	SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
		return http
			// Demonstrate that method security works
			// Best practice to use both for defense in depth
			.authorizeExchange((exchanges) -> exchanges
				.anyExchange().permitAll()
			)
			.httpBasic(withDefaults())
			.build();
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
		UserDetails rob = userBuilder.username("rob").password("rob").roles("USER").build();
		UserDetails admin = userBuilder.username("admin").password("admin").roles("USER", "ADMIN").build();
		return new MapReactiveUserDetailsService(rob, admin);
	}

}

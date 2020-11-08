package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Alexey Nesterov
 * @since 5.2
 */
@SpringBootApplication
public class WebfluxX509Application {

	@Bean
	public ReactiveUserDetailsService reactiveUserDetailsService() {
		return new MapReactiveUserDetailsService(
			User.withUsername("client").password("").authorities("ROLE_USER").build()
		);
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		// @formatter:off
		http
			.x509(withDefaults())
			.authorizeExchange((exchanges) ->
				exchanges
					.anyExchange().authenticated()
			);
		// @formatter:on

		return http.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebfluxX509Application.class);
	}
}

package sample;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Rob Winch
 * @since 5.1
 */
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
			.authorizeExchange((exchanges) ->
				exchanges
					.pathMatchers(HttpMethod.GET, "/message/**").hasAuthority("SCOPE_message:read")
					.pathMatchers(HttpMethod.POST, "/message/**").hasAuthority("SCOPE_message:write")
					.anyExchange().authenticated()
			)
			.oauth2ResourceServer((oauth2ResourceServer) ->
				oauth2ResourceServer
					.jwt(withDefaults())
			);
		return http.build();
	}
}

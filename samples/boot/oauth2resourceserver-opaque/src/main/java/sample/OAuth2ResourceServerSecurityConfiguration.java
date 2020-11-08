package sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Josh Cummings
 */
@EnableWebSecurity
public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}") String introspectionUri;
	@Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}") String clientId;
	@Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}") String clientSecret;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests((authorizeRequests) ->
				authorizeRequests
					.antMatchers(HttpMethod.GET, "/message/**").hasAuthority("SCOPE_message:read")
					.antMatchers(HttpMethod.POST, "/message/**").hasAuthority("SCOPE_message:write")
					.anyRequest().authenticated()
			)
			.oauth2ResourceServer((oauth2ResourceServer) ->
				oauth2ResourceServer
					.opaqueToken((opaqueToken) ->
						opaqueToken
							.introspectionUri(this.introspectionUri)
							.introspectionClientCredentials(this.clientId, this.clientSecret)
					)
			);
		// @formatter:on
	}
}

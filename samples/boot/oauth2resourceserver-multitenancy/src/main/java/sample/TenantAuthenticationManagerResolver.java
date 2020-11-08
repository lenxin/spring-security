package sample;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationManagerResolver;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtBearerTokenAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.OpaqueTokenAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.stereotype.Component;

@Component
public class TenantAuthenticationManagerResolver
		implements AuthenticationManagerResolver<HttpServletRequest> {

	private AuthenticationManager jwt;
	private AuthenticationManager opaqueToken;

	public TenantAuthenticationManagerResolver(
			JwtDecoder jwtDecoder, OpaqueTokenIntrospector opaqueTokenIntrospector) {

		JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoder);
		jwtAuthenticationProvider.setJwtAuthenticationConverter(new JwtBearerTokenAuthenticationConverter());
		this.jwt = new ProviderManager(jwtAuthenticationProvider);
		this.opaqueToken = new ProviderManager(new OpaqueTokenAuthenticationProvider(opaqueTokenIntrospector));
	}

	@Override
	public AuthenticationManager resolve(HttpServletRequest request) {
		String tenant = request.getHeader("tenant");
		if ("one".equals(tenant)) {
			return this.jwt;
		}
		if ("two".equals(tenant)) {
			return this.opaqueToken;
		}
		throw new IllegalArgumentException("unknown tenant");
	}
}

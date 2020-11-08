package org.springframework.security.oauth2.server.resource.authentication;

import java.util.Collection;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

/**
 * A {@link Converter} that takes a {@link Jwt} and converts it into a
 * {@link BearerTokenAuthentication}.
 *
 * In the process, it will attempt to parse either the "scope" or "scp" attribute,
 * whichever it finds first.
 *
 * It's not intended that this implementation be configured since it is simply an adapter.
 * If you are using, for example, a custom {@link JwtGrantedAuthoritiesConverter}, then
 * it's recommended that you simply create your own {@link Converter} that delegates to
 * your custom {@link JwtGrantedAuthoritiesConverter} and instantiates the appropriate
 * {@link BearerTokenAuthentication}.
 *
 * @author Josh Cummings
 * @since 5.2
 */
public final class JwtBearerTokenAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, jwt.getTokenValue(),
				jwt.getIssuedAt(), jwt.getExpiresAt());
		Map<String, Object> attributes = jwt.getClaims();
		AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);
		Collection<GrantedAuthority> authorities = token.getAuthorities();
		OAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(attributes, authorities);
		return new BearerTokenAuthentication(principal, accessToken, authorities);
	}

}

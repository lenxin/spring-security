package org.springframework.security.oauth2.server.resource.authentication;

import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import reactor.core.publisher.Mono;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.TestOAuth2AuthenticatedPrincipals;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionAuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionClaimNames;
import org.springframework.security.oauth2.server.resource.introspection.OAuth2IntrospectionException;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenIntrospector;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link OpaqueTokenReactiveAuthenticationManager}
 *
 * @author Josh Cummings
 */
public class OpaqueTokenReactiveAuthenticationManagerTests {

	@Test
	public void authenticateWhenActiveTokenThenOk() throws Exception {
		OAuth2AuthenticatedPrincipal authority = TestOAuth2AuthenticatedPrincipals
				.active((attributes) -> attributes.put("extension_field", "twenty-seven"));
		ReactiveOpaqueTokenIntrospector introspector = mock(ReactiveOpaqueTokenIntrospector.class);
		given(introspector.introspect(any())).willReturn(Mono.just(authority));
		OpaqueTokenReactiveAuthenticationManager provider = new OpaqueTokenReactiveAuthenticationManager(introspector);
		Authentication result = provider.authenticate(new BearerTokenAuthenticationToken("token")).block();
		assertThat(result.getPrincipal()).isInstanceOf(OAuth2IntrospectionAuthenticatedPrincipal.class);
		Map<String, Object> attributes = ((OAuth2AuthenticatedPrincipal) result.getPrincipal()).getAttributes();
		// @formatter:off
		assertThat(attributes)
				.isNotNull()
				.containsEntry(OAuth2IntrospectionClaimNames.ACTIVE, true)
				.containsEntry(OAuth2IntrospectionClaimNames.AUDIENCE,
						Arrays.asList("https://protected.example.net/resource"))
				.containsEntry(OAuth2IntrospectionClaimNames.CLIENT_ID, "l238j323ds-23ij4")
				.containsEntry(OAuth2IntrospectionClaimNames.EXPIRES_AT, Instant.ofEpochSecond(1419356238))
				.containsEntry(OAuth2IntrospectionClaimNames.ISSUER, new URL("https://server.example.com/"))
				.containsEntry(OAuth2IntrospectionClaimNames.NOT_BEFORE, Instant.ofEpochSecond(29348723984L))
				.containsEntry(OAuth2IntrospectionClaimNames.SCOPE, Arrays.asList("read", "write", "dolphin"))
				.containsEntry(OAuth2IntrospectionClaimNames.SUBJECT, "Z5O3upPC88QrAjx00dis")
				.containsEntry(OAuth2IntrospectionClaimNames.USERNAME, "jdoe")
				.containsEntry("extension_field", "twenty-seven");
		assertThat(result.getAuthorities())
				.extracting("authority")
				.containsExactly("SCOPE_read", "SCOPE_write",
				"SCOPE_dolphin");
		// @formatter:on
	}

	@Test
	public void authenticateWhenMissingScopeAttributeThenNoAuthorities() {
		OAuth2AuthenticatedPrincipal authority = new OAuth2IntrospectionAuthenticatedPrincipal(
				Collections.singletonMap("claim", "value"), null);
		ReactiveOpaqueTokenIntrospector introspector = mock(ReactiveOpaqueTokenIntrospector.class);
		given(introspector.introspect(any())).willReturn(Mono.just(authority));
		OpaqueTokenReactiveAuthenticationManager provider = new OpaqueTokenReactiveAuthenticationManager(introspector);
		Authentication result = provider.authenticate(new BearerTokenAuthenticationToken("token")).block();
		assertThat(result.getPrincipal()).isInstanceOf(OAuth2IntrospectionAuthenticatedPrincipal.class);
		Map<String, Object> attributes = ((OAuth2AuthenticatedPrincipal) result.getPrincipal()).getAttributes();
		assertThat(attributes).isNotNull().doesNotContainKey(OAuth2IntrospectionClaimNames.SCOPE);
		assertThat(result.getAuthorities()).isEmpty();
	}

	@Test
	public void authenticateWhenIntrospectionEndpointThrowsExceptionThenInvalidToken() {
		ReactiveOpaqueTokenIntrospector introspector = mock(ReactiveOpaqueTokenIntrospector.class);
		given(introspector.introspect(any()))
				.willReturn(Mono.error(new OAuth2IntrospectionException("with \"invalid\" chars")));
		OpaqueTokenReactiveAuthenticationManager provider = new OpaqueTokenReactiveAuthenticationManager(introspector);
		assertThatExceptionOfType(AuthenticationServiceException.class)
				.isThrownBy(() -> provider.authenticate(new BearerTokenAuthenticationToken("token")).block());
	}

	@Test
	public void constructorWhenIntrospectionClientIsNullThenIllegalArgumentException() {
		// @formatter:off
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new OpaqueTokenReactiveAuthenticationManager(null));
		// @formatter:on
	}

}

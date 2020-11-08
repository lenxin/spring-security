package org.springframework.security.oauth2.server.resource;

import org.junit.Test;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.TestJwts;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link DefaultAuthenticationEventPublisher}'s bearer token use cases
 *
 * {@see DefaultAuthenticationEventPublisher}
 */
public class DefaultAuthenticationEventPublisherBearerTokenTests {

	DefaultAuthenticationEventPublisher publisher;

	@Test
	public void publishAuthenticationFailureWhenInvalidBearerTokenExceptionThenMaps() {
		ApplicationEventPublisher appPublisher = mock(ApplicationEventPublisher.class);
		Authentication authentication = new JwtAuthenticationToken(TestJwts.jwt().build());
		Exception cause = new Exception();
		this.publisher = new DefaultAuthenticationEventPublisher(appPublisher);
		this.publisher.publishAuthenticationFailure(new InvalidBearerTokenException("invalid"), authentication);
		this.publisher.publishAuthenticationFailure(new InvalidBearerTokenException("invalid", cause), authentication);
		verify(appPublisher, times(2)).publishEvent(isA(AuthenticationFailureBadCredentialsEvent.class));
	}

}

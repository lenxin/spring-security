package org.springframework.security.config.http;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AnyRequestMatcher;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Rob Winch
 */
@RunWith(MockitoJUnitRunner.class)
public class DefaultFilterChainValidatorTests {

	private DefaultFilterChainValidator validator;

	private FilterChainProxy fcp;

	@Mock
	private Log logger;

	@Mock
	private DefaultFilterInvocationSecurityMetadataSource metadataSource;

	@Mock
	private AccessDecisionManager accessDecisionManager;

	private FilterSecurityInterceptor fsi;

	@Before
	public void setUp() {
		AnonymousAuthenticationFilter aaf = new AnonymousAuthenticationFilter("anonymous");
		this.fsi = new FilterSecurityInterceptor();
		this.fsi.setAccessDecisionManager(this.accessDecisionManager);
		this.fsi.setSecurityMetadataSource(this.metadataSource);
		AuthenticationEntryPoint authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/login");
		ExceptionTranslationFilter etf = new ExceptionTranslationFilter(authenticationEntryPoint);
		DefaultSecurityFilterChain securityChain = new DefaultSecurityFilterChain(AnyRequestMatcher.INSTANCE, aaf, etf,
				this.fsi);
		this.fcp = new FilterChainProxy(securityChain);
		this.validator = new DefaultFilterChainValidator();
		ReflectionTestUtils.setField(this.validator, "logger", this.logger);
	}

	// SEC-1878
	@SuppressWarnings("unchecked")
	@Test
	public void validateCheckLoginPageIsntProtectedThrowsIllegalArgumentException() {
		IllegalArgumentException toBeThrown = new IllegalArgumentException("failed to eval expression");
		willThrow(toBeThrown).given(this.accessDecisionManager).decide(any(Authentication.class), anyObject(),
				any(Collection.class));
		this.validator.validate(this.fcp);
		verify(this.logger).info(
				"Unable to check access to the login page to determine if anonymous access is allowed. This might be an error, but can happen under normal circumstances.",
				toBeThrown);
	}

	// SEC-1957
	@Test
	public void validateCustomMetadataSource() {
		FilterInvocationSecurityMetadataSource customMetaDataSource = mock(
				FilterInvocationSecurityMetadataSource.class);
		this.fsi.setSecurityMetadataSource(customMetaDataSource);
		this.validator.validate(this.fcp);
		verify(customMetaDataSource).getAttributes(any());
	}

}

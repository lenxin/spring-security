package org.springframework.security.web.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.TestingAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Rob Winch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CsrfAuthenticationStrategyTests {

	@Mock
	private CsrfTokenRepository csrfTokenRepository;

	private MockHttpServletRequest request;

	private MockHttpServletResponse response;

	private CsrfAuthenticationStrategy strategy;

	private CsrfToken existingToken;

	private CsrfToken generatedToken;

	@Before
	public void setup() {
		this.response = new MockHttpServletResponse();
		this.request = new MockHttpServletRequest();
		this.request.setAttribute(HttpServletResponse.class.getName(), this.response);
		this.strategy = new CsrfAuthenticationStrategy(this.csrfTokenRepository);
		this.existingToken = new DefaultCsrfToken("_csrf", "_csrf", "1");
		this.generatedToken = new DefaultCsrfToken("_csrf", "_csrf", "2");
	}

	@Test
	public void constructorNullCsrfTokenRepository() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CsrfAuthenticationStrategy(null));
	}

	@Test
	public void logoutRemovesCsrfTokenAndSavesNew() {
		given(this.csrfTokenRepository.loadToken(this.request)).willReturn(this.existingToken);
		given(this.csrfTokenRepository.generateToken(this.request)).willReturn(this.generatedToken);
		this.strategy.onAuthentication(new TestingAuthenticationToken("user", "password", "ROLE_USER"), this.request,
				this.response);
		verify(this.csrfTokenRepository).saveToken(null, this.request, this.response);
		verify(this.csrfTokenRepository).saveToken(eq(this.generatedToken), any(HttpServletRequest.class),
				any(HttpServletResponse.class));
		// SEC-2404, SEC-2832
		CsrfToken tokenInRequest = (CsrfToken) this.request.getAttribute(CsrfToken.class.getName());
		assertThat(tokenInRequest.getToken()).isSameAs(this.generatedToken.getToken());
		assertThat(tokenInRequest.getHeaderName()).isSameAs(this.generatedToken.getHeaderName());
		assertThat(tokenInRequest.getParameterName()).isSameAs(this.generatedToken.getParameterName());
		assertThat(this.request.getAttribute(this.generatedToken.getParameterName())).isSameAs(tokenInRequest);
	}

	// SEC-2872
	@Test
	public void delaySavingCsrf() {
		this.strategy = new CsrfAuthenticationStrategy(new LazyCsrfTokenRepository(this.csrfTokenRepository));
		given(this.csrfTokenRepository.loadToken(this.request)).willReturn(this.existingToken);
		given(this.csrfTokenRepository.generateToken(this.request)).willReturn(this.generatedToken);
		this.strategy.onAuthentication(new TestingAuthenticationToken("user", "password", "ROLE_USER"), this.request,
				this.response);
		verify(this.csrfTokenRepository).saveToken(null, this.request, this.response);
		verify(this.csrfTokenRepository, never()).saveToken(eq(this.generatedToken), any(HttpServletRequest.class),
				any(HttpServletResponse.class));
		CsrfToken tokenInRequest = (CsrfToken) this.request.getAttribute(CsrfToken.class.getName());
		tokenInRequest.getToken();
		verify(this.csrfTokenRepository).saveToken(eq(this.generatedToken), any(HttpServletRequest.class),
				any(HttpServletResponse.class));
	}

	@Test
	public void logoutRemovesNoActionIfNullToken() {
		this.strategy.onAuthentication(new TestingAuthenticationToken("user", "password", "ROLE_USER"), this.request,
				this.response);
		verify(this.csrfTokenRepository, never()).saveToken(any(CsrfToken.class), any(HttpServletRequest.class),
				any(HttpServletResponse.class));
	}

}

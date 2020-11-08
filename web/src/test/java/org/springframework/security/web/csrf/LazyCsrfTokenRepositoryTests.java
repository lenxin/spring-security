package org.springframework.security.web.csrf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Rob Winch
 */
@RunWith(MockitoJUnitRunner.class)
public class LazyCsrfTokenRepositoryTests {

	@Mock
	CsrfTokenRepository delegate;

	@Mock
	HttpServletRequest request;

	@Mock
	HttpServletResponse response;

	@InjectMocks
	LazyCsrfTokenRepository repository;

	DefaultCsrfToken token;

	@Before
	public void setup() {
		this.token = new DefaultCsrfToken("header", "param", "token");
		given(this.delegate.generateToken(this.request)).willReturn(this.token);
		given(this.request.getAttribute(HttpServletResponse.class.getName())).willReturn(this.response);
	}

	@Test
	public void constructNullDelegateThrowsIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new LazyCsrfTokenRepository(null));
	}

	@Test
	public void generateTokenNullResponseAttribute() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.repository.generateToken(mock(HttpServletRequest.class)));
	}

	@Test
	public void generateTokenGetTokenSavesToken() {
		CsrfToken newToken = this.repository.generateToken(this.request);
		newToken.getToken();
		verify(this.delegate).saveToken(this.token, this.request, this.response);
	}

	@Test
	public void saveNonNullDoesNothing() {
		this.repository.saveToken(this.token, this.request, this.response);
		verifyZeroInteractions(this.delegate);
	}

	@Test
	public void saveNullDelegates() {
		this.repository.saveToken(null, this.request, this.response);
		verify(this.delegate).saveToken(null, this.request, this.response);
	}

	@Test
	public void loadTokenDelegates() {
		given(this.delegate.loadToken(this.request)).willReturn(this.token);
		CsrfToken loadToken = this.repository.loadToken(this.request);
		assertThat(loadToken).isSameAs(this.token);
		verify(this.delegate).loadToken(this.request);
	}

}

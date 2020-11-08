package org.springframework.security.web.util.matcher;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;

/**
 * @author Rob Winch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class NegatedRequestMatcherTests {

	@Mock
	private RequestMatcher delegate;

	@Mock
	private HttpServletRequest request;

	private RequestMatcher matcher;

	@Test
	public void constructorNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new NegatedRequestMatcher(null));
	}

	@Test
	public void matchesDelegateFalse() {
		given(this.delegate.matches(this.request)).willReturn(false);
		this.matcher = new NegatedRequestMatcher(this.delegate);
		assertThat(this.matcher.matches(this.request)).isTrue();
	}

	@Test
	public void matchesDelegateTrue() {
		given(this.delegate.matches(this.request)).willReturn(true);
		this.matcher = new NegatedRequestMatcher(this.delegate);
		assertThat(this.matcher.matches(this.request)).isFalse();
	}

}

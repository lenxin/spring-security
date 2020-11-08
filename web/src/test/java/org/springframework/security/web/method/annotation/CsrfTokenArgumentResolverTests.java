package org.springframework.security.web.method.annotation;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CsrfTokenArgumentResolverTests {

	@Mock
	private ModelAndViewContainer mavContainer;

	@Mock
	private WebDataBinderFactory binderFactory;

	private MockHttpServletRequest request;

	private NativeWebRequest webRequest;

	private CsrfToken token;

	private CsrfTokenArgumentResolver resolver;

	@Before
	public void setup() {
		this.token = new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", "secret");
		this.resolver = new CsrfTokenArgumentResolver();
		this.request = new MockHttpServletRequest();
		this.webRequest = new ServletWebRequest(this.request);
	}

	@Test
	public void supportsParameterFalse() {
		assertThat(this.resolver.supportsParameter(noToken())).isFalse();
	}

	@Test
	public void supportsParameterTrue() {
		assertThat(this.resolver.supportsParameter(token())).isTrue();
	}

	@Test
	public void resolveArgumentNotFound() throws Exception {
		assertThat(this.resolver.resolveArgument(token(), this.mavContainer, this.webRequest, this.binderFactory))
				.isNull();
	}

	@Test
	public void resolveArgumentFound() throws Exception {
		this.request.setAttribute(CsrfToken.class.getName(), this.token);
		assertThat(this.resolver.resolveArgument(token(), this.mavContainer, this.webRequest, this.binderFactory))
				.isSameAs(this.token);
	}

	private MethodParameter noToken() {
		return getMethodParameter("noToken", String.class);
	}

	private MethodParameter token() {
		return getMethodParameter("token", CsrfToken.class);
	}

	private MethodParameter getMethodParameter(String methodName, Class<?>... paramTypes) {
		Method method = ReflectionUtils.findMethod(TestController.class, methodName, paramTypes);
		return new MethodParameter(method, 0);
	}

	public static class TestController {

		public void noToken(String user) {
		}

		public void token(CsrfToken token) {
		}

	}

}

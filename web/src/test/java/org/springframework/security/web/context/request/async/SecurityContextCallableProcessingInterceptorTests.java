package org.springframework.security.web.context.request.async;

import java.util.concurrent.Callable;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.NativeWebRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SecurityContextCallableProcessingInterceptorTests {

	@Mock
	private SecurityContext securityContext;

	@Mock
	private Callable<?> callable;

	@Mock
	private NativeWebRequest webRequest;

	@After
	public void clearSecurityContext() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void constructorNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new SecurityContextCallableProcessingInterceptor(null));
	}

	@Test
	public void currentSecurityContext() throws Exception {
		SecurityContextCallableProcessingInterceptor interceptor = new SecurityContextCallableProcessingInterceptor();
		SecurityContextHolder.setContext(this.securityContext);
		interceptor.beforeConcurrentHandling(this.webRequest, this.callable);
		SecurityContextHolder.clearContext();
		interceptor.preProcess(this.webRequest, this.callable);
		assertThat(SecurityContextHolder.getContext()).isSameAs(this.securityContext);
		interceptor.postProcess(this.webRequest, this.callable, null);
		assertThat(SecurityContextHolder.getContext()).isNotSameAs(this.securityContext);
	}

	@Test
	public void specificSecurityContext() throws Exception {
		SecurityContextCallableProcessingInterceptor interceptor = new SecurityContextCallableProcessingInterceptor(
				this.securityContext);
		interceptor.preProcess(this.webRequest, this.callable);
		assertThat(SecurityContextHolder.getContext()).isSameAs(this.securityContext);
		interceptor.postProcess(this.webRequest, this.callable, null);
		assertThat(SecurityContextHolder.getContext()).isNotSameAs(this.securityContext);
	}

}

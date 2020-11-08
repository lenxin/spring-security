package org.springframework.security.test.web.servlet.request;

import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.web.support.WebTestUtils;
import org.springframework.security.web.context.SecurityContextRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(PowerMockRunner.class)
@PrepareOnlyThisForTest(WebTestUtils.class)
@PowerMockIgnore({ "javax.security.auth.*", "org.w3c.dom.*", "org.xml.sax.*", "org.apache.xerces.*",
		"javax.xml.parsers.*" })
public class SecurityMockMvcRequestPostProcessorsUserDetailsTests {

	@Captor
	private ArgumentCaptor<SecurityContext> contextCaptor;

	@Mock
	private SecurityContextRepository repository;

	private MockHttpServletRequest request;

	@Mock
	private UserDetails userDetails;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
		mockWebTestUtils();
	}

	@After
	public void cleanup() {
		TestSecurityContextHolder.clearContext();
	}

	@Test
	public void userDetails() {
		user(this.userDetails).postProcessRequest(this.request);
		verify(this.repository).saveContext(this.contextCaptor.capture(), eq(this.request),
				any(HttpServletResponse.class));
		SecurityContext context = this.contextCaptor.getValue();
		assertThat(context.getAuthentication()).isInstanceOf(UsernamePasswordAuthenticationToken.class);
		assertThat(context.getAuthentication().getPrincipal()).isSameAs(this.userDetails);
	}

	private void mockWebTestUtils() {
		PowerMockito.spy(WebTestUtils.class);
		PowerMockito.when(WebTestUtils.getSecurityContextRepository(this.request)).thenReturn(this.repository);
	}

}

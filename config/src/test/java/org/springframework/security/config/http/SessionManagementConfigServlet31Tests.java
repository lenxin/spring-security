package org.springframework.security.config.http;

import java.lang.reflect.Method;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.config.util.InMemoryXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ ReflectionUtils.class, Method.class })
@PowerMockIgnore({ "org.w3c.dom.*", "org.xml.sax.*", "org.apache.xerces.*", "javax.xml.parsers.*" })
public class SessionManagementConfigServlet31Tests {

	// @formatter:off
	private static final String XML_AUTHENTICATION_MANAGER = "<authentication-manager>"
			+ "  <authentication-provider>"
			+ "    <user-service>"
			+ "      <user name='user' password='{noop}password' authorities='ROLE_USER' />"
			+ "    </user-service>"
			+ "  </authentication-provider>"
			+ "</authentication-manager>";
	// @formatter:on

	@Mock
	Method method;

	MockHttpServletRequest request;

	MockHttpServletResponse response;

	MockFilterChain chain;

	ConfigurableApplicationContext context;

	Filter springSecurityFilterChain;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest("GET", "");
		this.response = new MockHttpServletResponse();
		this.chain = new MockFilterChain();
	}

	@After
	public void teardown() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void changeSessionIdThenPreserveParameters() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "");
		request.getSession();
		request.setServletPath("/login");
		request.setMethod("POST");
		request.setParameter("username", "user");
		request.setParameter("password", "password");
		request.getSession().setAttribute("attribute1", "value1");
		String id = request.getSession().getId();
		// @formatter:off
		loadContext("<http>\n"
				+ "        <form-login/>\n"
				+ "        <session-management/>\n"
				+ "        <csrf disabled='true'/>\n"
				+ "    </http>"
				+ XML_AUTHENTICATION_MANAGER);
		// @formatter:on
		this.springSecurityFilterChain.doFilter(request, this.response, this.chain);
		assertThat(request.getSession().getId()).isNotEqualTo(id);
		assertThat(request.getSession().getAttribute("attribute1")).isEqualTo("value1");
	}

	@Test
	public void changeSessionId() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest("GET", "");
		request.getSession();
		request.setServletPath("/login");
		request.setMethod("POST");
		request.setParameter("username", "user");
		request.setParameter("password", "password");
		String id = request.getSession().getId();
		// @formatter:off
		loadContext("<http>\n"
				+ "        <form-login/>\n"
				+ "        <session-management session-fixation-protection='changeSessionId'/>\n"
				+ "        <csrf disabled='true'/>\n"
				+ "    </http>"
				+ XML_AUTHENTICATION_MANAGER);
		// @formatter:on
		this.springSecurityFilterChain.doFilter(request, this.response, this.chain);
		assertThat(request.getSession().getId()).isNotEqualTo(id);
	}

	private void loadContext(String context) {
		this.context = new InMemoryXmlApplicationContext(context);
		this.springSecurityFilterChain = this.context.getBean("springSecurityFilterChain", Filter.class);
	}

	private void login(Authentication auth) {
		HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
		HttpRequestResponseHolder requestResponseHolder = new HttpRequestResponseHolder(this.request, this.response);
		repo.loadContext(requestResponseHolder);
		SecurityContextImpl securityContextImpl = new SecurityContextImpl();
		securityContextImpl.setAuthentication(auth);
		repo.saveContext(securityContextImpl, requestResponseHolder.getRequest(), requestResponseHolder.getResponse());
	}

}

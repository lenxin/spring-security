package org.springframework.security.config.http;

import java.net.URI;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.test.SpringTestContext;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.test.context.annotation.SecurityTestExecutionListeners;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.support.WebTestUtils;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Rob Winch
 * @author Josh Cummings
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SecurityTestExecutionListeners
public class CsrfConfigTests {

	private static final String CONFIG_LOCATION_PREFIX = "classpath:org/springframework/security/config/http/CsrfConfigTests";

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	MockMvc mvc;

	@Test
	public void postWhenDefaultConfigurationThenForbiddenSinceCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("AutoConfig")).autowire();
		// @formatter:off
		this.mvc.perform(post("/csrf"))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void putWhenDefaultConfigurationThenForbiddenSinceCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("AutoConfig")).autowire();
		// @formatter:off
		this.mvc.perform(put("/csrf"))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void patchWhenDefaultConfigurationThenForbiddenSinceCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("AutoConfig")).autowire();
		// @formatter:off
		this.mvc.perform(patch("/csrf"))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void deleteWhenDefaultConfigurationThenForbiddenSinceCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("AutoConfig")).autowire();
		// @formatter:off
		this.mvc.perform(delete("/csrf"))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void invalidWhenDefaultConfigurationThenForbiddenSinceCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("AutoConfig")).autowire();
		// @formatter:off
		this.mvc.perform(request("INVALID", new URI("/csrf")))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void getWhenDefaultConfigurationThenCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("AutoConfig")).autowire();
		// @formatter:off
		this.mvc.perform(get("/csrf"))
				.andExpect(csrfInBody());
		// @formatter:on
	}

	@Test
	public void headWhenDefaultConfigurationThenCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("AutoConfig")).autowire();
		// @formatter:off
		this.mvc.perform(head("/csrf-in-header"))
				.andExpect(csrfInHeader());
		// @formatter:on
	}

	@Test
	public void traceWhenDefaultConfigurationThenCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("AutoConfig")).autowire();
		// @formatter:off
		MockMvc traceEnabled = MockMvcBuilders.webAppContextSetup(this.spring.getContext())
				.apply(springSecurity())
				.addDispatcherServletCustomizer((dispatcherServlet) -> dispatcherServlet.setDispatchTraceRequest(true))
				.build();
		traceEnabled.perform(request(HttpMethod.TRACE, "/csrf-in-header"))
				.andExpect(csrfInHeader());
		// @formatter:on
	}

	@Test
	public void optionsWhenDefaultConfigurationThenCsrfIsEnabled() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("AutoConfig")).autowire();
		// @formatter:off
		this.mvc.perform(options("/csrf-in-header"))
				.andExpect(csrfInHeader());
		// @formatter:on
	}

	@Test
	public void postWhenCsrfDisabledThenRequestAllowed() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("CsrfDisabled")).autowire();
		// @formatter:off
		this.mvc.perform(post("/ok"))
				.andExpect(status().isOk());
		// @formatter:on
		assertThat(getFilter(this.spring, CsrfFilter.class)).isNull();
	}

	@Test
	public void postWhenCsrfElementEnabledThenForbidden() throws Exception {
		this.spring.configLocations(this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		this.mvc.perform(post("/csrf"))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void putWhenCsrfElementEnabledThenForbidden() throws Exception {
		this.spring.configLocations(this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		this.mvc.perform(put("/csrf"))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void patchWhenCsrfElementEnabledThenForbidden() throws Exception {
		this.spring.configLocations(this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		this.mvc.perform(patch("/csrf"))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void deleteWhenCsrfElementEnabledThenForbidden() throws Exception {
		this.spring.configLocations(this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		this.mvc.perform(delete("/csrf"))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void invalidWhenCsrfElementEnabledThenForbidden() throws Exception {
		this.spring.configLocations(this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		this.mvc.perform(request("INVALID", new URI("/csrf")))
				.andExpect(status().isForbidden())
				.andExpect(csrfCreated());
		// @formatter:on
	}

	@Test
	public void getWhenCsrfElementEnabledThenOk() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		this.mvc.perform(get("/csrf"))
				.andExpect(csrfInBody());
		// @formatter:on
	}

	@Test
	public void headWhenCsrfElementEnabledThenOk() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		this.mvc.perform(head("/csrf-in-header"))
				.andExpect(csrfInHeader());
		// @formatter:on
	}

	@Test
	public void traceWhenCsrfElementEnabledThenOk() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		MockMvc traceEnabled = MockMvcBuilders.webAppContextSetup(this.spring.getContext())
				.apply(springSecurity())
				.addDispatcherServletCustomizer((dispatcherServlet) -> dispatcherServlet.setDispatchTraceRequest(true))
				.build();
		// @formatter:on
		traceEnabled.perform(request(HttpMethod.TRACE, "/csrf-in-header")).andExpect(csrfInHeader());
	}

	@Test
	public void optionsWhenCsrfElementEnabledThenOk() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("CsrfEnabled")).autowire();
		// @formatter:off
		this.mvc.perform(options("/csrf-in-header"))
				.andExpect(csrfInHeader());
		// @formatter:on
	}

	@Test
	public void autowireWhenCsrfElementEnabledThenCreatesCsrfRequestDataValueProcessor() {
		this.spring.configLocations(this.xml("CsrfEnabled")).autowire();
		assertThat(this.spring.getContext().getBean(RequestDataValueProcessor.class)).isNotNull();
	}

	@Test
	public void postWhenUsingCsrfAndCustomAccessDeniedHandlerThenTheHandlerIsAppropriatelyEngaged() throws Exception {
		this.spring.configLocations(this.xml("WithAccessDeniedHandler"), this.xml("shared-access-denied-handler"))
				.autowire();
		// @formatter:off
		this.mvc.perform(post("/ok"))
				.andExpect(status().isIAmATeapot());
		// @formatter:on
	}

	@Test
	public void postWhenHasCsrfTokenButSessionExpiresThenRequestIsCancelledAfterSuccessfulAuthentication()
			throws Exception {
		this.spring.configLocations(this.xml("CsrfEnabled")).autowire();
		// simulates a request that has no authentication (e.g. session time-out)
		MvcResult result = this.mvc.perform(post("/authenticated").with(csrf()))
				.andExpect(redirectedUrl("http://localhost/login")).andReturn();
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		// if the request cache is consulted, then it will redirect back to /some-url,
		// which we don't want
		// @formatter:off
		MockHttpServletRequestBuilder login = post("/login")
				.param("username", "user")
				.param("password", "password")
				.session(session)
				.with(csrf());
		this.mvc.perform(login)
				.andExpect(redirectedUrl("/"));
		// @formatter:on
	}

	@Test
	public void getWhenHasCsrfTokenButSessionExpiresThenRequestIsRememeberedAfterSuccessfulAuthentication()
			throws Exception {
		this.spring.configLocations(this.xml("CsrfEnabled")).autowire();
		// simulates a request that has no authentication (e.g. session time-out)
		MvcResult result = this.mvc.perform(get("/authenticated")).andExpect(redirectedUrl("http://localhost/login"))
				.andReturn();
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		// if the request cache is consulted, then it will redirect back to /some-url,
		// which we do want
		// @formatter:off
		MockHttpServletRequestBuilder login = post("/login")
				.param("username", "user")
				.param("password", "password")
				.session(session)
				.with(csrf());
		this.mvc.perform(login)
				.andExpect(redirectedUrl("http://localhost/authenticated"));
		// @formatter:on
	}

	/**
	 * SEC-2422: csrf expire CSRF token and session-management invalid-session-url
	 */
	@Test
	public void postWhenUsingCsrfAndCustomSessionManagementAndNoSessionThenStillRedirectsToInvalidSessionUrl()
			throws Exception {
		this.spring.configLocations(this.xml("WithSessionManagement")).autowire();
		// @formatter:off
		MockHttpServletRequestBuilder postToOk = post("/ok")
				.param("_csrf", "abc");
		MvcResult result = this.mvc.perform(postToOk)
				.andExpect(redirectedUrl("/error/sessionError"))
				.andReturn();
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		this.mvc.perform(post("/csrf").session(session))
				.andExpect(status().isForbidden());
		// @formatter:on
	}

	@Test
	public void requestWhenUsingCustomRequestMatcherConfiguredThenAppliesAccordingly() throws Exception {
		SpringTestContext context = this.spring.configLocations(this.xml("shared-controllers"),
				this.xml("WithRequestMatcher"), this.xml("mock-request-matcher"));
		context.autowire();
		RequestMatcher matcher = context.getContext().getBean(RequestMatcher.class);
		given(matcher.matches(any(HttpServletRequest.class))).willReturn(false);
		// @formatter:off
		this.mvc.perform(post("/ok"))
				.andExpect(status().isOk());
		// @formatter:on
		given(matcher.matches(any(HttpServletRequest.class))).willReturn(true);
		// @formatter:off
		this.mvc.perform(get("/ok"))
				.andExpect(status().isForbidden());
		// @formatter:on
	}

	@Test
	public void getWhenDefaultConfigurationThenSessionNotImmediatelyCreated() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("AutoConfig")).autowire();
		// @formatter:off
		MvcResult result = this.mvc.perform(get("/ok"))
				.andExpect(status().isOk()).andReturn();
		// @formatter:on
		assertThat(result.getRequest().getSession(false)).isNull();
	}

	@Test
	@WithMockUser
	public void postWhenCsrfMismatchesThenForbidden() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("AutoConfig")).autowire();
		MvcResult result = this.mvc.perform(get("/ok")).andReturn();
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		// @formatter:off
		MockHttpServletRequestBuilder postOk = post("/ok")
				.session(session)
				.with(csrf().useInvalidToken());
		this.mvc.perform(postOk)
				.andExpect(status().isForbidden());
		// @formatter:on
	}

	@Test
	public void loginWhenDefaultConfigurationThenCsrfCleared() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("AutoConfig")).autowire();
		MvcResult result = this.mvc.perform(get("/csrf")).andReturn();
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		// @formatter:off
		MockHttpServletRequestBuilder loginRequest = post("/login")
				.param("username", "user")
				.param("password", "password")
				.session(session)
				.with(csrf());
		this.mvc.perform(loginRequest)
				.andExpect(status().isFound());
		this.mvc.perform(get("/csrf").session(session))
				.andExpect(csrfChanged(result));
		// @formatter:on
	}

	@Test
	public void logoutWhenDefaultConfigurationThenCsrfCleared() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("AutoConfig")).autowire();
		MvcResult result = this.mvc.perform(get("/csrf")).andReturn();
		MockHttpSession session = (MockHttpSession) result.getRequest().getSession();
		// @formatter:off
		this.mvc.perform(post("/logout").session(session).with(csrf()))
				.andExpect(status().isFound());
		this.mvc.perform(get("/csrf").session(session))
				.andExpect(csrfChanged(result));
		// @formatter:on
	}

	/**
	 * SEC-2495: csrf disables logout on GET
	 */
	@Test
	@WithMockUser
	public void logoutWhenDefaultConfigurationThenDisabled() throws Exception {
		this.spring.configLocations(this.xml("shared-controllers"), this.xml("CsrfEnabled")).autowire();
		// renders form to log out but does not perform a redirect
		// @formatter:off
		this.mvc.perform(get("/logout"))
				.andExpect(status().isOk());
		// @formatter:on
		// still logged in
		// @formatter:off
		this.mvc.perform(get("/authenticated"))
				.andExpect(status().isOk());
		// @formatter:on
	}

	private <T extends Filter> T getFilter(SpringTestContext context, Class<T> type) {
		FilterChainProxy chain = context.getContext().getBean(FilterChainProxy.class);
		List<Filter> filters = chain.getFilters("/any");
		for (Filter filter : filters) {
			if (type.isAssignableFrom(filter.getClass())) {
				return (T) filter;
			}
		}
		return null;
	}

	private String xml(String configName) {
		return CONFIG_LOCATION_PREFIX + "-" + configName + ".xml";
	}

	ResultMatcher csrfChanged(MvcResult first) {
		return (second) -> {
			assertThat(first).isNotNull();
			assertThat(second).isNotNull();
			assertThat(first.getResponse().getContentAsString())
					.isNotEqualTo(second.getResponse().getContentAsString());
		};
	}

	ResultMatcher csrfCreated() {
		return new CsrfCreatedResultMatcher();
	}

	ResultMatcher csrfInHeader() {
		return new CsrfReturnedResultMatcher((result) -> result.getResponse().getHeader("X-CSRF-TOKEN"));
	}

	ResultMatcher csrfInBody() {
		return new CsrfReturnedResultMatcher((result) -> result.getResponse().getContentAsString());
	}

	@Controller
	public static class RootController {

		@RequestMapping(value = "/csrf-in-header",
				method = { RequestMethod.HEAD, RequestMethod.TRACE, RequestMethod.OPTIONS })
		@ResponseBody
		String csrfInHeaderAndBody(CsrfToken token, HttpServletResponse response) {
			response.setHeader(token.getHeaderName(), token.getToken());
			return csrfInBody(token);
		}

		@RequestMapping(value = "/csrf", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH,
				RequestMethod.DELETE, RequestMethod.GET })
		@ResponseBody
		String csrfInBody(CsrfToken token) {
			return token.getToken();
		}

		@RequestMapping(value = "/ok", method = { RequestMethod.POST, RequestMethod.GET })
		@ResponseBody
		String ok() {
			return "ok";
		}

		@GetMapping("/authenticated")
		@ResponseBody
		String authenticated() {
			return "authenticated";
		}

	}

	private static class TeapotAccessDeniedHandler implements AccessDeniedHandler {

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response,
				AccessDeniedException accessDeniedException) {
			response.setStatus(HttpStatus.IM_A_TEAPOT_418);
		}

	}

	@FunctionalInterface
	interface ExceptionalFunction<IN, OUT> {

		OUT apply(IN in) throws Exception;

	}

	static class CsrfCreatedResultMatcher implements ResultMatcher {

		@Override
		public void match(MvcResult result) {
			MockHttpServletRequest request = result.getRequest();
			CsrfToken token = WebTestUtils.getCsrfTokenRepository(request).loadToken(request);
			assertThat(token).isNotNull();
		}

	}

	static class CsrfReturnedResultMatcher implements ResultMatcher {

		ExceptionalFunction<MvcResult, String> token;

		CsrfReturnedResultMatcher(ExceptionalFunction<MvcResult, String> token) {
			this.token = token;
		}

		@Override
		public void match(MvcResult result) throws Exception {
			MockHttpServletRequest request = result.getRequest();
			CsrfToken token = WebTestUtils.getCsrfTokenRepository(request).loadToken(request);
			assertThat(token).isNotNull();
			assertThat(token.getToken()).isEqualTo(this.token.apply(result));
		}

	}

}

package org.springframework.security.config.annotation.web.configurers;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractConfigAttributeRequestMatcherRegistryTests {

	private ConcreteAbstractRequestMatcherMappingConfigurer registry;

	@Before
	public void setup() {
		this.registry = new ConcreteAbstractRequestMatcherMappingConfigurer();
	}

	@Test
	public void testGetRequestMatcherIsTypeRegexMatcher() {
		List<RequestMatcher> requestMatchers = this.registry.regexMatchers(HttpMethod.GET, "/a.*");
		for (RequestMatcher requestMatcher : requestMatchers) {
			assertThat(requestMatcher).isInstanceOf(RegexRequestMatcher.class);
		}
	}

	@Test
	public void testRequestMatcherIsTypeRegexMatcher() {
		List<RequestMatcher> requestMatchers = this.registry.regexMatchers("/a.*");
		for (RequestMatcher requestMatcher : requestMatchers) {
			assertThat(requestMatcher).isInstanceOf(RegexRequestMatcher.class);
		}
	}

	@Test
	public void testGetRequestMatcherIsTypeAntPathRequestMatcher() {
		List<RequestMatcher> requestMatchers = this.registry.antMatchers(HttpMethod.GET, "/a.*");
		for (RequestMatcher requestMatcher : requestMatchers) {
			assertThat(requestMatcher).isInstanceOf(AntPathRequestMatcher.class);
		}
	}

	@Test
	public void testRequestMatcherIsTypeAntPathRequestMatcher() {
		List<RequestMatcher> requestMatchers = this.registry.antMatchers("/a.*");
		for (RequestMatcher requestMatcher : requestMatchers) {
			assertThat(requestMatcher).isInstanceOf(AntPathRequestMatcher.class);
		}
	}

	static class ConcreteAbstractRequestMatcherMappingConfigurer
			extends AbstractConfigAttributeRequestMatcherRegistry<List<RequestMatcher>> {

		List<AccessDecisionVoter> decisionVoters() {
			return null;
		}

		@Override
		protected List<RequestMatcher> chainRequestMatchersInternal(List<RequestMatcher> requestMatchers) {
			return requestMatchers;
		}

		@Override
		public List<RequestMatcher> mvcMatchers(String... mvcPatterns) {
			return null;
		}

		@Override
		public List<RequestMatcher> mvcMatchers(HttpMethod method, String... mvcPatterns) {
			return null;
		}

	}

}

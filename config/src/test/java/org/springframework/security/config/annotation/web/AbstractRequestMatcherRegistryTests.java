package org.springframework.security.config.annotation.web;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link AbstractRequestMatcherRegistry}.
 *
 * @author Joe Grandja
 */
public class AbstractRequestMatcherRegistryTests {

	private TestRequestMatcherRegistry matcherRegistry;

	@Before
	public void setUp() {
		this.matcherRegistry = new TestRequestMatcherRegistry();
	}

	@Test
	public void regexMatchersWhenHttpMethodAndPatternParamsThenReturnRegexRequestMatcherType() {
		List<RequestMatcher> requestMatchers = this.matcherRegistry.regexMatchers(HttpMethod.GET, "/a.*");
		assertThat(requestMatchers).isNotEmpty();
		assertThat(requestMatchers.size()).isEqualTo(1);
		assertThat(requestMatchers.get(0)).isExactlyInstanceOf(RegexRequestMatcher.class);
	}

	@Test
	public void regexMatchersWhenPatternParamThenReturnRegexRequestMatcherType() {
		List<RequestMatcher> requestMatchers = this.matcherRegistry.regexMatchers("/a.*");
		assertThat(requestMatchers).isNotEmpty();
		assertThat(requestMatchers.size()).isEqualTo(1);
		assertThat(requestMatchers.get(0)).isExactlyInstanceOf(RegexRequestMatcher.class);
	}

	@Test
	public void antMatchersWhenHttpMethodAndPatternParamsThenReturnAntPathRequestMatcherType() {
		List<RequestMatcher> requestMatchers = this.matcherRegistry.antMatchers(HttpMethod.GET, "/a.*");
		assertThat(requestMatchers).isNotEmpty();
		assertThat(requestMatchers.size()).isEqualTo(1);
		assertThat(requestMatchers.get(0)).isExactlyInstanceOf(AntPathRequestMatcher.class);
	}

	@Test
	public void antMatchersWhenPatternParamThenReturnAntPathRequestMatcherType() {
		List<RequestMatcher> requestMatchers = this.matcherRegistry.antMatchers("/a.*");
		assertThat(requestMatchers).isNotEmpty();
		assertThat(requestMatchers.size()).isEqualTo(1);
		assertThat(requestMatchers.get(0)).isExactlyInstanceOf(AntPathRequestMatcher.class);
	}

	private static class TestRequestMatcherRegistry extends AbstractRequestMatcherRegistry<List<RequestMatcher>> {

		@Override
		public List<RequestMatcher> mvcMatchers(String... mvcPatterns) {
			return null;
		}

		@Override
		public List<RequestMatcher> mvcMatchers(HttpMethod method, String... mvcPatterns) {
			return null;
		}

		@Override
		protected List<RequestMatcher> chainRequestMatchers(List<RequestMatcher> requestMatchers) {
			return requestMatchers;
		}

	}

}

package org.springframework.security.config.annotation.web.configurers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.SecurityConfig;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractConfigAttributeRequestMatcherRegistry.UrlMapping;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;

/**
 * Configures non-null URL's to grant access to every URL
 *
 * @author Rob Winch
 * @since 3.2
 */
final class PermitAllSupport {

	private PermitAllSupport() {
	}

	static void permitAll(HttpSecurityBuilder<? extends HttpSecurityBuilder<?>> http, String... urls) {
		for (String url : urls) {
			if (url != null) {
				permitAll(http, new ExactUrlRequestMatcher(url));
			}
		}
	}

	@SuppressWarnings("unchecked")
	static void permitAll(HttpSecurityBuilder<? extends HttpSecurityBuilder<?>> http,
			RequestMatcher... requestMatchers) {
		ExpressionUrlAuthorizationConfigurer<?> configurer = http
				.getConfigurer(ExpressionUrlAuthorizationConfigurer.class);
		Assert.state(configurer != null, "permitAll only works with HttpSecurity.authorizeRequests()");
		for (RequestMatcher matcher : requestMatchers) {
			if (matcher != null) {
				configurer.getRegistry().addMapping(0, new UrlMapping(matcher,
						SecurityConfig.createList(ExpressionUrlAuthorizationConfigurer.permitAll)));
			}
		}
	}

	private static final class ExactUrlRequestMatcher implements RequestMatcher {

		private String processUrl;

		private ExactUrlRequestMatcher(String processUrl) {
			this.processUrl = processUrl;
		}

		@Override
		public boolean matches(HttpServletRequest request) {
			String uri = request.getRequestURI();
			String query = request.getQueryString();
			if (query != null) {
				uri += "?" + query;
			}
			if ("".equals(request.getContextPath())) {
				return uri.equals(this.processUrl);
			}
			return uri.equals(request.getContextPath() + this.processUrl);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("ExactUrl [processUrl='").append(this.processUrl).append("']");
			return sb.toString();
		}

	}

}

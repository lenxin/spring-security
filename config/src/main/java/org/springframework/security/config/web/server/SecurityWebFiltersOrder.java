package org.springframework.security.config.web.server;

/**
 * @author Rob Winch
 * @since 5.0
 */
public enum SecurityWebFiltersOrder {

	FIRST(Integer.MIN_VALUE),

	HTTP_HEADERS_WRITER,

	/**
	 * {@link org.springframework.security.web.server.transport.HttpsRedirectWebFilter}
	 */
	HTTPS_REDIRECT,

	/**
	 * {@link org.springframework.web.cors.reactive.CorsWebFilter}
	 */
	CORS,

	/**
	 * {@link org.springframework.security.web.server.csrf.CsrfWebFilter}
	 */
	CSRF,

	/**
	 * {@link org.springframework.security.web.server.context.ReactorContextWebFilter}
	 */
	REACTOR_CONTEXT,

	/**
	 * Instance of AuthenticationWebFilter
	 */
	HTTP_BASIC,

	/**
	 * Instance of AuthenticationWebFilter
	 */
	FORM_LOGIN, AUTHENTICATION,

	/**
	 * Instance of AnonymousAuthenticationWebFilter
	 */
	ANONYMOUS_AUTHENTICATION,

	OAUTH2_AUTHORIZATION_CODE,

	LOGIN_PAGE_GENERATING,

	LOGOUT_PAGE_GENERATING,

	/**
	 * {@link org.springframework.security.web.server.context.SecurityContextServerWebExchangeWebFilter}
	 */
	SECURITY_CONTEXT_SERVER_WEB_EXCHANGE,

	/**
	 * {@link org.springframework.security.web.server.savedrequest.ServerRequestCacheWebFilter}
	 */
	SERVER_REQUEST_CACHE,

	LOGOUT,

	EXCEPTION_TRANSLATION,

	AUTHORIZATION,

	LAST(Integer.MAX_VALUE);

	private static final int INTERVAL = 100;

	private final int order;

	SecurityWebFiltersOrder() {
		this.order = ordinal() * INTERVAL;
	}

	SecurityWebFiltersOrder(int order) {
		this.order = order;
	}

	public int getOrder() {
		return this.order;
	}

}

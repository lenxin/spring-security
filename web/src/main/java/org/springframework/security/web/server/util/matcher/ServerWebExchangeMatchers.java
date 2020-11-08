package org.springframework.security.web.server.util.matcher;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;

/**
 * Provides factory methods for creating common {@link ServerWebExchangeMatcher}
 *
 * @author Rob Winch
 * @since 5.0
 */
public abstract class ServerWebExchangeMatchers {

	private ServerWebExchangeMatchers() {
	}

	/**
	 * Creates a matcher that matches on the specific method and any of the provided
	 * patterns.
	 * @param method the method to match on. If null, any method will be matched
	 * @param patterns the patterns to match on
	 * @return the matcher to use
	 */
	public static ServerWebExchangeMatcher pathMatchers(HttpMethod method, String... patterns) {
		List<ServerWebExchangeMatcher> matchers = new ArrayList<>(patterns.length);
		for (String pattern : patterns) {
			matchers.add(new PathPatternParserServerWebExchangeMatcher(pattern, method));
		}
		return new OrServerWebExchangeMatcher(matchers);
	}

	/**
	 * Creates a matcher that matches on any of the provided patterns.
	 * @param patterns the patterns to match on
	 * @return the matcher to use
	 */
	public static ServerWebExchangeMatcher pathMatchers(String... patterns) {
		return pathMatchers(null, patterns);
	}

	/**
	 * Creates a matcher that will match on any of the provided matchers
	 * @param matchers the matchers to match on
	 * @return the matcher to use
	 */
	public static ServerWebExchangeMatcher matchers(ServerWebExchangeMatcher... matchers) {
		return new OrServerWebExchangeMatcher(matchers);
	}

	/**
	 * Matches any exchange
	 * @return the matcher to use
	 */
	@SuppressWarnings("Convert2Lambda")
	public static ServerWebExchangeMatcher anyExchange() {
		// we don't use a lambda to ensure a unique equals and hashcode
		// which otherwise can cause problems with adding multiple entries to an ordered
		// LinkedHashMap
		return new ServerWebExchangeMatcher() {

			@Override
			public Mono<MatchResult> matches(ServerWebExchange exchange) {
				return ServerWebExchangeMatcher.MatchResult.match();
			}

		};
	}

}

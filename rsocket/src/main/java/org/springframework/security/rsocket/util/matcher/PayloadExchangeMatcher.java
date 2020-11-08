package org.springframework.security.rsocket.util.matcher;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import reactor.core.publisher.Mono;

import org.springframework.security.rsocket.api.PayloadExchange;

/**
 * An interface for determining if a {@link PayloadExchangeMatcher} matches.
 *
 * @author Rob Winch
 * @since 5.2
 */
public interface PayloadExchangeMatcher {

	/**
	 * Determines if a request matches or not
	 * @param exchange
	 * @return
	 */
	Mono<MatchResult> matches(PayloadExchange exchange);

	/**
	 * The result of matching
	 */
	class MatchResult {

		private final boolean match;

		private final Map<String, Object> variables;

		private MatchResult(boolean match, Map<String, Object> variables) {
			this.match = match;
			this.variables = variables;
		}

		public boolean isMatch() {
			return this.match;
		}

		/**
		 * Gets potential variables and their values
		 * @return
		 */
		public Map<String, Object> getVariables() {
			return this.variables;
		}

		/**
		 * Creates an instance of {@link MatchResult} that is a match with no variables
		 * @return
		 */
		public static Mono<MatchResult> match() {
			return match(Collections.emptyMap());
		}

		/**
		 *
		 * Creates an instance of {@link MatchResult} that is a match with the specified
		 * variables
		 * @param variables
		 * @return
		 */
		public static Mono<MatchResult> match(Map<String, ? extends Object> variables) {
			MatchResult result = new MatchResult(true,
					(variables != null) ? new HashMap<String, Object>(variables) : null);
			return Mono.just(result);
		}

		/**
		 * Creates an instance of {@link MatchResult} that is not a match.
		 * @return
		 */
		public static Mono<MatchResult> notMatch() {
			return Mono.just(new MatchResult(false, Collections.emptyMap()));
		}

	}

}

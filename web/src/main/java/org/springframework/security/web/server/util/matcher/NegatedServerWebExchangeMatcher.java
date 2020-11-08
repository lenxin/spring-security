package org.springframework.security.web.server.util.matcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Mono;

import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * Negates the provided matcher. If the provided matcher returns true, then the result
 * will be false. If the provided matcher returns false, then the result will be true.
 *
 * @author Tao Qian
 * @author Mathieu Ouellet
 * @since 5.1
 */
public class NegatedServerWebExchangeMatcher implements ServerWebExchangeMatcher {

	private static final Log logger = LogFactory.getLog(NegatedServerWebExchangeMatcher.class);

	private final ServerWebExchangeMatcher matcher;

	public NegatedServerWebExchangeMatcher(ServerWebExchangeMatcher matcher) {
		Assert.notNull(matcher, "matcher cannot be null");
		this.matcher = matcher;
	}

	@Override
	public Mono<MatchResult> matches(ServerWebExchange exchange) {
		return this.matcher.matches(exchange).flatMap(this::negate)
				.doOnNext((matchResult) -> logger.debug(LogMessage.format("matches = %s", matchResult.isMatch())));
	}

	private Mono<MatchResult> negate(MatchResult matchResult) {
		return matchResult.isMatch() ? MatchResult.notMatch() : MatchResult.match();
	}

	@Override
	public String toString() {
		return "NegatedServerWebExchangeMatcher{" + "matcher=" + this.matcher + '}';
	}

}

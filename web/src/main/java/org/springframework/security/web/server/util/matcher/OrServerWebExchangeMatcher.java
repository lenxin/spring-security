package org.springframework.security.web.server.util.matcher;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.core.log.LogMessage;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;

/**
 * Matches if any of the provided {@link ServerWebExchangeMatcher} match
 *
 * @author Rob Winch
 * @author Mathieu Ouellet
 * @since 5.0
 * @see AndServerWebExchangeMatcher
 */
public class OrServerWebExchangeMatcher implements ServerWebExchangeMatcher {

	private static final Log logger = LogFactory.getLog(OrServerWebExchangeMatcher.class);

	private final List<ServerWebExchangeMatcher> matchers;

	public OrServerWebExchangeMatcher(List<ServerWebExchangeMatcher> matchers) {
		Assert.notEmpty(matchers, "matchers cannot be empty");
		this.matchers = matchers;
	}

	public OrServerWebExchangeMatcher(ServerWebExchangeMatcher... matchers) {
		this(Arrays.asList(matchers));
	}

	@Override
	public Mono<MatchResult> matches(ServerWebExchange exchange) {
		return Flux.fromIterable(this.matchers)
				.doOnNext((matcher) -> logger.debug(LogMessage.format("Trying to match using %s", matcher)))
				.flatMap((matcher) -> matcher.matches(exchange)).filter(MatchResult::isMatch).next()
				.switchIfEmpty(MatchResult.notMatch())
				.doOnNext((matchResult) -> logger.debug(matchResult.isMatch() ? "matched" : "No matches found"));
	}

	@Override
	public String toString() {
		return "OrServerWebExchangeMatcher{matchers=" + this.matchers + '}';
	}

}

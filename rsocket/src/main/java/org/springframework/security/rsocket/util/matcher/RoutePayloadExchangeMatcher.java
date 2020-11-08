package org.springframework.security.rsocket.util.matcher;

import java.util.Map;
import java.util.Optional;

import reactor.core.publisher.Mono;

import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.util.Assert;
import org.springframework.util.RouteMatcher;

// FIXME: Pay attention to the package this goes into. It requires spring-messaging for the MetadataExtractor.

/**
 * @author Rob Winch
 * @since 5.2
 */
public class RoutePayloadExchangeMatcher implements PayloadExchangeMatcher {

	private final String pattern;

	private final MetadataExtractor metadataExtractor;

	private final RouteMatcher routeMatcher;

	public RoutePayloadExchangeMatcher(MetadataExtractor metadataExtractor, RouteMatcher routeMatcher, String pattern) {
		Assert.notNull(pattern, "pattern cannot be null");
		this.metadataExtractor = metadataExtractor;
		this.routeMatcher = routeMatcher;
		this.pattern = pattern;
	}

	@Override
	public Mono<MatchResult> matches(PayloadExchange exchange) {
		Map<String, Object> metadata = this.metadataExtractor.extract(exchange.getPayload(),
				exchange.getMetadataMimeType());
		return Optional.ofNullable((String) metadata.get(MetadataExtractor.ROUTE_KEY))
				.map((routeValue) -> this.routeMatcher.parseRoute(routeValue))
				.map((route) -> this.routeMatcher.matchAndExtract(this.pattern, route)).map((v) -> MatchResult.match(v))
				.orElse(MatchResult.notMatch());
	}

}

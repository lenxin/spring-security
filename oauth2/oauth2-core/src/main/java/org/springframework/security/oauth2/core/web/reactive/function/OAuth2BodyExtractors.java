package org.springframework.security.oauth2.core.web.reactive.function;

import reactor.core.publisher.Mono;

import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.web.reactive.function.BodyExtractor;

/**
 * Static factory methods for OAuth2 {@link BodyExtractor} implementations.
 *
 * @author Rob Winch
 * @since 5.1
 */
public abstract class OAuth2BodyExtractors {

	/**
	 * Extractor to decode an {@link OAuth2AccessTokenResponse}
	 * @return a BodyExtractor for {@link OAuth2AccessTokenResponse}
	 */
	public static BodyExtractor<Mono<OAuth2AccessTokenResponse>, ReactiveHttpInputMessage> oauth2AccessTokenResponse() {
		return new OAuth2AccessTokenResponseBodyExtractor();
	}

	private OAuth2BodyExtractors() {
	}

}

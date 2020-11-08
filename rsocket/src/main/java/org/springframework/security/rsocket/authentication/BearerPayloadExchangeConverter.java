package org.springframework.security.rsocket.authentication;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import io.rsocket.metadata.CompositeMetadata;
import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.metadata.BearerTokenMetadata;

/**
 * Converts from the {@link PayloadExchange} to a {@link BearerTokenAuthenticationToken}
 * by extracting {@link BearerTokenMetadata#BEARER_AUTHENTICATION_MIME_TYPE} from the
 * metadata.
 *
 * @author Rob Winch
 * @since 5.2
 */
public class BearerPayloadExchangeConverter implements PayloadExchangeAuthenticationConverter {

	private static final String BEARER_MIME_TYPE_VALUE = BearerTokenMetadata.BEARER_AUTHENTICATION_MIME_TYPE.toString();

	@Override
	public Mono<Authentication> convert(PayloadExchange exchange) {
		ByteBuf metadata = exchange.getPayload().metadata();
		CompositeMetadata compositeMetadata = new CompositeMetadata(metadata, false);
		for (CompositeMetadata.Entry entry : compositeMetadata) {
			if (BEARER_MIME_TYPE_VALUE.equals(entry.getMimeType())) {
				ByteBuf content = entry.getContent();
				String token = content.toString(StandardCharsets.UTF_8);
				return Mono.just(new BearerTokenAuthenticationToken(token));
			}
		}
		return Mono.empty();
	}

}

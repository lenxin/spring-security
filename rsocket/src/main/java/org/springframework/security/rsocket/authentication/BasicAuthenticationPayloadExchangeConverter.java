package org.springframework.security.rsocket.authentication;

import io.rsocket.metadata.WellKnownMimeType;
import reactor.core.publisher.Mono;

import org.springframework.messaging.rsocket.DefaultMetadataExtractor;
import org.springframework.messaging.rsocket.MetadataExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.metadata.BasicAuthenticationDecoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

/**
 * Converts from the {@link PayloadExchange} to a
 * {@link UsernamePasswordAuthenticationToken} by extracting
 * {@link UsernamePasswordMetadata#BASIC_AUTHENTICATION_MIME_TYPE} from the metadata.
 *
 * @author Rob Winch
 * @since 5.2
 */
public class BasicAuthenticationPayloadExchangeConverter implements PayloadExchangeAuthenticationConverter {

	private MimeType metadataMimetype = MimeTypeUtils
			.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString());

	private MetadataExtractor metadataExtractor = createDefaultExtractor();

	@Override
	public Mono<Authentication> convert(PayloadExchange exchange) {
		return Mono.fromCallable(() -> this.metadataExtractor.extract(exchange.getPayload(), this.metadataMimetype))
				.flatMap((metadata) -> Mono
						.justOrEmpty(metadata.get(UsernamePasswordMetadata.BASIC_AUTHENTICATION_MIME_TYPE.toString())))
				.cast(UsernamePasswordMetadata.class)
				.map((credentials) -> new UsernamePasswordAuthenticationToken(credentials.getUsername(),
						credentials.getPassword()));
	}

	private static MetadataExtractor createDefaultExtractor() {
		DefaultMetadataExtractor result = new DefaultMetadataExtractor(new BasicAuthenticationDecoder());
		result.metadataToExtract(UsernamePasswordMetadata.BASIC_AUTHENTICATION_MIME_TYPE,
				UsernamePasswordMetadata.class, (String) null);
		return result;
	}

}

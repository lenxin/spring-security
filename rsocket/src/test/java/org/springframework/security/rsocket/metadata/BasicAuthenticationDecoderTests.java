package org.springframework.security.rsocket.metadata;

import java.util.Map;

import org.junit.Test;
import reactor.core.publisher.Mono;

import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.util.MimeType;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 */
public class BasicAuthenticationDecoderTests {

	@Test
	public void basicAuthenticationWhenEncodedThenDecodes() {
		BasicAuthenticationEncoder encoder = new BasicAuthenticationEncoder();
		BasicAuthenticationDecoder decoder = new BasicAuthenticationDecoder();
		UsernamePasswordMetadata expectedCredentials = new UsernamePasswordMetadata("rob", "password");
		DefaultDataBufferFactory factory = new DefaultDataBufferFactory();
		ResolvableType elementType = ResolvableType.forClass(UsernamePasswordMetadata.class);
		MimeType mimeType = UsernamePasswordMetadata.BASIC_AUTHENTICATION_MIME_TYPE;
		Map<String, Object> hints = null;
		DataBuffer dataBuffer = encoder.encodeValue(expectedCredentials, factory, elementType, mimeType, hints);
		UsernamePasswordMetadata actualCredentials = decoder
				.decodeToMono(Mono.just(dataBuffer), elementType, mimeType, hints).block();
		assertThat(actualCredentials).isEqualToComparingFieldByField(expectedCredentials);
	}

}

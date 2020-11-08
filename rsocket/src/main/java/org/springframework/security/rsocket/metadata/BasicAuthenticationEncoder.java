package org.springframework.security.rsocket.metadata;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import org.springframework.core.ResolvableType;
import org.springframework.core.codec.AbstractEncoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.util.MimeType;

/**
 * Encodes {@link UsernamePasswordMetadata#BASIC_AUTHENTICATION_MIME_TYPE}
 *
 * @author Rob Winch
 * @since 5.2
 * @deprecated Basic Authentication did not evolve into a standard. use
 * {@link SimpleAuthenticationEncoder}
 */
@Deprecated
public class BasicAuthenticationEncoder extends AbstractEncoder<UsernamePasswordMetadata> {

	public BasicAuthenticationEncoder() {
		super(UsernamePasswordMetadata.BASIC_AUTHENTICATION_MIME_TYPE);
	}

	@Override
	public Flux<DataBuffer> encode(Publisher<? extends UsernamePasswordMetadata> inputStream,
			DataBufferFactory bufferFactory, ResolvableType elementType, MimeType mimeType, Map<String, Object> hints) {
		return Flux.from(inputStream)
				.map((credentials) -> encodeValue(credentials, bufferFactory, elementType, mimeType, hints));
	}

	@Override
	public DataBuffer encodeValue(UsernamePasswordMetadata credentials, DataBufferFactory bufferFactory,
			ResolvableType valueType, MimeType mimeType, Map<String, Object> hints) {
		String username = credentials.getUsername();
		String password = credentials.getPassword();
		byte[] usernameBytes = username.getBytes(StandardCharsets.UTF_8);
		byte[] usernameBytesLengthBytes = ByteBuffer.allocate(4).putInt(usernameBytes.length).array();
		DataBuffer metadata = bufferFactory.allocateBuffer();
		boolean release = true;
		try {
			metadata.write(usernameBytesLengthBytes);
			metadata.write(usernameBytes);
			metadata.write(password.getBytes(StandardCharsets.UTF_8));
			release = false;
			return metadata;
		}
		finally {
			if (release) {
				DataBufferUtils.release(metadata);
			}
		}
	}

}

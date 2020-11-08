package org.springframework.security.crypto.keygen;

import org.springframework.security.crypto.codec.Hex;

/**
 * A StringKeyGenerator that generates hex-encoded String keys. Delegates to a
 * {@link BytesKeyGenerator} for the actual key generation.
 *
 * @author Keith Donald
 */
final class HexEncodingStringKeyGenerator implements StringKeyGenerator {

	private final BytesKeyGenerator keyGenerator;

	HexEncodingStringKeyGenerator(BytesKeyGenerator keyGenerator) {
		this.keyGenerator = keyGenerator;
	}

	@Override
	public String generateKey() {
		return new String(Hex.encode(this.keyGenerator.generateKey()));
	}

}

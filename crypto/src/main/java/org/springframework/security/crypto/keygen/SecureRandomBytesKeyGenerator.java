package org.springframework.security.crypto.keygen;

import java.security.SecureRandom;

/**
 * A KeyGenerator that uses {@link SecureRandom} to generate byte array-based keys.
 * <p>
 * No specific provider is used for the {@code SecureRandom}, so the platform default will
 * be used.
 *
 * @author Keith Donald
 */
final class SecureRandomBytesKeyGenerator implements BytesKeyGenerator {

	private static final int DEFAULT_KEY_LENGTH = 8;

	private final SecureRandom random;

	private final int keyLength;

	/**
	 * Creates a secure random key generator using the defaults.
	 */
	SecureRandomBytesKeyGenerator() {
		this(DEFAULT_KEY_LENGTH);
	}

	/**
	 * Creates a secure random key generator with a custom key length.
	 */
	SecureRandomBytesKeyGenerator(int keyLength) {
		this.random = new SecureRandom();
		this.keyLength = keyLength;
	}

	@Override
	public int getKeyLength() {
		return this.keyLength;
	}

	@Override
	public byte[] generateKey() {
		byte[] bytes = new byte[this.keyLength];
		this.random.nextBytes(bytes);
		return bytes;
	}

}

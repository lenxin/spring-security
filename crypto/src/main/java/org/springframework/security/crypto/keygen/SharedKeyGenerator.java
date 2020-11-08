package org.springframework.security.crypto.keygen;

/**
 * Key generator that simply returns the same key every time.
 *
 * @author Keith Donald
 * @author Annabelle Donald
 * @author Corgan Donald
 */
final class SharedKeyGenerator implements BytesKeyGenerator {

	private byte[] sharedKey;

	SharedKeyGenerator(byte[] sharedKey) {
		this.sharedKey = sharedKey;
	}

	@Override
	public int getKeyLength() {
		return this.sharedKey.length;
	}

	@Override
	public byte[] generateKey() {
		return this.sharedKey;
	}

}

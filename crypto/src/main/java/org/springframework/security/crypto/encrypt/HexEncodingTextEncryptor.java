package org.springframework.security.crypto.encrypt;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;

/**
 * Delegates to an {@link BytesEncryptor} to encrypt text strings. Raw text strings are
 * UTF-8 encoded before being passed to the encryptor. Encrypted strings are returned
 * hex-encoded.
 *
 * @author Keith Donald
 */
final class HexEncodingTextEncryptor implements TextEncryptor {

	private final BytesEncryptor encryptor;

	HexEncodingTextEncryptor(BytesEncryptor encryptor) {
		this.encryptor = encryptor;
	}

	@Override
	public String encrypt(String text) {
		return new String(Hex.encode(this.encryptor.encrypt(Utf8.encode(text))));
	}

	@Override
	public String decrypt(String encryptedText) {
		return Utf8.decode(this.encryptor.decrypt(Hex.decode(encryptedText)));
	}

}

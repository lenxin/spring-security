package org.springframework.security.crypto.password;

import java.security.MessageDigest;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.util.EncodingUtils;

/**
 * This {@link PasswordEncoder} is provided for legacy purposes only and is not considered
 * secure.
 *
 * A standard {@code PasswordEncoder} implementation that uses SHA-256 hashing with 1024
 * iterations and a random 8-byte random salt value. It uses an additional system-wide
 * secret value to provide additional protection.
 * <p>
 * The digest algorithm is invoked on the concatenated bytes of the salt, secret and
 * password.
 * <p>
 * If you are developing a new system,
 * {@link org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder} is a better
 * choice both in terms of security and interoperability with other languages.
 *
 * @author Keith Donald
 * @author Luke Taylor
 * @deprecated Digest based password encoding is not considered secure. Instead use an
 * adaptive one way function like BCryptPasswordEncoder, Pbkdf2PasswordEncoder, or
 * SCryptPasswordEncoder. Even better use {@link DelegatingPasswordEncoder} which supports
 * password upgrades. There are no plans to remove this support. It is deprecated to
 * indicate that this is a legacy implementation and using it is considered insecure.
 */
@Deprecated
public final class StandardPasswordEncoder implements PasswordEncoder {

	private static final int DEFAULT_ITERATIONS = 1024;

	private final Digester digester;

	private final byte[] secret;

	private final BytesKeyGenerator saltGenerator;

	/**
	 * Constructs a standard password encoder with no additional secret value.
	 */
	public StandardPasswordEncoder() {
		this("");
	}

	/**
	 * Constructs a standard password encoder with a secret value which is also included
	 * in the password hash.
	 * @param secret the secret key used in the encoding process (should not be shared)
	 */
	public StandardPasswordEncoder(CharSequence secret) {
		this("SHA-256", secret);
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return encode(rawPassword, this.saltGenerator.generateKey());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		byte[] digested = decode(encodedPassword);
		byte[] salt = EncodingUtils.subArray(digested, 0, this.saltGenerator.getKeyLength());
		return MessageDigest.isEqual(digested, digest(rawPassword, salt));
	}

	private StandardPasswordEncoder(String algorithm, CharSequence secret) {
		this.digester = new Digester(algorithm, DEFAULT_ITERATIONS);
		this.secret = Utf8.encode(secret);
		this.saltGenerator = KeyGenerators.secureRandom();
	}

	private String encode(CharSequence rawPassword, byte[] salt) {
		byte[] digest = digest(rawPassword, salt);
		return new String(Hex.encode(digest));
	}

	private byte[] digest(CharSequence rawPassword, byte[] salt) {
		byte[] digest = this.digester.digest(EncodingUtils.concatenate(salt, this.secret, Utf8.encode(rawPassword)));
		return EncodingUtils.concatenate(salt, digest);
	}

	private byte[] decode(CharSequence encodedPassword) {
		return Hex.decode(encodedPassword);
	}

}

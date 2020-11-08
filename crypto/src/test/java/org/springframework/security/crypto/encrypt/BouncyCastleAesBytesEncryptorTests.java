package org.springframework.security.crypto.encrypt;

import java.security.SecureRandom;
import java.util.UUID;

import org.bouncycastle.util.Arrays;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.keygen.KeyGenerators;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class BouncyCastleAesBytesEncryptorTests {

	private byte[] testData;

	private String password;

	private String salt;

	@Before
	public void setup() {
		// generate random password, salt, and test data
		SecureRandom secureRandom = new SecureRandom();
		this.password = UUID.randomUUID().toString();
		byte[] saltBytes = new byte[16];
		secureRandom.nextBytes(saltBytes);
		this.salt = new String(Hex.encode(saltBytes));
		this.testData = new byte[1024 * 1024];
		secureRandom.nextBytes(this.testData);
	}

	@Test
	public void bcCbcWithSecureIvGeneratesDifferentMessages() {
		BytesEncryptor bcEncryptor = new BouncyCastleAesCbcBytesEncryptor(this.password, this.salt);
		generatesDifferentCipherTexts(bcEncryptor);
	}

	@Test
	public void bcGcmWithSecureIvGeneratesDifferentMessages() {
		BytesEncryptor bcEncryptor = new BouncyCastleAesGcmBytesEncryptor(this.password, this.salt);
		generatesDifferentCipherTexts(bcEncryptor);
	}

	private void generatesDifferentCipherTexts(BytesEncryptor bcEncryptor) {
		byte[] encrypted1 = bcEncryptor.encrypt(this.testData);
		byte[] encrypted2 = bcEncryptor.encrypt(this.testData);
		Assert.assertFalse(Arrays.areEqual(encrypted1, encrypted2));
		byte[] decrypted1 = bcEncryptor.decrypt(encrypted1);
		byte[] decrypted2 = bcEncryptor.decrypt(encrypted2);
		Assert.assertArrayEquals(this.testData, decrypted1);
		Assert.assertArrayEquals(this.testData, decrypted2);
	}

	@Test
	public void bcCbcWithWrongLengthIv() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new BouncyCastleAesCbcBytesEncryptor(this.password, this.salt, KeyGenerators.secureRandom(8)));
	}

	@Test
	public void bcGcmWithWrongLengthIv() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new BouncyCastleAesGcmBytesEncryptor(this.password, this.salt, KeyGenerators.secureRandom(8)));
	}

}

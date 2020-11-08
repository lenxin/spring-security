package org.springframework.security.crypto.encrypt;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EncryptorsTests {

	@Test
	public void stronger() throws Exception {
		CryptoAssumptions.assumeGCMJCE();
		BytesEncryptor encryptor = Encryptors.stronger("password", "5c0744940b5c369b");
		byte[] result = encryptor.encrypt("text".getBytes("UTF-8"));
		assertThat(result).isNotNull();
		assertThat(new String(result).equals("text")).isFalse();
		assertThat(new String(encryptor.decrypt(result))).isEqualTo("text");
		assertThat(new String(result)).isNotEqualTo(new String(encryptor.encrypt("text".getBytes())));
	}

	@Test
	public void standard() throws Exception {
		CryptoAssumptions.assumeCBCJCE();
		BytesEncryptor encryptor = Encryptors.standard("password", "5c0744940b5c369b");
		byte[] result = encryptor.encrypt("text".getBytes("UTF-8"));
		assertThat(result).isNotNull();
		assertThat(new String(result).equals("text")).isFalse();
		assertThat(new String(encryptor.decrypt(result))).isEqualTo("text");
		assertThat(new String(result)).isNotEqualTo(new String(encryptor.encrypt("text".getBytes())));
	}

	@Test
	public void preferred() {
		CryptoAssumptions.assumeGCMJCE();
		TextEncryptor encryptor = Encryptors.delux("password", "5c0744940b5c369b");
		String result = encryptor.encrypt("text");
		assertThat(result).isNotNull();
		assertThat(result.equals("text")).isFalse();
		assertThat(encryptor.decrypt(result)).isEqualTo("text");
		assertThat(result.equals(encryptor.encrypt("text"))).isFalse();
	}

	@Test
	public void text() {
		CryptoAssumptions.assumeCBCJCE();
		TextEncryptor encryptor = Encryptors.text("password", "5c0744940b5c369b");
		String result = encryptor.encrypt("text");
		assertThat(result).isNotNull();
		assertThat(result.equals("text")).isFalse();
		assertThat(encryptor.decrypt(result)).isEqualTo("text");
		assertThat(result.equals(encryptor.encrypt("text"))).isFalse();
	}

	@Test
	public void queryableText() {
		CryptoAssumptions.assumeCBCJCE();
		TextEncryptor encryptor = Encryptors.queryableText("password", "5c0744940b5c369b");
		String result = encryptor.encrypt("text");
		assertThat(result).isNotNull();
		assertThat(result.equals("text")).isFalse();
		assertThat(encryptor.decrypt(result)).isEqualTo("text");
		assertThat(result.equals(encryptor.encrypt("text"))).isTrue();
	}

	@Test
	public void noOpText() {
		TextEncryptor encryptor = Encryptors.noOpText();
		assertThat(encryptor.encrypt("text")).isEqualTo("text");
		assertThat(encryptor.decrypt("text")).isEqualTo("text");
	}

}

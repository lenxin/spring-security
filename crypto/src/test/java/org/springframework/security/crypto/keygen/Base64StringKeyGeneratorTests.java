package org.springframework.security.crypto.keygen;

import java.util.Base64;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class Base64StringKeyGeneratorTests {

	@Test
	public void constructorIntWhenLessThan32ThenIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Base64StringKeyGenerator(31));
	}

	@Test
	public void constructorEncoderWhenEncoderNullThenThrowsIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new Base64StringKeyGenerator(null));
	}

	@Test
	public void generateKeyWhenDefaultConstructorThen32Bytes() {
		String result = new Base64StringKeyGenerator().generateKey();
		assertThat(Base64.getDecoder().decode(result.getBytes())).hasSize(32);
	}

	@Test
	public void generateKeyWhenCustomKeySizeThen32Bytes() {
		int size = 40;
		String result = new Base64StringKeyGenerator(size).generateKey();
		assertThat(Base64.getDecoder().decode(result.getBytes())).hasSize(size);
	}

	@Test
	public void generateKeyWhenBase64Then32Bytes() {
		String result = new Base64StringKeyGenerator(Base64.getUrlEncoder()).generateKey();
		assertThat(Base64.getUrlDecoder().decode(result.getBytes())).hasSize(32);
	}

	@Test
	public void generateKeyWhenBase64AndCustomKeySizeThen32Bytes() {
		int size = 40;
		String result = new Base64StringKeyGenerator(Base64.getUrlEncoder(), size).generateKey();
		assertThat(Base64.getUrlDecoder().decode(result.getBytes())).hasSize(size);
	}

}

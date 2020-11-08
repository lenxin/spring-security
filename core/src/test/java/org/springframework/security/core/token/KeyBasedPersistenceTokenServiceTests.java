package org.springframework.security.core.token;

import java.security.SecureRandom;
import java.util.Date;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link KeyBasedPersistenceTokenService}.
 *
 * @author Ben Alex
 */
public class KeyBasedPersistenceTokenServiceTests {

	private KeyBasedPersistenceTokenService getService() {
		SecureRandomFactoryBean fb = new SecureRandomFactoryBean();
		KeyBasedPersistenceTokenService service = new KeyBasedPersistenceTokenService();
		service.setServerSecret("MY:SECRET$$$#");
		service.setServerInteger(454545);
		try {
			SecureRandom rnd = fb.getObject();
			service.setSecureRandom(rnd);
			service.afterPropertiesSet();
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return service;
	}

	@Test
	public void testOperationWithSimpleExtendedInformation() {
		KeyBasedPersistenceTokenService service = getService();
		Token token = service.allocateToken("Hello world");
		Token result = service.verifyToken(token.getKey());
		assertThat(result).isEqualTo(token);
	}

	@Test
	public void testOperationWithComplexExtendedInformation() {
		KeyBasedPersistenceTokenService service = getService();
		Token token = service.allocateToken("Hello:world:::");
		Token result = service.verifyToken(token.getKey());
		assertThat(result).isEqualTo(token);
	}

	@Test
	public void testOperationWithEmptyRandomNumber() {
		KeyBasedPersistenceTokenService service = getService();
		service.setPseudoRandomNumberBytes(0);
		Token token = service.allocateToken("Hello:world:::");
		Token result = service.verifyToken(token.getKey());
		assertThat(result).isEqualTo(token);
	}

	@Test
	public void testOperationWithNoExtendedInformation() {
		KeyBasedPersistenceTokenService service = getService();
		Token token = service.allocateToken("");
		Token result = service.verifyToken(token.getKey());
		assertThat(result).isEqualTo(token);
	}

	@Test
	public void testOperationWithMissingKey() {
		KeyBasedPersistenceTokenService service = getService();
		assertThatIllegalArgumentException().isThrownBy(() -> {
			Token token = new DefaultToken("", new Date().getTime(), "");
			service.verifyToken(token.getKey());
		});
	}

	@Test
	public void testOperationWithTamperedKey() {
		KeyBasedPersistenceTokenService service = getService();
		Token goodToken = service.allocateToken("");
		String fake = goodToken.getKey().toUpperCase();
		Token token = new DefaultToken(fake, new Date().getTime(), "");
		assertThatIllegalArgumentException().isThrownBy(() -> service.verifyToken(token.getKey()));
	}

}

package org.springframework.security.core.token;

import java.util.Date;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link DefaultToken}.
 *
 * @author Ben Alex
 *
 */
public class DefaultTokenTests {

	@Test
	public void testEquality() {
		String key = "key";
		long created = new Date().getTime();
		String extendedInformation = "extended";
		DefaultToken t1 = new DefaultToken(key, created, extendedInformation);
		DefaultToken t2 = new DefaultToken(key, created, extendedInformation);
		assertThat(t2).isEqualTo(t1);
	}

	@Test
	public void testRejectsNullExtendedInformation() {
		String key = "key";
		long created = new Date().getTime();
		assertThatIllegalArgumentException().isThrownBy(() -> new DefaultToken(key, created, null));
	}

	@Test
	public void testEqualityWithDifferentExtendedInformation3() {
		String key = "key";
		long created = new Date().getTime();
		DefaultToken t1 = new DefaultToken(key, created, "length1");
		DefaultToken t2 = new DefaultToken(key, created, "longerLength2");
		assertThat(t1).isNotEqualTo(t2);
	}

}

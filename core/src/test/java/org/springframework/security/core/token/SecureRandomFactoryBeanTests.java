package org.springframework.security.core.token;

import java.security.SecureRandom;

import org.junit.Test;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link SecureRandomFactoryBean}.
 *
 * @author Ben Alex
 *
 */
public class SecureRandomFactoryBeanTests {

	@Test
	public void testObjectType() {
		SecureRandomFactoryBean factory = new SecureRandomFactoryBean();
		assertThat(factory.getObjectType()).isEqualTo(SecureRandom.class);
	}

	@Test
	public void testIsSingleton() {
		SecureRandomFactoryBean factory = new SecureRandomFactoryBean();
		assertThat(factory.isSingleton()).isFalse();
	}

	@Test
	public void testCreatesUsingDefaults() throws Exception {
		SecureRandomFactoryBean factory = new SecureRandomFactoryBean();
		Object result = factory.getObject();
		assertThat(result).isInstanceOf(SecureRandom.class);
		int rnd = ((SecureRandom) result).nextInt();
		assertThat(rnd).isNotEqualTo(0);
	}

	@Test
	public void testCreatesUsingSeed() throws Exception {
		SecureRandomFactoryBean factory = new SecureRandomFactoryBean();
		Resource resource = new ClassPathResource(
				"org/springframework/security/core/token/SecureRandomFactoryBeanTests.class");
		assertThat(resource).isNotNull();
		factory.setSeed(resource);
		SecureRandom first = factory.getObject();
		SecureRandom second = factory.getObject();
		assertThat(first.nextInt()).isNotEqualTo(0).isNotEqualTo(second.nextInt());
	}

}

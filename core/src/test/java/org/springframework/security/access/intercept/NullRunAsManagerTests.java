package org.springframework.security.access.intercept;

import org.junit.Test;

import org.springframework.security.access.SecurityConfig;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link NullRunAsManager}.
 *
 * @author Ben Alex
 */
public class NullRunAsManagerTests {

	@Test
	public void testAlwaysReturnsNull() {
		NullRunAsManager runAs = new NullRunAsManager();
		assertThat(runAs.buildRunAs(null, null, null)).isNull();
	}

	@Test
	public void testAlwaysSupportsClass() {
		NullRunAsManager runAs = new NullRunAsManager();
		assertThat(runAs.supports(String.class)).isTrue();
	}

	@Test
	public void testNeverSupportsAttribute() {
		NullRunAsManager runAs = new NullRunAsManager();
		assertThat(runAs.supports(new SecurityConfig("X"))).isFalse();
	}

}

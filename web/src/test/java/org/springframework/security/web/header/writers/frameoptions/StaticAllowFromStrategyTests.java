package org.springframework.security.web.header.writers.frameoptions;

import java.net.URI;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the StaticAllowFromStrategy.
 *
 * @author Marten Deinum
 * @since 3.2
 */
public class StaticAllowFromStrategyTests {

	@Test
	public void shouldReturnUri() {
		String uri = "https://www.test.com";
		StaticAllowFromStrategy strategy = new StaticAllowFromStrategy(URI.create(uri));
		assertThat(strategy.getAllowFromValue(new MockHttpServletRequest())).isEqualTo(uri);
	}

}

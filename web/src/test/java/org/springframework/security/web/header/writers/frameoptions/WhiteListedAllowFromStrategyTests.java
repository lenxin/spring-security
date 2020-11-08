package org.springframework.security.web.header.writers.frameoptions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Test for the {@code WhiteListedAllowFromStrategy}.
 *
 * @author Marten Deinum
 * @since 3.2
 */
public class WhiteListedAllowFromStrategyTests {

	@Test
	public void emptyListShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new WhiteListedAllowFromStrategy(new ArrayList<>()));
	}

	@Test
	public void nullListShouldThrowException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new WhiteListedAllowFromStrategy(null));
	}

	@Test
	public void listWithSingleElementShouldMatch() {
		List<String> allowed = new ArrayList<>();
		allowed.add("https://www.test.com");
		WhiteListedAllowFromStrategy strategy = new WhiteListedAllowFromStrategy(allowed);
		strategy.setAllowFromParameterName("from");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("from", "https://www.test.com");
		String result = strategy.getAllowFromValue(request);
		assertThat(result).isEqualTo("https://www.test.com");
	}

	@Test
	public void listWithMultipleElementShouldMatch() {
		List<String> allowed = new ArrayList<>();
		allowed.add("https://www.test.com");
		allowed.add("https://www.springsource.org");
		WhiteListedAllowFromStrategy strategy = new WhiteListedAllowFromStrategy(allowed);
		strategy.setAllowFromParameterName("from");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("from", "https://www.test.com");
		String result = strategy.getAllowFromValue(request);
		assertThat(result).isEqualTo("https://www.test.com");
	}

	@Test
	public void listWithSingleElementShouldNotMatch() {
		List<String> allowed = new ArrayList<>();
		allowed.add("https://www.test.com");
		WhiteListedAllowFromStrategy strategy = new WhiteListedAllowFromStrategy(allowed);
		strategy.setAllowFromParameterName("from");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setParameter("from", "https://www.test123.com");
		String result = strategy.getAllowFromValue(request);
		assertThat(result).isEqualTo("DENY");
	}

	@Test
	public void requestWithoutParameterShouldNotMatch() {
		List<String> allowed = new ArrayList<>();
		allowed.add("https://www.test.com");
		WhiteListedAllowFromStrategy strategy = new WhiteListedAllowFromStrategy(allowed);
		strategy.setAllowFromParameterName("from");
		MockHttpServletRequest request = new MockHttpServletRequest();
		String result = strategy.getAllowFromValue(request);
		assertThat(result).isEqualTo("DENY");
	}

}

package org.springframework.security.web.header.writers.frameoptions;

import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
public class AbstractRequestParameterAllowFromStrategyTests {

	private MockHttpServletRequest request;

	@Before
	public void setup() {
		this.request = new MockHttpServletRequest();
	}

	@Test
	public void nullAllowFromParameterValue() {
		RequestParameterAllowFromStrategyStub strategy = new RequestParameterAllowFromStrategyStub(true);
		assertThat(strategy.getAllowFromValue(this.request)).isEqualTo("DENY");
	}

	@Test
	public void emptyAllowFromParameterValue() {
		this.request.setParameter("x-frames-allow-from", "");
		RequestParameterAllowFromStrategyStub strategy = new RequestParameterAllowFromStrategyStub(true);
		assertThat(strategy.getAllowFromValue(this.request)).isEqualTo("DENY");
	}

	@Test
	public void emptyAllowFromCustomParameterValue() {
		String customParam = "custom";
		this.request.setParameter(customParam, "");
		RequestParameterAllowFromStrategyStub strategy = new RequestParameterAllowFromStrategyStub(true);
		strategy.setAllowFromParameterName(customParam);
		assertThat(strategy.getAllowFromValue(this.request)).isEqualTo("DENY");
	}

	@Test
	public void allowFromParameterValueAllowed() {
		String value = "https://example.com";
		this.request.setParameter("x-frames-allow-from", value);
		RequestParameterAllowFromStrategyStub strategy = new RequestParameterAllowFromStrategyStub(true);
		assertThat(strategy.getAllowFromValue(this.request)).isEqualTo(value);
	}

	@Test
	public void allowFromParameterValueDenied() {
		String value = "https://example.com";
		this.request.setParameter("x-frames-allow-from", value);
		RequestParameterAllowFromStrategyStub strategy = new RequestParameterAllowFromStrategyStub(false);
		assertThat(strategy.getAllowFromValue(this.request)).isEqualTo("DENY");
	}

	private static class RequestParameterAllowFromStrategyStub extends AbstractRequestParameterAllowFromStrategy {

		private boolean match;

		RequestParameterAllowFromStrategyStub(boolean match) {
			this.match = match;
		}

		@Override
		protected boolean allowed(String allowFromOrigin) {
			return this.match;
		}

	}

}

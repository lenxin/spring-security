package org.springframework.security.web.csrf;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 *
 */
public class DefaultCsrfTokenTests {

	private final String headerName = "headerName";

	private final String parameterName = "parameterName";

	private final String tokenValue = "tokenValue";

	@Test
	public void constructorNullHeaderName() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultCsrfToken(null, this.parameterName, this.tokenValue));
	}

	@Test
	public void constructorEmptyHeaderName() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultCsrfToken("", this.parameterName, this.tokenValue));
	}

	@Test
	public void constructorNullParameterName() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultCsrfToken(this.headerName, null, this.tokenValue));
	}

	@Test
	public void constructorEmptyParameterName() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultCsrfToken(this.headerName, "", this.tokenValue));
	}

	@Test
	public void constructorNullTokenValue() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultCsrfToken(this.headerName, this.parameterName, null));
	}

	@Test
	public void constructorEmptyTokenValue() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new DefaultCsrfToken(this.headerName, this.parameterName, ""));
	}

}

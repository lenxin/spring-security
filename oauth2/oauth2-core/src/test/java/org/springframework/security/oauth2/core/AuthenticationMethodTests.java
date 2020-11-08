package org.springframework.security.oauth2.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link AuthenticationMethod}.
 *
 * @author MyeongHyeon Lee
 */
public class AuthenticationMethodTests {

	@Test
	public void constructorWhenValueIsNullThenThrowIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AuthenticationMethod(null))
				.withMessage("value cannot be empty");
	}

	@Test
	public void getValueWhenHeaderAuthenticationTypeThenReturnHeader() {
		assertThat(AuthenticationMethod.HEADER.getValue()).isEqualTo("header");
	}

	@Test
	public void getValueWhenFormAuthenticationTypeThenReturnForm() {
		assertThat(AuthenticationMethod.FORM.getValue()).isEqualTo("form");
	}

	@Test
	public void getValueWhenFormAuthenticationTypeThenReturnQuery() {
		assertThat(AuthenticationMethod.QUERY.getValue()).isEqualTo("query");
	}

}

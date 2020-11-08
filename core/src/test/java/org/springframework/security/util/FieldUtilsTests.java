package org.springframework.security.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * @author Luke Taylor
 */
public class FieldUtilsTests {

	@Test
	public void gettingAndSettingProtectedFieldIsSuccessful() throws Exception {
		Object tc = new TestClass();
		assertThat(FieldUtils.getProtectedFieldValue("protectedField", tc)).isEqualTo("x");
		assertThat(FieldUtils.getFieldValue(tc, "nested.protectedField")).isEqualTo("z");
		FieldUtils.setProtectedFieldValue("protectedField", tc, "y");
		assertThat(FieldUtils.getProtectedFieldValue("protectedField", tc)).isEqualTo("y");
		assertThatIllegalStateException().isThrownBy(() -> FieldUtils.getProtectedFieldValue("nonExistentField", tc));
	}

	@SuppressWarnings("unused")
	static class TestClass {

		private String protectedField = "x";

		private Nested nested = new Nested();

	}

	@SuppressWarnings("unused")
	static class Nested {

		private String protectedField = "z";

	}

}

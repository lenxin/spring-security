package org.springframework.security.web.jackson2;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.security.web.csrf.DefaultCsrfToken;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Jitendra Singh
 * @since 4.2
 */
public class DefaultCsrfTokenMixinTests extends AbstractMixinTests {

	// @formatter:off
	public static final String CSRF_JSON = "{"
		+ "\"@class\": \"org.springframework.security.web.csrf.DefaultCsrfToken\", "
		+ "\"headerName\": \"csrf-header\", "
		+ "\"parameterName\": \"_csrf\", "
		+ "\"token\": \"1\""
	+ "}";
	// @formatter:on
	@Test
	public void defaultCsrfTokenSerializedTest() throws JsonProcessingException, JSONException {
		DefaultCsrfToken token = new DefaultCsrfToken("csrf-header", "_csrf", "1");
		String serializedJson = this.mapper.writeValueAsString(token);
		JSONAssert.assertEquals(CSRF_JSON, serializedJson, true);
	}

	@Test
	public void defaultCsrfTokenDeserializeTest() throws IOException {
		DefaultCsrfToken token = this.mapper.readValue(CSRF_JSON, DefaultCsrfToken.class);
		assertThat(token).isNotNull();
		assertThat(token.getHeaderName()).isEqualTo("csrf-header");
		assertThat(token.getParameterName()).isEqualTo("_csrf");
		assertThat(token.getToken()).isEqualTo("1");
	}

	@Test
	public void defaultCsrfTokenDeserializeWithoutClassTest() throws IOException {
		String tokenJson = "{\"headerName\": \"csrf-header\", \"parameterName\": \"_csrf\", \"token\": \"1\"}";
		assertThatExceptionOfType(JsonMappingException.class)
				.isThrownBy(() -> this.mapper.readValue(tokenJson, DefaultCsrfToken.class));
	}

	@Test
	public void defaultCsrfTokenDeserializeNullValuesTest() throws IOException {
		String tokenJson = "{\"@class\": \"org.springframework.security.web.csrf.DefaultCsrfToken\", \"headerName\": \"\", \"parameterName\": null, \"token\": \"1\"}";
		assertThatExceptionOfType(JsonMappingException.class)
				.isThrownBy(() -> this.mapper.readValue(tokenJson, DefaultCsrfToken.class));
	}

}

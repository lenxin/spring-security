package org.springframework.security.jackson2;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.security.authentication.BadCredentialsException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Yannick Lombardi
 * @since 5.0
 */
public class BadCredentialsExceptionMixinTests extends AbstractMixinTests {

	// @formatter:off
	private static final String EXCEPTION_JSON = "{"
		+ "\"@class\": \"org.springframework.security.authentication.BadCredentialsException\","
		+ "\"localizedMessage\": \"message\", "
		+ "\"message\": \"message\", "
		+ "\"suppressed\": [\"[Ljava.lang.Throwable;\",[]]"
		+ "}";
	// @formatter:on
	@Test
	public void serializeBadCredentialsExceptionMixinTest() throws JsonProcessingException, JSONException {
		BadCredentialsException exception = new BadCredentialsException("message");
		String serializedJson = this.mapper.writeValueAsString(exception);
		JSONAssert.assertEquals(EXCEPTION_JSON, serializedJson, true);
	}

	@Test
	public void deserializeBadCredentialsExceptionMixinTest() throws IOException {
		BadCredentialsException exception = this.mapper.readValue(EXCEPTION_JSON, BadCredentialsException.class);
		assertThat(exception).isNotNull();
		assertThat(exception.getCause()).isNull();
		assertThat(exception.getMessage()).isEqualTo("message");
		assertThat(exception.getLocalizedMessage()).isEqualTo("message");
	}

}

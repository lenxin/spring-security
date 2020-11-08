package org.springframework.security.web.jackson2;

import java.io.IOException;

import javax.servlet.http.Cookie;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jitendra Singh
 * @since 4.2
 */
public class CookieMixinTests extends AbstractMixinTests {

	// @formatter:off
	private static final String COOKIE_JSON = "{"
		+ "\"@class\": \"javax.servlet.http.Cookie\", "
		+ "\"name\": \"demo\", "
		+ "\"value\": \"cookie1\","
		+ "\"comment\": null, "
		+ "\"maxAge\": -1, "
		+ "\"path\": null, "
		+ "\"secure\": false, "
		+ "\"version\": 0, "
		+ "\"isHttpOnly\": false, "
		+ "\"domain\": null"
	+ "}";
	// @formatter:on
	@Test
	public void serializeCookie() throws JsonProcessingException, JSONException {
		Cookie cookie = new Cookie("demo", "cookie1");
		String actualString = this.mapper.writeValueAsString(cookie);
		JSONAssert.assertEquals(COOKIE_JSON, actualString, true);
	}

	@Test
	public void deserializeCookie() throws IOException {
		Cookie cookie = this.mapper.readValue(COOKIE_JSON, Cookie.class);
		assertThat(cookie).isNotNull();
		assertThat(cookie.getName()).isEqualTo("demo");
		assertThat(cookie.getDomain()).isEqualTo("");
		assertThat(cookie.isHttpOnly()).isEqualTo(false);
	}

}

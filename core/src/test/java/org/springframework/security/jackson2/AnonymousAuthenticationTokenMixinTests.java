package org.springframework.security.jackson2;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Jitendra Singh
 * @since 4.2
 */
public class AnonymousAuthenticationTokenMixinTests extends AbstractMixinTests {

	private static final String HASH_KEY = "key";

	// @formatter:off
	private static final String ANONYMOUS_JSON = "{"
		+ "\"@class\": \"org.springframework.security.authentication.AnonymousAuthenticationToken\", "
		+ "\"details\": null,"
		+ "\"principal\": " + UserDeserializerTests.USER_JSON + ","
		+ "\"authenticated\": true, "
		+ "\"keyHash\": " + HASH_KEY.hashCode() + ","
		+ "\"authorities\": " + SimpleGrantedAuthorityMixinTests.AUTHORITIES_ARRAYLIST_JSON
	+ "}";
	// @formatter:on
	@Test
	public void serializeAnonymousAuthenticationTokenTest() throws JsonProcessingException, JSONException {
		User user = createDefaultUser();
		AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(HASH_KEY, user, user.getAuthorities());
		String actualJson = this.mapper.writeValueAsString(token);
		JSONAssert.assertEquals(ANONYMOUS_JSON, actualJson, true);
	}

	@Test
	public void deserializeAnonymousAuthenticationTokenTest() throws IOException {
		AnonymousAuthenticationToken token = this.mapper.readValue(ANONYMOUS_JSON, AnonymousAuthenticationToken.class);
		assertThat(token).isNotNull();
		assertThat(token.getKeyHash()).isEqualTo(HASH_KEY.hashCode());
		assertThat(token.getAuthorities()).isNotNull().hasSize(1).contains(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Test
	public void deserializeAnonymousAuthenticationTokenWithoutAuthoritiesTest() throws IOException {
		String jsonString = "{\"@class\": \"org.springframework.security.authentication.AnonymousAuthenticationToken\", \"details\": null,"
				+ "\"principal\": \"user\", \"authenticated\": true, \"keyHash\": " + HASH_KEY.hashCode() + ","
				+ "\"authorities\": [\"java.util.ArrayList\", []]}";
		assertThatExceptionOfType(JsonMappingException.class)
				.isThrownBy(() -> this.mapper.readValue(jsonString, AnonymousAuthenticationToken.class));
	}

	@Test
	public void serializeAnonymousAuthenticationTokenMixinAfterEraseCredentialTest()
			throws JsonProcessingException, JSONException {
		User user = createDefaultUser();
		AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(HASH_KEY, user, user.getAuthorities());
		token.eraseCredentials();
		String actualJson = this.mapper.writeValueAsString(token);
		JSONAssert.assertEquals(ANONYMOUS_JSON.replace(UserDeserializerTests.USER_PASSWORD, "null"), actualJson, true);
	}

}

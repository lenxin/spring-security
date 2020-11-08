package org.springframework.security.jackson2;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Jitendra Singh
 * @since 4.2
 */
public class SimpleGrantedAuthorityMixinTests extends AbstractMixinTests {

	// @formatter:off
	public static final String AUTHORITY_JSON = "{\"@class\": \"org.springframework.security.core.authority.SimpleGrantedAuthority\", \"authority\": \"ROLE_USER\"}";
	public static final String AUTHORITIES_ARRAYLIST_JSON = "[\"java.util.Collections$UnmodifiableRandomAccessList\", [" + AUTHORITY_JSON + "]]";
	public static final String AUTHORITIES_SET_JSON = "[\"java.util.Collections$UnmodifiableSet\", [" + AUTHORITY_JSON + "]]";
	public static final String NO_AUTHORITIES_ARRAYLIST_JSON = "[\"java.util.Collections$UnmodifiableRandomAccessList\", []]";
	public static final String EMPTY_AUTHORITIES_ARRAYLIST_JSON = "[\"java.util.Collections$EmptyList\", []]";
	public static final String NO_AUTHORITIES_SET_JSON = "[\"java.util.Collections$UnmodifiableSet\", []]";
	// @formatter:on
	@Test
	public void serializeSimpleGrantedAuthorityTest() throws JsonProcessingException, JSONException {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		String serializeJson = this.mapper.writeValueAsString(authority);
		JSONAssert.assertEquals(AUTHORITY_JSON, serializeJson, true);
	}

	@Test
	public void deserializeGrantedAuthorityTest() throws IOException {
		SimpleGrantedAuthority authority = this.mapper.readValue(AUTHORITY_JSON, SimpleGrantedAuthority.class);
		assertThat(authority).isNotNull();
		assertThat(authority.getAuthority()).isNotNull().isEqualTo("ROLE_USER");
	}

	@Test
	public void deserializeGrantedAuthorityWithoutRoleTest() throws IOException {
		String json = "{\"@class\": \"org.springframework.security.core.authority.SimpleGrantedAuthority\"}";
		assertThatExceptionOfType(JsonMappingException.class)
				.isThrownBy(() -> this.mapper.readValue(json, SimpleGrantedAuthority.class));
	}

}

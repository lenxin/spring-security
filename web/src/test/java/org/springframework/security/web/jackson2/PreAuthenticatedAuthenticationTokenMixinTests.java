package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.jackson2.SimpleGrantedAuthorityMixinTests;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 4.2
 */
public class PreAuthenticatedAuthenticationTokenMixinTests extends AbstractMixinTests {

	// @formatter:off
	private static final String PREAUTH_JSON = "{"
		+ "\"@class\": \"org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken\","
		+ "\"principal\": \"principal\", "
		+ "\"credentials\": \"credentials\", "
		+ "\"authenticated\": true, "
		+ "\"details\": null, "
		+ "\"authorities\": " + SimpleGrantedAuthorityMixinTests.AUTHORITIES_ARRAYLIST_JSON
	+ "}";
	// @formatter:on
	PreAuthenticatedAuthenticationToken expected;

	@Before
	public void setupExpected() {
		this.expected = new PreAuthenticatedAuthenticationToken("principal", "credentials",
				AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

	@Test
	public void serializeWhenPrincipalCredentialsAuthoritiesThenSuccess()
			throws JsonProcessingException, JSONException {
		String serializedJson = this.mapper.writeValueAsString(this.expected);
		JSONAssert.assertEquals(PREAUTH_JSON, serializedJson, true);
	}

	@Test
	public void deserializeAuthenticatedUsernamePasswordAuthenticationTokenMixinTest() throws Exception {
		PreAuthenticatedAuthenticationToken deserialized = this.mapper.readValue(PREAUTH_JSON,
				PreAuthenticatedAuthenticationToken.class);
		assertThat(deserialized).isNotNull();
		assertThat(deserialized.isAuthenticated()).isTrue();
		assertThat(deserialized.getAuthorities()).isEqualTo(this.expected.getAuthorities());
	}

}

package org.springframework.security.jackson2;

import java.io.IOException;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Jitendra Singh
 * @since 4.2
 */
public class RememberMeAuthenticationTokenMixinTests extends AbstractMixinTests {

	private static final String REMEMBERME_KEY = "rememberMe";

	// @formatter:off
	private static final String REMEMBERME_AUTH_JSON = "{"
		+ "\"@class\": \"org.springframework.security.authentication.RememberMeAuthenticationToken\", "
		+ "\"keyHash\": " + REMEMBERME_KEY.hashCode() + ", "
		+ "\"authenticated\": true, \"details\": null" + ", "
		+ "\"principal\": " + UserDeserializerTests.USER_JSON + ", "
		+ "\"authorities\": " + SimpleGrantedAuthorityMixinTests.AUTHORITIES_ARRAYLIST_JSON
	+ "}";
	// @formatter:on

	// @formatter:off
	private static final String REMEMBERME_AUTH_STRINGPRINCIPAL_JSON = "{"
		+ "\"@class\": \"org.springframework.security.authentication.RememberMeAuthenticationToken\","
		+ "\"keyHash\": " + REMEMBERME_KEY.hashCode() + ", "
		+ "\"authenticated\": true, "
		+ "\"details\": null,"
		+ "\"principal\": \"admin\", "
		+ "\"authorities\": " + SimpleGrantedAuthorityMixinTests.AUTHORITIES_ARRAYLIST_JSON
	+ "}";
	// @formatter:on

	@Test
	public void testWithNullPrincipal() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new RememberMeAuthenticationToken("key", null, Collections.<GrantedAuthority>emptyList()));
	}

	@Test
	public void testWithNullKey() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> new RememberMeAuthenticationToken(null, "principal", Collections.<GrantedAuthority>emptyList()));
	}

	@Test
	public void serializeRememberMeAuthenticationToken() throws JsonProcessingException, JSONException {
		RememberMeAuthenticationToken token = new RememberMeAuthenticationToken(REMEMBERME_KEY, "admin",
				Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
		String actualJson = this.mapper.writeValueAsString(token);
		JSONAssert.assertEquals(REMEMBERME_AUTH_STRINGPRINCIPAL_JSON, actualJson, true);
	}

	@Test
	public void serializeRememberMeAuthenticationWithUserToken() throws JsonProcessingException, JSONException {
		User user = createDefaultUser();
		RememberMeAuthenticationToken token = new RememberMeAuthenticationToken(REMEMBERME_KEY, user,
				user.getAuthorities());
		String actualJson = this.mapper.writeValueAsString(token);
		JSONAssert.assertEquals(String.format(REMEMBERME_AUTH_JSON, "\"password\""), actualJson, true);
	}

	@Test
	public void serializeRememberMeAuthenticationWithUserTokenAfterEraseCredential()
			throws JsonProcessingException, JSONException {
		User user = createDefaultUser();
		RememberMeAuthenticationToken token = new RememberMeAuthenticationToken(REMEMBERME_KEY, user,
				user.getAuthorities());
		token.eraseCredentials();
		String actualJson = this.mapper.writeValueAsString(token);
		JSONAssert.assertEquals(REMEMBERME_AUTH_JSON.replace(UserDeserializerTests.USER_PASSWORD, "null"), actualJson,
				true);
	}

	@Test
	public void deserializeRememberMeAuthenticationToken() throws IOException {
		RememberMeAuthenticationToken token = this.mapper.readValue(REMEMBERME_AUTH_STRINGPRINCIPAL_JSON,
				RememberMeAuthenticationToken.class);
		assertThat(token).isNotNull();
		assertThat(token.getPrincipal()).isNotNull().isEqualTo("admin").isEqualTo(token.getName());
		assertThat(token.getAuthorities()).hasSize(1).contains(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Test
	public void deserializeRememberMeAuthenticationTokenWithUserTest() throws IOException {
		RememberMeAuthenticationToken token = this.mapper.readValue(String.format(REMEMBERME_AUTH_JSON, "\"password\""),
				RememberMeAuthenticationToken.class);
		assertThat(token).isNotNull();
		assertThat(token.getPrincipal()).isNotNull().isInstanceOf(User.class);
		assertThat(((User) token.getPrincipal()).getUsername()).isEqualTo("admin");
		assertThat(((User) token.getPrincipal()).getPassword()).isEqualTo("1234");
		assertThat(((User) token.getPrincipal()).getAuthorities()).hasSize(1)
				.contains(new SimpleGrantedAuthority("ROLE_USER"));
		assertThat(token.getAuthorities()).hasSize(1).contains(new SimpleGrantedAuthority("ROLE_USER"));
		assertThat(((User) token.getPrincipal()).isEnabled()).isEqualTo(true);
	}

}

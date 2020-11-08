package org.springframework.security.jackson2;

import java.io.IOException;
import java.util.Collections;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Jitendra Singh
 * @since 4.2
 */
public class UserDeserializerTests extends AbstractMixinTests {

	public static final String USER_PASSWORD = "\"1234\"";

	// @formatter:off
	public static final String USER_JSON = "{"
		+ "\"@class\": \"org.springframework.security.core.userdetails.User\", "
		+ "\"username\": \"admin\","
		+ " \"password\": " + USER_PASSWORD + ", "
		+ "\"accountNonExpired\": true, "
		+ "\"accountNonLocked\": true, "
		+ "\"credentialsNonExpired\": true, "
		+ "\"enabled\": true, "
		+ "\"authorities\": " + SimpleGrantedAuthorityMixinTests.AUTHORITIES_SET_JSON
	+ "}";
	// @formatter:on
	@Test
	public void serializeUserTest() throws JsonProcessingException, JSONException {
		User user = createDefaultUser();
		String userJson = this.mapper.writeValueAsString(user);
		JSONAssert.assertEquals(userWithPasswordJson(user.getPassword()), userJson, true);
	}

	@Test
	public void serializeUserWithoutAuthority() throws JsonProcessingException, JSONException {
		User user = new User("admin", "1234", Collections.<GrantedAuthority>emptyList());
		String userJson = this.mapper.writeValueAsString(user);
		JSONAssert.assertEquals(userWithNoAuthoritiesJson(), userJson, true);
	}

	@Test
	public void deserializeUserWithNullPasswordEmptyAuthorityTest() throws IOException {
		String userJsonWithoutPasswordString = USER_JSON.replace(SimpleGrantedAuthorityMixinTests.AUTHORITIES_SET_JSON,
				"[]");
		assertThatIllegalArgumentException()
				.isThrownBy(() -> this.mapper.readValue(userJsonWithoutPasswordString, User.class));
	}

	@Test
	public void deserializeUserWithNullPasswordNoAuthorityTest() throws Exception {
		String userJsonWithoutPasswordString = removeNode(userWithNoAuthoritiesJson(), this.mapper, "password");
		User user = this.mapper.readValue(userJsonWithoutPasswordString, User.class);
		assertThat(user).isNotNull();
		assertThat(user.getUsername()).isEqualTo("admin");
		assertThat(user.getPassword()).isNull();
		assertThat(user.getAuthorities()).isEmpty();
		assertThat(user.isEnabled()).isEqualTo(true);
	}

	@Test
	public void deserializeUserWithNoClassIdInAuthoritiesTest() throws Exception {
		String userJson = USER_JSON.replace(SimpleGrantedAuthorityMixinTests.AUTHORITIES_SET_JSON,
				"[{\"authority\": \"ROLE_USER\"}]");
		assertThatIllegalArgumentException().isThrownBy(() -> this.mapper.readValue(userJson, User.class));
	}

	@Test
	public void deserializeUserWithClassIdInAuthoritiesTest() throws IOException {
		User user = this.mapper.readValue(userJson(), User.class);
		assertThat(user).isNotNull();
		assertThat(user.getUsername()).isEqualTo("admin");
		assertThat(user.getPassword()).isEqualTo("1234");
		assertThat(user.getAuthorities()).hasSize(1).contains(new SimpleGrantedAuthority("ROLE_USER"));
	}

	private String removeNode(String json, ObjectMapper mapper, String toRemove) throws Exception {
		ObjectNode node = mapper.getFactory().createParser(json).readValueAsTree();
		node.remove(toRemove);
		String result = mapper.writeValueAsString(node);
		JSONAssert.assertNotEquals(json, result, false);
		return result;
	}

	public static String userJson() {
		return USER_JSON;
	}

	public static String userWithPasswordJson(String password) {
		return userJson().replaceAll(Pattern.quote(USER_PASSWORD), "\"" + password + "\"");
	}

	public static String userWithNoAuthoritiesJson() {
		return userJson().replace(SimpleGrantedAuthorityMixinTests.AUTHORITIES_SET_JSON,
				SimpleGrantedAuthorityMixinTests.NO_AUTHORITIES_SET_JSON);
	}

}

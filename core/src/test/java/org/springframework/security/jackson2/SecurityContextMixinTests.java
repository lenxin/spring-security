package org.springframework.security.jackson2;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jitendra Singh
 * @since 4.2
 */
public class SecurityContextMixinTests extends AbstractMixinTests {

	// @formatter:off
	public static final String SECURITY_CONTEXT_JSON = "{"
		+ "\"@class\": \"org.springframework.security.core.context.SecurityContextImpl\", "
		+ "\"authentication\": " + UsernamePasswordAuthenticationTokenMixinTests.AUTHENTICATED_STRINGPRINCIPAL_JSON
	+ "}";
	// @formatter:on
	@Test
	public void securityContextSerializeTest() throws JsonProcessingException, JSONException {
		SecurityContext context = new SecurityContextImpl();
		context.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "1234",
				Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))));
		String actualJson = this.mapper.writeValueAsString(context);
		JSONAssert.assertEquals(SECURITY_CONTEXT_JSON, actualJson, true);
	}

	@Test
	public void securityContextDeserializeTest() throws IOException {
		SecurityContext context = this.mapper.readValue(SECURITY_CONTEXT_JSON, SecurityContextImpl.class);
		assertThat(context).isNotNull();
		assertThat(context.getAuthentication()).isNotNull().isInstanceOf(UsernamePasswordAuthenticationToken.class);
		assertThat(context.getAuthentication().getPrincipal()).isEqualTo("admin");
		assertThat(context.getAuthentication().getCredentials()).isEqualTo("1234");
		assertThat(context.getAuthentication().isAuthenticated()).isTrue();
		Collection authorities = context.getAuthentication().getAuthorities();
		assertThat(authorities).hasSize(1);
		assertThat(authorities).contains(new SimpleGrantedAuthority("ROLE_USER"));
	}

}

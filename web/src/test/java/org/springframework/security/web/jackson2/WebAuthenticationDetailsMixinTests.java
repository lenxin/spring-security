package org.springframework.security.web.jackson2;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Jitendra Singh
 * @since 4.2
 */
public class WebAuthenticationDetailsMixinTests extends AbstractMixinTests {

	// @formatter:off
	private static final String AUTHENTICATION_DETAILS_JSON = "{"
		+ "\"@class\": \"org.springframework.security.web.authentication.WebAuthenticationDetails\","
		+ "\"sessionId\": \"1\", "
		+ "\"remoteAddress\": "
		+ "\"/localhost\""
	+ "}";
	// @formatter:on
	@Test
	public void buildWebAuthenticationDetailsUsingDifferentConstructors() throws IOException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("localhost");
		request.setSession(new MockHttpSession(null, "1"));
		WebAuthenticationDetails details = new WebAuthenticationDetails(request);
		WebAuthenticationDetails authenticationDetails = this.mapper.readValue(AUTHENTICATION_DETAILS_JSON,
				WebAuthenticationDetails.class);
		assertThat(details.equals(authenticationDetails));
	}

	@Test
	public void webAuthenticationDetailsSerializeTest() throws JsonProcessingException, JSONException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("/localhost");
		request.setSession(new MockHttpSession(null, "1"));
		WebAuthenticationDetails details = new WebAuthenticationDetails(request);
		String actualJson = this.mapper.writeValueAsString(details);
		JSONAssert.assertEquals(AUTHENTICATION_DETAILS_JSON, actualJson, true);
	}

	@Test
	public void webAuthenticationDetailsDeserializeTest() throws IOException {
		WebAuthenticationDetails details = this.mapper.readValue(AUTHENTICATION_DETAILS_JSON,
				WebAuthenticationDetails.class);
		assertThat(details).isNotNull();
		assertThat(details.getRemoteAddress()).isEqualTo("/localhost");
		assertThat(details.getSessionId()).isEqualTo("1");
	}

}

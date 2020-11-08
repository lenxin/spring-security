package sample.web;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * Tests for {@link OAuth2LoginController}
 *
 * @author Josh Cummings
 */
@RunWith(SpringRunner.class)
@WebMvcTest(OAuth2LoginController.class)
public class OAuth2LoginControllerTests {

	@Autowired
	MockMvc mvc;

	@Test
	public void rootWhenAuthenticatedReturnsUserAndClient() throws Exception {
		this.mvc.perform(get("/").with(oauth2Login()))
			.andExpect(model().attribute("userName", "user"))
			.andExpect(model().attribute("clientName", "test"))
			.andExpect(model().attribute("userAttributes", Collections.singletonMap("sub", "user")));
	}

	@Test
	public void rootWhenOverridingClientRegistrationReturnsAccordingly() throws Exception {
		ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("test")
				.authorizationGrantType(AuthorizationGrantType.PASSWORD)
				.clientId("my-client-id")
				.clientName("my-client-name")
				.tokenUri("https://token-uri.example.org")
				.build();

		this.mvc.perform(get("/").with(oauth2Login()
				.clientRegistration(clientRegistration)
				.attributes((a) -> a.put("sub", "spring-security"))))
				.andExpect(model().attribute("userName", "spring-security"))
				.andExpect(model().attribute("clientName", "my-client-name"))
				.andExpect(model().attribute("userAttributes", Collections.singletonMap("sub", "spring-security")));
	}
}

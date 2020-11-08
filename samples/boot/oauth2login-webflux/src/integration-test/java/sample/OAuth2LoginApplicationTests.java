package sample;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

/**
 * Tests for {@link ReactiveOAuth2LoginApplication}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class OAuth2LoginApplicationTests {

	@Autowired
	WebTestClient test;

	@Autowired
	ReactiveClientRegistrationRepository clientRegistrationRepository;

	@Test
	public void requestWhenMockOidcLoginThenIndex() {
		this.clientRegistrationRepository.findByRegistrationId("github")
				.map((clientRegistration) ->
						this.test.mutateWith(mockOAuth2Login().clientRegistration(clientRegistration))
							.get().uri("/")
							.exchange()
							.expectBody(String.class).value(containsString("GitHub"))
				).block();
	}
}

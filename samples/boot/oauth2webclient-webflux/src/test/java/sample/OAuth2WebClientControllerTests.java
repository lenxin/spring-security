package sample;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import sample.config.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Client;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@WebFluxTest
@Import(SecurityConfig.class)
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
public class OAuth2WebClientControllerTests {
	private static MockWebServer web = new MockWebServer();

	@Autowired
	private WebTestClient client;

	@AfterClass
	public static void shutdown() throws Exception {
		web.shutdown();
	}

	@Test
	public void explicitWhenAuthenticatedThenUsesClientIdRegistration() throws Exception {
		web.enqueue(new MockResponse().setBody("body").setResponseCode(200));
		this.client.mutateWith(mockOAuth2Login())
				.mutateWith(mockOAuth2Client("client-id"))
				.get().uri("/webclient/explicit")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void implicitWhenAuthenticatedThenUsesDefaultRegistration() throws Exception {
		web.enqueue(new MockResponse().setBody("body").setResponseCode(200));
		this.client.mutateWith(mockOAuth2Login())
				.get().uri("/webclient/implicit")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void publicExplicitWhenAuthenticatedThenUsesClientIdRegistration() throws Exception {
		web.enqueue(new MockResponse().setBody("body").setResponseCode(200));
		this.client.mutateWith(mockOAuth2Client("client-id"))
				.get().uri("/public/webclient/explicit")
				.exchange()
				.expectStatus().isOk();
	}

	@Test
	public void publicImplicitWhenAuthenticatedThenUsesDefaultRegistration() throws Exception {
		web.enqueue(new MockResponse().setBody("body").setResponseCode(200));
		this.client.mutateWith(mockOAuth2Login())
				.get().uri("/public/webclient/implicit")
				.exchange()
				.expectStatus().isOk();
	}

	@TestConfiguration
	static class WebClientConfig {
		@Bean
		WebClient web() {
			return WebClient.create(web.url("/").toString());
		}
	}
}

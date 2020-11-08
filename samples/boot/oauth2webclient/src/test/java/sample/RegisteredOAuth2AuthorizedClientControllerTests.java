package sample;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Client;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class RegisteredOAuth2AuthorizedClientControllerTests {
	private static MockWebServer web = new MockWebServer();

	@Autowired
	private MockMvc mockMvc;

	@AfterClass
	public static void shutdown() throws Exception {
		web.shutdown();
	}

	@Test
	public void annotationExplicitWhenAuthenticatedThenUsesClientIdRegistration() throws Exception {
		web.enqueue(new MockResponse().setBody("body").setResponseCode(200));
		this.mockMvc.perform(get("/annotation/explicit")
				.with(oauth2Login())
				.with(oauth2Client("client-id")))
				.andExpect(status().isOk());
	}

	@Test
	public void annotationImplicitWhenAuthenticatedThenUsesDefaultRegistration() throws Exception {
		web.enqueue(new MockResponse().setBody("body").setResponseCode(200));
		this.mockMvc.perform(get("/annotation/implicit")
				.with(oauth2Login()))
				.andExpect(status().isOk());
	}

	@Test
	public void publicAnnotationExplicitWhenAuthenticatedThenUsesClientIdRegistration() throws Exception {
		web.enqueue(new MockResponse().setBody("body").setResponseCode(200));
		this.mockMvc.perform(get("/public/annotation/explicit")
				.with(oauth2Client("client-id")))
				.andExpect(status().isOk());
	}

	@Test
	public void publicAnnotationImplicitWhenAuthenticatedThenUsesDefaultRegistration() throws Exception {
		web.enqueue(new MockResponse().setBody("body").setResponseCode(200));
		this.mockMvc.perform(get("/public/annotation/implicit")
				.with(oauth2Login()))
				.andExpect(status().isOk());
	}

	@TestConfiguration
	static class WebClientConfig {
		@Bean
		WebClient web() {
			return WebClient.create(web.url("/").toString());
		}
	}
}

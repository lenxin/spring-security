package sample;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWebTestClient
public class HelloWebfluxApplicationTests {
	WebTestClient rest;

	@Autowired
	public void setup(ApplicationContext context) {
		this.rest = WebTestClient
				.bindToApplicationContext(context)
				.apply(springSecurity())
				.configureClient()
				.build();
	}

	@Test
	public void basicWhenNoCredentialsThenUnauthorized() {
		this.rest
			.get()
			.uri("/")
			.exchange()
			.expectStatus().isUnauthorized();
	}

	@Test
	public void basicWhenValidCredentialsThenOk() {
		this.rest
			.get()
			.uri("/")
			.headers(userCredentials())
			.exchange()
			.expectStatus().isOk()
			.expectBody().json("{\"message\":\"Hello user!\"}");
	}

	@Test
	public void basicWhenInvalidCredentialsThenUnauthorized() {
		this.rest
			.get()
			.uri("/")
			.headers(invalidCredentials())
			.exchange()
			.expectStatus().isUnauthorized()
			.expectBody().isEmpty();
	}

	@Test
	public void mockSupportWhenMutateWithMockUserThenOk() {
		this.rest
			.mutateWith(mockUser())
			.get()
			.uri("/")
			.exchange()
			.expectStatus().isOk()
			.expectBody().json("{\"message\":\"Hello user!\"}");
	}

	@Test
	@WithMockUser
	public void mockSupportWhenWithMockUserThenOk() {
		this.rest
			.get()
			.uri("/")
			.exchange()
			.expectStatus().isOk()
			.expectBody().json("{\"message\":\"Hello user!\"}");
	}

	private Consumer<HttpHeaders> userCredentials() {
		return (httpHeaders) -> httpHeaders.setBasicAuth("user", "user");
	}

	private Consumer<HttpHeaders> invalidCredentials() {
		return (httpHeaders) -> httpHeaders.setBasicAuth("user", "INVALID");
	}
}

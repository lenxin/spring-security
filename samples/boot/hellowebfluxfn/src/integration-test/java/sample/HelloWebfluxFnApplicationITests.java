package sample;

import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloWebfluxFnApplicationITests {

	WebTestClient rest;

	@Autowired
	public void setRest(WebTestClient rest) {
		this.rest = rest
				.mutateWith((b, h, c) -> b.filter(ExchangeFilterFunctions.basicAuthentication()));
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

	private Consumer<HttpHeaders> userCredentials() {
		return (httpHeaders) -> httpHeaders.setBasicAuth("user", "user");
	}

	private Consumer<HttpHeaders> invalidCredentials() {
		return (httpHeaders) -> httpHeaders.setBasicAuth("user", "INVALID");
	}
}

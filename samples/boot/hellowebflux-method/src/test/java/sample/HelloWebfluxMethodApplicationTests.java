package sample;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity;

import java.util.function.Consumer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWebfluxMethodApplicationTests {
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
	public void messageWhenNotAuthenticated() {
		this.rest
			.get()
			.uri("/message")
			.exchange()
			.expectStatus().isUnauthorized();
	}

	@Test
	public void messageWhenUserThenForbidden() {
		this.rest
			.get()
			.uri("/message")
			.headers(robsCredentials())
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void messageWhenAdminThenOk() {
		this.rest
			.get()
			.uri("/message")
			.headers(adminCredentials())
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("Hello World!");
	}

	@Test
	@WithMockUser
	public void messageWhenWithMockUserThenForbidden() {
		this.rest
			.get()
			.uri("/message")
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void messageWhenWithMockAdminThenOk() {
		this.rest
			.get()
			.uri("/message")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("Hello World!");
	}

	@Test
	public void messageWhenMutateWithMockUserThenForbidden() {
		this.rest
			.mutateWith(mockUser())
			.get()
			.uri("/message")
			.exchange()
			.expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void messageWhenMutateWithMockAdminThenOk() {
		this.rest
			.mutateWith(mockUser().roles("ADMIN"))
			.get()
			.uri("/message")
			.exchange()
			.expectStatus().isOk()
			.expectBody(String.class).isEqualTo("Hello World!");
	}

	private Consumer<HttpHeaders> robsCredentials() {
		return (httpHeaders) -> httpHeaders.setBasicAuth("rob", "rob");
	}

	private Consumer<HttpHeaders> adminCredentials() {
		return (httpHeaders) -> httpHeaders.setBasicAuth("admin", "admin");
	}
}

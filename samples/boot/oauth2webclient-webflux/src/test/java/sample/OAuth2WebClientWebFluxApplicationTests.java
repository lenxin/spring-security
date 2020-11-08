package sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author Rob Winch
 */
@SpringBootTest
@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
public class OAuth2WebClientWebFluxApplicationTests {
	@Autowired
	private WebTestClient client;

	@Test
	public void annotationExplicitWhenNotAuthenticatedThenLoginRequested() {
		this.client.get().uri("/annotation/explicit")
				.exchange()
				.expectStatus().is3xxRedirection();
	}
}

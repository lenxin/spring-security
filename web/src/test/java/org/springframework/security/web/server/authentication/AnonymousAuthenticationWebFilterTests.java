package org.springframework.security.web.server.authentication;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.web.reactive.server.WebTestClientBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author Ankur Pathak
 * @since 5.2.0
 */
@RunWith(MockitoJUnitRunner.class)
public class AnonymousAuthenticationWebFilterTests {

	@Test
	public void anonymousAuthenticationFilterWorking() {
		WebTestClient client = WebTestClientBuilder.bindToControllerAndWebFilters(HttpMeController.class,
				new AnonymousAuthenticationWebFilter(UUID.randomUUID().toString())).build();
		client.get().uri("/me").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("anonymousUser");
	}

	@RestController
	@RequestMapping("/me")
	public static class HttpMeController {

		@GetMapping
		public Mono<String> me(ServerWebExchange exchange) {
			return ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
					.map(Authentication::getPrincipal).ofType(String.class);
		}

	}

}

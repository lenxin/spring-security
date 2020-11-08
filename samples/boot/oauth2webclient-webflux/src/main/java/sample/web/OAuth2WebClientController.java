package sample.web;

import reactor.core.publisher.Mono;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

/**
 * @author Joe Grandja
 * @author Rob Winch
 */
@Controller
@RequestMapping(path = {"/webclient", "/public/webclient"})
public class OAuth2WebClientController {
	private final WebClient webClient;

	public OAuth2WebClientController(WebClient webClient) {
		this.webClient = webClient;
	}

	@GetMapping("/explicit")
	String explicit(Model model) {
		Mono<String> body = this.webClient
				.get()
				.attributes(clientRegistrationId("client-id"))
				.retrieve()
				.bodyToMono(String.class);
		model.addAttribute("body", body);
		return "response";
	}

	@GetMapping("/implicit")
	String implicit(Model model) {
		Mono<String> body = this.webClient
				.get()
				.retrieve()
				.bodyToMono(String.class);
		model.addAttribute("body", body);
		return "response";
	}
}

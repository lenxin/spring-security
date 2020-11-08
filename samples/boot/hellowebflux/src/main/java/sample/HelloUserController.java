package sample;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RestController
public class HelloUserController {

	@GetMapping("/")
	public Mono<Map<String, String>> hello(Mono<Principal> principal) {
		return principal
			.map(Principal::getName)
			.map(this::helloMessage);
	}

	private Map<String, String> helloMessage(String username) {
		return Collections.singletonMap("message", "Hello " + username + "!");
	}
}

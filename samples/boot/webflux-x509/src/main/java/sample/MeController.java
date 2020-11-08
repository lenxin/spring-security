package sample;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Alexey Nesterov
 * @since 5.2
 */
@RestController
@RequestMapping("/me")
public class MeController {

	@GetMapping
	public Mono<String> me() {
		return ReactiveSecurityContextHolder.getContext()
				.map(SecurityContext::getAuthentication)
				.map((authentication) -> "Hello, " + authentication.getName());
	}
}

package sample;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author Rob Winch
 * @since 5.0
 */
@Component
public class HelloWorldMessageService {
	@PreAuthorize("hasRole('ADMIN')")
	public Mono<String> findMessage() {
		return Mono.just("Hello World!");
	}
}

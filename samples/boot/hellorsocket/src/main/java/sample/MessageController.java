package sample;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

/**
 * @author Rob Winch
 * @since 5.2
 */
@Controller
public class MessageController {

	@MessageMapping("message")
	public Mono<String> message() {
		return Mono.just("Hello");
	}
}

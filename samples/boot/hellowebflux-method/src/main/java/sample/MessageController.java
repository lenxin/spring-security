package sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RestController
public class MessageController {
	private final HelloWorldMessageService messages;

	public MessageController(HelloWorldMessageService messages) {
		this.messages = messages;
	}

	@GetMapping("/message")
	public Mono<String> message() {
		return this.messages.findMessage();
	}
}

package sample;

import java.security.Principal;
import java.util.Collections;

import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author Rob Winch
 * @since 5.0
 */
@Component
public class HelloUserController {

	public Mono<ServerResponse> hello(ServerRequest serverRequest) {
		return serverRequest.principal()
			.map(Principal::getName)
			.flatMap((username) ->
				ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.syncBody(Collections.singletonMap("message", "Hello " + username + "!"))
			);
	}
}

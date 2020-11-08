package org.springframework.security.oauth2.client.web.reactive.function.client;

import java.util.ArrayList;
import java.util.List;

import reactor.core.publisher.Mono;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;

import static org.mockito.Mockito.mock;

/**
 * @author Rob Winch
 * @since 5.1
 */
public class MockExchangeFunction implements ExchangeFunction {

	private List<ClientRequest> requests = new ArrayList<>();

	private ClientResponse response = mock(ClientResponse.class);

	public ClientRequest getRequest() {
		return this.requests.get(this.requests.size() - 1);
	}

	public List<ClientRequest> getRequests() {
		return this.requests;
	}

	public ClientResponse getResponse() {
		return this.response;
	}

	@Override
	public Mono<ClientResponse> exchange(ClientRequest request) {
		return Mono.defer(() -> {
			this.requests.add(request);
			return Mono.just(this.response);
		});
	}

}

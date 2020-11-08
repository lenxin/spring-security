package org.springframework.security.rsocket.core;

import java.util.Arrays;
import java.util.List;

import io.rsocket.ConnectionSetupPayload;
import io.rsocket.Payload;
import io.rsocket.RSocket;
import io.rsocket.SocketAcceptor;
import io.rsocket.metadata.WellKnownMimeType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.api.PayloadInterceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Rob Winch
 */
@RunWith(MockitoJUnitRunner.class)
public class PayloadSocketAcceptorInterceptorTests {

	@Mock
	private PayloadInterceptor interceptor;

	@Mock
	private SocketAcceptor socketAcceptor;

	@Mock
	private ConnectionSetupPayload setupPayload;

	@Mock
	private RSocket rSocket;

	@Mock
	private Payload payload;

	private List<PayloadInterceptor> interceptors;

	private PayloadSocketAcceptorInterceptor acceptorInterceptor;

	@Before
	public void setup() {
		this.interceptors = Arrays.asList(this.interceptor);
		this.acceptorInterceptor = new PayloadSocketAcceptorInterceptor(this.interceptors);
	}

	@Test
	public void applyWhenDefaultMetadataMimeTypeThenDefaulted() {
		given(this.setupPayload.dataMimeType()).willReturn(MediaType.APPLICATION_JSON_VALUE);
		PayloadExchange exchange = captureExchange();
		assertThat(exchange.getMetadataMimeType().toString())
				.isEqualTo(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString());
		assertThat(exchange.getDataMimeType()).isEqualTo(MediaType.APPLICATION_JSON);
	}

	@Test
	public void acceptWhenDefaultMetadataMimeTypeOverrideThenDefaulted() {
		this.acceptorInterceptor.setDefaultMetadataMimeType(MediaType.APPLICATION_JSON);
		given(this.setupPayload.dataMimeType()).willReturn(MediaType.APPLICATION_JSON_VALUE);
		PayloadExchange exchange = captureExchange();
		assertThat(exchange.getMetadataMimeType()).isEqualTo(MediaType.APPLICATION_JSON);
		assertThat(exchange.getDataMimeType()).isEqualTo(MediaType.APPLICATION_JSON);
	}

	@Test
	public void acceptWhenDefaultDataMimeTypeThenDefaulted() {
		this.acceptorInterceptor.setDefaultDataMimeType(MediaType.APPLICATION_JSON);
		PayloadExchange exchange = captureExchange();
		assertThat(exchange.getMetadataMimeType().toString())
				.isEqualTo(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString());
		assertThat(exchange.getDataMimeType()).isEqualTo(MediaType.APPLICATION_JSON);
	}

	private PayloadExchange captureExchange() {
		given(this.socketAcceptor.accept(any(), any())).willReturn(Mono.just(this.rSocket));
		given(this.interceptor.intercept(any(), any())).willReturn(Mono.empty());
		SocketAcceptor wrappedAcceptor = this.acceptorInterceptor.apply(this.socketAcceptor);
		RSocket result = wrappedAcceptor.accept(this.setupPayload, this.rSocket).block();
		assertThat(result).isInstanceOf(PayloadInterceptorRSocket.class);
		given(this.rSocket.fireAndForget(any())).willReturn(Mono.empty());
		result.fireAndForget(this.payload).block();
		ArgumentCaptor<PayloadExchange> exchangeArg = ArgumentCaptor.forClass(PayloadExchange.class);
		verify(this.interceptor, times(2)).intercept(exchangeArg.capture(), any());
		return exchangeArg.getValue();
	}

}

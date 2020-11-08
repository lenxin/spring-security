package org.springframework.security.config.annotation.rsocket;

import java.util.ArrayList;
import java.util.List;

import io.rsocket.core.RSocketServer;
import io.rsocket.exceptions.ApplicationErrorException;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.metadata.WellKnownMimeType;
import io.rsocket.transport.netty.server.CloseableChannel;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor;
import org.springframework.security.rsocket.core.SecuritySocketAcceptorInterceptor;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Rob Winch
 */
@ContextConfiguration
@RunWith(SpringRunner.class)
public class SimpleAuthenticationITests {

	@Autowired
	RSocketMessageHandler handler;

	@Autowired
	SecuritySocketAcceptorInterceptor interceptor;

	@Autowired
	ServerController controller;

	private CloseableChannel server;

	private RSocketRequester requester;

	@Before
	public void setup() {
		// @formatter:off
		this.server = RSocketServer.create()
				.payloadDecoder(PayloadDecoder.ZERO_COPY)
				.interceptors((registry) ->
					registry.forSocketAcceptor(this.interceptor)
				)
				.acceptor(this.handler.responder())
				.bind(TcpServerTransport.create("localhost", 0))
				.block();
		// @formatter:on
	}

	@After
	public void dispose() {
		this.requester.rsocket().dispose();
		this.server.dispose();
		this.controller.payloads.clear();
	}

	@Test
	public void retrieveMonoWhenSecureThenDenied() throws Exception {
		// @formatter:off
		this.requester = RSocketRequester.builder()
			.rsocketStrategies(this.handler.getRSocketStrategies())
			.connectTcp("localhost", this.server.address().getPort())
			.block();
		// @formatter:on
		String data = "rob";
		// @formatter:off
		assertThatExceptionOfType(ApplicationErrorException.class)
			.isThrownBy(() -> this.requester.route("secure.retrieve-mono")
				.data(data).retrieveMono(String.class)
				.block()
			);
		// @formatter:on
		assertThat(this.controller.payloads).isEmpty();
	}

	@Test
	public void retrieveMonoWhenAuthorizedThenGranted() {
		MimeType authenticationMimeType = MimeTypeUtils
				.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());
		UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("rob", "password");
		// @formatter:off
		this.requester = RSocketRequester.builder()
			.setupMetadata(credentials, authenticationMimeType)
			.rsocketStrategies(this.handler.getRSocketStrategies())
			.connectTcp("localhost", this.server.address().getPort())
			.block();
		// @formatter:on
		String data = "rob";
		// @formatter:off
		String hiRob = this.requester.route("secure.retrieve-mono")
			.metadata(credentials, authenticationMimeType)
			.data(data).retrieveMono(String.class)
			.block();
		// @formatter:on
		assertThat(hiRob).isEqualTo("Hi rob");
		assertThat(this.controller.payloads).containsOnly(data);
	}

	@Configuration
	@EnableRSocketSecurity
	static class Config {

		@Bean
		ServerController controller() {
			return new ServerController();
		}

		@Bean
		RSocketMessageHandler messageHandler() {
			RSocketMessageHandler handler = new RSocketMessageHandler();
			handler.setRSocketStrategies(rsocketStrategies());
			return handler;
		}

		@Bean
		RSocketStrategies rsocketStrategies() {
			return RSocketStrategies.builder().encoder(new SimpleAuthenticationEncoder()).build();
		}

		@Bean
		PayloadSocketAcceptorInterceptor rsocketInterceptor(RSocketSecurity rsocket) {
			rsocket.authorizePayload((authorize) -> authorize.anyRequest().authenticated().anyExchange().permitAll())
					.simpleAuthentication(Customizer.withDefaults());
			return rsocket.build();
		}

		@Bean
		MapReactiveUserDetailsService uds() {
			// @formatter:off
			UserDetails rob = User.withDefaultPasswordEncoder()
					.username("rob")
					.password("password")
					.roles("USER", "ADMIN")
					.build();
			// @formatter:on
			return new MapReactiveUserDetailsService(rob);
		}

	}

	@Controller
	static class ServerController {

		private List<String> payloads = new ArrayList<>();

		@MessageMapping("**")
		String retrieveMono(String payload) {
			add(payload);
			return "Hi " + payload;
		}

		private void add(String p) {
			this.payloads.add(p);
		}

	}

}

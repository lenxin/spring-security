package org.springframework.security.rsocket.core;

import io.rsocket.SocketAcceptor;
import io.rsocket.plugins.SocketAcceptorInterceptor;

import org.springframework.util.Assert;

/**
 * A SocketAcceptorInterceptor that applies Security through a delegate
 * {@link SocketAcceptorInterceptor}. This allows security to be applied lazily to an
 * application.
 *
 * @author Rob Winch
 * @since 5.2
 */
public class SecuritySocketAcceptorInterceptor implements SocketAcceptorInterceptor {

	private final SocketAcceptorInterceptor acceptorInterceptor;

	public SecuritySocketAcceptorInterceptor(SocketAcceptorInterceptor acceptorInterceptor) {
		Assert.notNull(acceptorInterceptor, "acceptorInterceptor cannot be null");
		this.acceptorInterceptor = acceptorInterceptor;
	}

	@Override
	public SocketAcceptor apply(SocketAcceptor socketAcceptor) {
		return this.acceptorInterceptor.apply(socketAcceptor);
	}

}

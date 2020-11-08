package org.springframework.security.web.server.util.matcher;

/**
 * A rich object for associating a {@link ServerWebExchangeMatcher} to another object.
 *
 * @author Rob Winch
 * @since 5.0
 */
public class ServerWebExchangeMatcherEntry<T> {

	private final ServerWebExchangeMatcher matcher;

	private final T entry;

	public ServerWebExchangeMatcherEntry(ServerWebExchangeMatcher matcher, T entry) {
		this.matcher = matcher;
		this.entry = entry;
	}

	public ServerWebExchangeMatcher getMatcher() {
		return this.matcher;
	}

	public T getEntry() {
		return this.entry;
	}

}

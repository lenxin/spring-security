package org.springframework.security.rsocket.util.matcher;

/**
 * @author Rob Winch
 */
public class PayloadExchangeMatcherEntry<T> {

	private final PayloadExchangeMatcher matcher;

	private final T entry;

	public PayloadExchangeMatcherEntry(PayloadExchangeMatcher matcher, T entry) {
		this.matcher = matcher;
		this.entry = entry;
	}

	public PayloadExchangeMatcher getMatcher() {
		return this.matcher;
	}

	public T getEntry() {
		return this.entry;
	}

}

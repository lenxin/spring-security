package org.springframework.security.rsocket.api;

/**
 * The {@link PayloadExchange} type
 *
 * @author Rob Winch
 * @since 5.2
 */
public enum PayloadExchangeType {

	/**
	 * The <a href="https://rsocket.io/docs/Protocol#setup-frame-0x01">Setup</a>. Can be
	 * used to determine if a Payload is part of the connection
	 */
	SETUP(false),

	/**
	 * A <a href="https://rsocket.io/docs/Protocol#frame-fnf">Fire and Forget</a>
	 * exchange.
	 */
	FIRE_AND_FORGET(true),

	/**
	 * A <a href="https://rsocket.io/docs/Protocol#frame-request-response">Request
	 * Response</a> exchange.
	 */
	REQUEST_RESPONSE(true),

	/**
	 * A <a href="https://rsocket.io/docs/Protocol#request-stream-frame">Request
	 * Stream</a> exchange. This is only represents the request portion. The
	 * {@link #PAYLOAD} type represents the data that submitted.
	 */
	REQUEST_STREAM(true),

	/**
	 * A <a href="https://rsocket.io/docs/Protocol#request-channel-frame">Request
	 * Channel</a> exchange.
	 */
	REQUEST_CHANNEL(true),

	/**
	 * A <a href="https://rsocket.io/docs/Protocol#payload-frame">Payload</a> exchange.
	 */
	PAYLOAD(false),

	/**
	 * A <a href="https://rsocket.io/docs/Protocol#frame-metadata-push">Metadata Push</a>
	 * exchange.
	 */
	METADATA_PUSH(true);

	private final boolean isRequest;

	PayloadExchangeType(boolean isRequest) {
		this.isRequest = isRequest;
	}

	/**
	 * Determines if this exchange is a type of request (i.e. the initial frame).
	 * @return true if it is a request, else false
	 */
	public boolean isRequest() {
		return this.isRequest;
	}

}

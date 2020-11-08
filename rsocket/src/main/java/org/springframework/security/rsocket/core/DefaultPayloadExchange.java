package org.springframework.security.rsocket.core;

import io.rsocket.Payload;

import org.springframework.security.rsocket.api.PayloadExchange;
import org.springframework.security.rsocket.api.PayloadExchangeType;
import org.springframework.util.Assert;
import org.springframework.util.MimeType;

/**
 * Default implementation of {@link PayloadExchange}
 *
 * @author Rob Winch
 * @since 5.2
 */
public class DefaultPayloadExchange implements PayloadExchange {

	private final PayloadExchangeType type;

	private final Payload payload;

	private final MimeType metadataMimeType;

	private final MimeType dataMimeType;

	public DefaultPayloadExchange(PayloadExchangeType type, Payload payload, MimeType metadataMimeType,
			MimeType dataMimeType) {
		Assert.notNull(type, "type cannot be null");
		Assert.notNull(payload, "payload cannot be null");
		Assert.notNull(metadataMimeType, "metadataMimeType cannot be null");
		Assert.notNull(dataMimeType, "dataMimeType cannot be null");
		this.type = type;
		this.payload = payload;
		this.metadataMimeType = metadataMimeType;
		this.dataMimeType = dataMimeType;
	}

	@Override
	public PayloadExchangeType getType() {
		return this.type;
	}

	@Override
	public Payload getPayload() {
		return this.payload;
	}

	@Override
	public MimeType getMetadataMimeType() {
		return this.metadataMimeType;
	}

	@Override
	public MimeType getDataMimeType() {
		return this.dataMimeType;
	}

}

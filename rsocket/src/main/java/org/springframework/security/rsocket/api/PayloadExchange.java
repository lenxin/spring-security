package org.springframework.security.rsocket.api;

import io.rsocket.Payload;

import org.springframework.util.MimeType;

/**
 * Contract for a Payload interaction.
 *
 * @author Rob Winch
 * @since 5.2
 */
public interface PayloadExchange {

	PayloadExchangeType getType();

	Payload getPayload();

	MimeType getDataMimeType();

	MimeType getMetadataMimeType();

}

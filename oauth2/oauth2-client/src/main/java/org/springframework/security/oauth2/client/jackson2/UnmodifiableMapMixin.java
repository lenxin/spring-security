package org.springframework.security.oauth2.client.jackson2;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This mixin class is used to serialize/deserialize
 * {@link Collections#unmodifiableMap(Map)}. It also registers a custom deserializer
 * {@link UnmodifiableMapDeserializer}.
 *
 * @author Joe Grandja
 * @since 5.3
 * @see Collections#unmodifiableMap(Map)
 * @see UnmodifiableMapDeserializer
 * @see OAuth2ClientJackson2Module
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonDeserialize(using = UnmodifiableMapDeserializer.class)
abstract class UnmodifiableMapMixin {

	@JsonCreator
	UnmodifiableMapMixin(Map<?, ?> map) {
	}

}

package org.springframework.security.jackson2;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This mixin class used to deserialize java.util.Collections$UnmodifiableRandomAccessList
 * and used with various AuthenticationToken implementation's mixin classes.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new CoreJackson2Module());
 * </pre>
 *
 * @author Rob Winch
 * @see UnmodifiableListDeserializer
 * @see CoreJackson2Module
 * @see SecurityJackson2Modules
 * @since 5.0.2
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonDeserialize(using = UnmodifiableListDeserializer.class)
class UnmodifiableListMixin {

	/**
	 * Mixin Constructor
	 * @param s the Set
	 */
	@JsonCreator
	UnmodifiableListMixin(Set<?> s) {
	}

}

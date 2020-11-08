package org.springframework.security.jackson2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * This mixin class helps in serialize/deserialize
 * {@link org.springframework.security.authentication.BadCredentialsException} class. To
 * use this class you need to register it with
 * {@link com.fasterxml.jackson.databind.ObjectMapper}.
 *
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new CoreJackson2Module());
 * </pre>
 *
 * <i>Note: This class will save TypeInfo (full class name) into a property
 * called @class</i> <i>The cause and stackTrace are ignored in the serialization.</i>
 *
 * @author Yannick Lombardi
 * @see CoreJackson2Module
 * @since 5.0
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonIgnoreProperties(ignoreUnknown = true, value = { "cause", "stackTrace" })
class BadCredentialsExceptionMixin {

	/**
	 * Constructor used by Jackson to create
	 * {@link org.springframework.security.authentication.BadCredentialsException} object.
	 * @param message the detail message
	 */
	@JsonCreator
	BadCredentialsExceptionMixin(@JsonProperty("message") String message) {
	}

}

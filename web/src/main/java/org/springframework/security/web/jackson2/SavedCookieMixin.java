package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Jackson mixin class to serialize/deserialize
 * {@link org.springframework.security.web.savedrequest.SavedCookie} serialization
 * support.
 *
 * <pre>
 * 		ObjectMapper mapper = new ObjectMapper();
 *		mapper.registerModule(new WebServletJackson2Module());
 * </pre>
 *
 * @author Jitendra Singh.
 * @see WebServletJackson2Module
 * @see org.springframework.security.jackson2.SecurityJackson2Modules
 * @since 4.2
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
abstract class SavedCookieMixin {

	@JsonCreator
	SavedCookieMixin(@JsonProperty("name") String name, @JsonProperty("value") String value,
			@JsonProperty("comment") String comment, @JsonProperty("domain") String domain,
			@JsonProperty("maxAge") int maxAge, @JsonProperty("path") String path,
			@JsonProperty("secure") boolean secure, @JsonProperty("version") int version) {

	}

}

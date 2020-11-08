package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Jackson mixin class to serialize/deserialize
 * {@link org.springframework.security.web.authentication.WebAuthenticationDetails}.
 *
 * <pre>
 * 	ObjectMapper mapper = new ObjectMapper();
 *	mapper.registerModule(new WebServletJackson2Module());
 * </pre>
 *
 * @author Jitendra Singh
 * @see WebServletJackson2Module
 * @see org.springframework.security.jackson2.SecurityJackson2Modules
 * @since 4.2
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE, creatorVisibility = JsonAutoDetect.Visibility.ANY)
class WebAuthenticationDetailsMixin {

	@JsonCreator
	WebAuthenticationDetailsMixin(@JsonProperty("remoteAddress") String remoteAddress,
			@JsonProperty("sessionId") String sessionId) {
	}

}

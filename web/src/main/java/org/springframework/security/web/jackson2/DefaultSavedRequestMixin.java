package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.springframework.security.web.savedrequest.DefaultSavedRequest;

/**
 * Jackson mixin class to serialize/deserialize {@link DefaultSavedRequest}. This mixin
 * use {@link org.springframework.security.web.savedrequest.DefaultSavedRequest.Builder}
 * to deserialized json.In order to use this mixin class you also need to register
 * {@link CookieMixin}.
 * <p>
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new WebServletJackson2Module());
 * </pre>
 *
 * @author Jitendra Singh
 * @see WebServletJackson2Module
 * @see org.springframework.security.jackson2.SecurityJackson2Modules
 * @since 4.2
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonDeserialize(builder = DefaultSavedRequest.Builder.class)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
abstract class DefaultSavedRequestMixin {

}

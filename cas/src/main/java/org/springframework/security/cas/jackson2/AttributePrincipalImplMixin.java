package org.springframework.security.cas.jackson2;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.jasig.cas.client.proxy.ProxyRetriever;

/**
 * Helps in deserialize {@link org.jasig.cas.client.authentication.AttributePrincipalImpl}
 * which is used with
 * {@link org.springframework.security.cas.authentication.CasAuthenticationToken}. Type
 * information will be stored in property named @class.
 * <p>
 * <pre>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new CasJackson2Module());
 * </pre>
 *
 * @author Jitendra Singh
 * @see CasJackson2Module
 * @see org.springframework.security.jackson2.SecurityJackson2Modules
 * @since 4.2
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
		isGetterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
class AttributePrincipalImplMixin {

	/**
	 * Mixin Constructor helps in deserialize
	 * {@link org.jasig.cas.client.authentication.AttributePrincipalImpl}
	 * @param name the unique identifier for the principal.
	 * @param attributes the key/value pairs for this principal.
	 * @param proxyGrantingTicket the ticket associated with this principal.
	 * @param proxyRetriever the ProxyRetriever implementation to call back to the CAS
	 * server.
	 */
	@JsonCreator
	AttributePrincipalImplMixin(@JsonProperty("name") String name,
			@JsonProperty("attributes") Map<String, Object> attributes,
			@JsonProperty("proxyGrantingTicket") String proxyGrantingTicket,
			@JsonProperty("proxyRetriever") ProxyRetriever proxyRetriever) {
	}

}

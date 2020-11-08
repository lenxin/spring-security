package org.springframework.security.web.jackson2;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 * Custom deserializer for {@link PreAuthenticatedAuthenticationToken}. At the time of
 * deserialization it will invoke suitable constructor depending on the value of
 * <b>authenticated</b> property. It will ensure that the token's state must not change.
 * <p>
 * This deserializer is already registered with
 * {@link PreAuthenticatedAuthenticationTokenMixin} but you can also registered it with
 * your own mixin class.
 *
 * @author Jitendra Singh
 * @since 4.2
 * @see PreAuthenticatedAuthenticationTokenMixin
 */
class PreAuthenticatedAuthenticationTokenDeserializer extends JsonDeserializer<PreAuthenticatedAuthenticationToken> {

	private static final TypeReference<List<GrantedAuthority>> GRANTED_AUTHORITY_LIST = new TypeReference<List<GrantedAuthority>>() {
	};

	/**
	 * This method construct {@link PreAuthenticatedAuthenticationToken} object from
	 * serialized json.
	 * @param jp the JsonParser
	 * @param ctxt the DeserializationContext
	 * @return the user
	 * @throws IOException if a exception during IO occurs
	 * @throws JsonProcessingException if an error during JSON processing occurs
	 */
	@Override
	public PreAuthenticatedAuthenticationToken deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode jsonNode = mapper.readTree(jp);
		Boolean authenticated = readJsonNode(jsonNode, "authenticated").asBoolean();
		JsonNode principalNode = readJsonNode(jsonNode, "principal");
		Object principal = (!principalNode.isObject()) ? principalNode.asText()
				: mapper.readValue(principalNode.traverse(mapper), Object.class);
		Object credentials = readJsonNode(jsonNode, "credentials").asText();
		List<GrantedAuthority> authorities = mapper.readValue(readJsonNode(jsonNode, "authorities").traverse(mapper),
				GRANTED_AUTHORITY_LIST);
		PreAuthenticatedAuthenticationToken token = (!authenticated)
				? new PreAuthenticatedAuthenticationToken(principal, credentials)
				: new PreAuthenticatedAuthenticationToken(principal, credentials, authorities);
		token.setDetails(readJsonNode(jsonNode, "details"));
		return token;
	}

	private JsonNode readJsonNode(JsonNode jsonNode, String field) {
		return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
	}

}

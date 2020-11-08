package org.springframework.security.jackson2;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Custom deserializer for {@link UnmodifiableSetMixin}.
 *
 * @author Jitendra Singh
 * @since 4.2
 * @see UnmodifiableSetMixin
 */
class UnmodifiableSetDeserializer extends JsonDeserializer<Set> {

	@Override
	public Set deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode node = mapper.readTree(jp);
		Set<Object> resultSet = new HashSet<>();
		if (node != null) {
			if (node instanceof ArrayNode) {
				ArrayNode arrayNode = (ArrayNode) node;
				for (JsonNode elementNode : arrayNode) {
					resultSet.add(mapper.readValue(elementNode.traverse(mapper), Object.class));
				}
			}
			else {
				resultSet.add(mapper.readValue(node.traverse(mapper), Object.class));
			}
		}
		return Collections.unmodifiableSet(resultSet);
	}

}

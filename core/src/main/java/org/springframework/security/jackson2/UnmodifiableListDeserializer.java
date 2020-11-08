package org.springframework.security.jackson2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Custom deserializer for {@link UnmodifiableListDeserializer}.
 *
 * @author Rob Winch
 * @since 5.0.2
 * @see UnmodifiableListMixin
 */
class UnmodifiableListDeserializer extends JsonDeserializer<List> {

	@Override
	public List deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		ObjectMapper mapper = (ObjectMapper) jp.getCodec();
		JsonNode node = mapper.readTree(jp);
		List<Object> result = new ArrayList<>();
		if (node != null) {
			if (node instanceof ArrayNode) {
				ArrayNode arrayNode = (ArrayNode) node;
				for (JsonNode elementNode : arrayNode) {
					result.add(mapper.readValue(elementNode.traverse(mapper), Object.class));
				}
			}
			else {
				result.add(mapper.readValue(node.traverse(mapper), Object.class));
			}
		}
		return Collections.unmodifiableList(result);
	}

}

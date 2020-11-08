package org.springframework.security.core.authority.mapping;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class implements the MappableAttributesRetriever interface by just returning a
 * list of mappable attributes as previously set using the corresponding setter method.
 *
 * @author Ruud Senden
 * @since 2.0
 */
public class SimpleMappableAttributesRetriever implements MappableAttributesRetriever {

	private Set<String> mappableAttributes = null;

	@Override
	public Set<String> getMappableAttributes() {
		return this.mappableAttributes;
	}

	public void setMappableAttributes(Set<String> aMappableRoles) {
		this.mappableAttributes = new HashSet<>();
		this.mappableAttributes.addAll(aMappableRoles);
		this.mappableAttributes = Collections.unmodifiableSet(this.mappableAttributes);
	}

}

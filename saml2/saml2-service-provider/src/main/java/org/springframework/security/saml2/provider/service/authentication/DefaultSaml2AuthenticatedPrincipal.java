package org.springframework.security.saml2.provider.service.authentication;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * Default implementation of a {@link Saml2AuthenticatedPrincipal}.
 *
 * @author Clement Stoquart
 * @since 5.4
 */
public class DefaultSaml2AuthenticatedPrincipal implements Saml2AuthenticatedPrincipal, Serializable {

	private final String name;

	private final Map<String, List<Object>> attributes;

	public DefaultSaml2AuthenticatedPrincipal(String name, Map<String, List<Object>> attributes) {
		Assert.notNull(name, "name cannot be null");
		Assert.notNull(attributes, "attributes cannot be null");
		this.name = name;
		this.attributes = attributes;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Map<String, List<Object>> getAttributes() {
		return this.attributes;
	}

}

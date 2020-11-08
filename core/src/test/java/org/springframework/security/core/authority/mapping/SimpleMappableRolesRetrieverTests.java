package org.springframework.security.core.authority.mapping;

import java.util.Set;

import org.junit.Test;

import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author TSARDD
 * @since 18-okt-2007
 */
public class SimpleMappableRolesRetrieverTests {

	@Test
	public final void testGetSetMappableRoles() {
		Set<String> roles = StringUtils.commaDelimitedListToSet("Role1,Role2");
		SimpleMappableAttributesRetriever r = new SimpleMappableAttributesRetriever();
		r.setMappableAttributes(roles);
		Set<String> result = r.getMappableAttributes();
		assertThat(roles.containsAll(result) && result.containsAll(roles))
				.withFailMessage("Role collections do not match; result: " + result + ", expected: " + roles).isTrue();
	}

}

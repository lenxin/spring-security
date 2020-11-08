package org.springframework.security.access.hierarchicalroles;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * Utility methods for {@link RoleHierarchy}.
 *
 * @author Thomas Darimont
 * @since 4.2.0
 */
public final class RoleHierarchyUtils {

	private RoleHierarchyUtils() {
	}

	/**
	 * Converts the supplied {@link Map} of role name to implied role name(s) to a string
	 * representation understood by {@link RoleHierarchyImpl#setHierarchy(String)}. The
	 * map key is the role name and the map value is a {@link List} of implied role
	 * name(s).
	 * @param roleHierarchyMap the mapping(s) of role name to implied role name(s)
	 * @return a string representation of a role hierarchy
	 * @throws IllegalArgumentException if roleHierarchyMap is null or empty or if a role
	 * name is null or empty or if an implied role name(s) is null or empty
	 */
	public static String roleHierarchyFromMap(Map<String, List<String>> roleHierarchyMap) {
		Assert.notEmpty(roleHierarchyMap, "roleHierarchyMap cannot be empty");
		StringWriter result = new StringWriter();
		PrintWriter writer = new PrintWriter(result);
		roleHierarchyMap.forEach((role, impliedRoles) -> {
			Assert.hasLength(role, "role name must be supplied");
			Assert.notEmpty(impliedRoles, "implied role name(s) cannot be empty");
			for (String impliedRole : impliedRoles) {
				writer.println(role + " > " + impliedRole);
			}
		});
		return result.toString();
	}

}

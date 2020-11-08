package org.springframework.security.access.hierarchicalroles;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link RoleHierarchyUtils}.
 *
 * @author Joe Grandja
 */
public class RoleHierarchyUtilsTests {

	private static final String EOL = System.lineSeparator();

	@Test
	public void roleHierarchyFromMapWhenMapValidThenConvertsCorrectly() {
		// @formatter:off
		String expectedRoleHierarchy = "ROLE_A > ROLE_B" + EOL +
				"ROLE_A > ROLE_C" + EOL +
				"ROLE_B > ROLE_D" + EOL +
				"ROLE_C > ROLE_D" + EOL;
		// @formatter:on
		Map<String, List<String>> roleHierarchyMap = new TreeMap<>();
		roleHierarchyMap.put("ROLE_A", Arrays.asList("ROLE_B", "ROLE_C"));
		roleHierarchyMap.put("ROLE_B", Arrays.asList("ROLE_D"));
		roleHierarchyMap.put("ROLE_C", Arrays.asList("ROLE_D"));
		String roleHierarchy = RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap);
		assertThat(roleHierarchy).isEqualTo(expectedRoleHierarchy);
	}

	@Test
	public void roleHierarchyFromMapWhenMapNullThenThrowsIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> RoleHierarchyUtils.roleHierarchyFromMap(null));
	}

	@Test
	public void roleHierarchyFromMapWhenMapEmptyThenThrowsIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(
				() -> RoleHierarchyUtils.roleHierarchyFromMap(Collections.<String, List<String>>emptyMap()));
	}

	@Test
	public void roleHierarchyFromMapWhenRoleNullThenThrowsIllegalArgumentException() {
		Map<String, List<String>> roleHierarchyMap = new HashMap<>();
		roleHierarchyMap.put(null, Arrays.asList("ROLE_B", "ROLE_C"));
		assertThatIllegalArgumentException()
				.isThrownBy(() -> RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap));
	}

	@Test
	public void roleHierarchyFromMapWhenRoleEmptyThenThrowsIllegalArgumentException() {
		Map<String, List<String>> roleHierarchyMap = new HashMap<>();
		roleHierarchyMap.put("", Arrays.asList("ROLE_B", "ROLE_C"));
		assertThatIllegalArgumentException()
				.isThrownBy(() -> RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap));
	}

	@Test
	public void roleHierarchyFromMapWhenImpliedRolesNullThenThrowsIllegalArgumentException() {
		Map<String, List<String>> roleHierarchyMap = new HashMap<>();
		roleHierarchyMap.put("ROLE_A", null);
		assertThatIllegalArgumentException()
				.isThrownBy(() -> RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap));
	}

	@Test
	public void roleHierarchyFromMapWhenImpliedRolesEmptyThenThrowsIllegalArgumentException() {
		Map<String, List<String>> roleHierarchyMap = new HashMap<>();
		roleHierarchyMap.put("ROLE_A", Collections.<String>emptyList());
		assertThatIllegalArgumentException()
				.isThrownBy(() -> RoleHierarchyUtils.roleHierarchyFromMap(roleHierarchyMap));
	}

}

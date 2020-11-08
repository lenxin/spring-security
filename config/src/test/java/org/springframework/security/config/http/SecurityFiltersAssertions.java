package org.springframework.security.config.http;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Assertions for tests that rely on confirming behavior of the package-private
 * SecurityFilters enum
 *
 * @author Josh Cummings
 */
public final class SecurityFiltersAssertions {

	private static Collection<SecurityFilters> ordered = Arrays.asList(SecurityFilters.values());

	private SecurityFiltersAssertions() {
	}

	public static void assertEquals(List<String> filters) {
		List<String> expected = ordered.stream().map(SecurityFilters::name).collect(Collectors.toList());
		assertThat(filters).isEqualTo(expected);
	}

}

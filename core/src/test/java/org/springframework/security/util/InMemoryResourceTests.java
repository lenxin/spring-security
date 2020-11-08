package org.springframework.security.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Luke Taylor
 */
public class InMemoryResourceTests {

	@Test
	public void resourceContainsExpectedData() throws Exception {
		InMemoryResource resource = new InMemoryResource("blah");
		assertThat(resource.getDescription()).isNull();
		assertThat(resource.hashCode()).isEqualTo(1);
		assertThat(resource.getInputStream()).isNotNull();
	}

	@Test
	public void resourceIsEqualToOneWithSameContent() {
		assertThat(new InMemoryResource("xxx")).isEqualTo(new InMemoryResource("xxx"));
		assertThat(new InMemoryResource("xxx").equals(new InMemoryResource("xxxx"))).isFalse();
		assertThat(new InMemoryResource("xxx").equals(new Object())).isFalse();
	}

}

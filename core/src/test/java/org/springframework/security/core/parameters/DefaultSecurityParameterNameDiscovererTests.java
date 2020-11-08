package org.springframework.security.core.parameters;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 3.2
 */
@SuppressWarnings("unchecked")
public class DefaultSecurityParameterNameDiscovererTests {

	private DefaultSecurityParameterNameDiscoverer discoverer;

	@Before
	public void setup() {
		this.discoverer = new DefaultSecurityParameterNameDiscoverer();
	}

	@Test
	public void constructorDefault() {
		List<ParameterNameDiscoverer> discoverers = (List<ParameterNameDiscoverer>) ReflectionTestUtils
				.getField(this.discoverer, "parameterNameDiscoverers");
		assertThat(discoverers).hasSize(2);
		ParameterNameDiscoverer annotationDisc = discoverers.get(0);
		assertThat(annotationDisc).isInstanceOf(AnnotationParameterNameDiscoverer.class);
		Set<String> annotationsToUse = (Set<String>) ReflectionTestUtils.getField(annotationDisc,
				"annotationClassesToUse");
		assertThat(annotationsToUse).containsOnly("org.springframework.security.access.method.P", P.class.getName());
		assertThat(discoverers.get(1).getClass()).isEqualTo(DefaultParameterNameDiscoverer.class);
	}

	@Test
	public void constructorDiscoverers() {
		this.discoverer = new DefaultSecurityParameterNameDiscoverer(
				Arrays.asList(new LocalVariableTableParameterNameDiscoverer()));
		List<ParameterNameDiscoverer> discoverers = (List<ParameterNameDiscoverer>) ReflectionTestUtils
				.getField(this.discoverer, "parameterNameDiscoverers");
		assertThat(discoverers).hasSize(3);
		assertThat(discoverers.get(0)).isInstanceOf(LocalVariableTableParameterNameDiscoverer.class);
		ParameterNameDiscoverer annotationDisc = discoverers.get(1);
		assertThat(annotationDisc).isInstanceOf(AnnotationParameterNameDiscoverer.class);
		Set<String> annotationsToUse = (Set<String>) ReflectionTestUtils.getField(annotationDisc,
				"annotationClassesToUse");
		assertThat(annotationsToUse).containsOnly("org.springframework.security.access.method.P", P.class.getName());
		assertThat(discoverers.get(2)).isInstanceOf(DefaultParameterNameDiscoverer.class);
	}

}

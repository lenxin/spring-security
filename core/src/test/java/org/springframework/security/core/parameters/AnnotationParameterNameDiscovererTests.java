package org.springframework.security.core.parameters;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.access.method.P;
import org.springframework.util.ReflectionUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationParameterNameDiscovererTests {

	private AnnotationParameterNameDiscoverer discoverer;

	@Before
	public void setup() {
		this.discoverer = new AnnotationParameterNameDiscoverer(P.class.getName());
	}

	@Test
	public void getParameterNamesInterfaceSingleParam() {
		assertThat(this.discoverer
				.getParameterNames(ReflectionUtils.findMethod(Dao.class, "findMessageByTo", String.class)))
						.isEqualTo(new String[] { "to" });
	}

	@Test
	public void getParameterNamesInterfaceSingleParamAnnotatedWithMultiParams() {
		assertThat(this.discoverer.getParameterNames(
				ReflectionUtils.findMethod(Dao.class, "findMessageByToAndFrom", String.class, String.class)))
						.isEqualTo(new String[] { "to", null });
	}

	@Test
	public void getParameterNamesInterfaceNoAnnotation() {
		assertThat(this.discoverer
				.getParameterNames(ReflectionUtils.findMethod(Dao.class, "findMessageByIdNoAnnotation", String.class)))
						.isNull();
	}

	@Test
	public void getParameterNamesClassSingleParam() {
		assertThat(this.discoverer
				.getParameterNames(ReflectionUtils.findMethod(Dao.class, "findMessageByTo", String.class)))
						.isEqualTo(new String[] { "to" });
	}

	@Test
	public void getParameterNamesClassSingleParamAnnotatedWithMultiParams() {
		assertThat(this.discoverer.getParameterNames(
				ReflectionUtils.findMethod(Dao.class, "findMessageByToAndFrom", String.class, String.class)))
						.isEqualTo(new String[] { "to", null });
	}

	@Test
	public void getParameterNamesClassNoAnnotation() {
		assertThat(this.discoverer
				.getParameterNames(ReflectionUtils.findMethod(Dao.class, "findMessageByIdNoAnnotation", String.class)))
						.isNull();
	}

	@Test
	public void getParameterNamesConstructor() throws Exception {
		assertThat(this.discoverer.getParameterNames(Impl.class.getDeclaredConstructor(String.class)))
				.isEqualTo(new String[] { "id" });
	}

	@Test
	public void getParameterNamesConstructorNoAnnotation() throws Exception {
		assertThat(this.discoverer.getParameterNames(Impl.class.getDeclaredConstructor(Long.class))).isNull();
	}

	@Test
	public void getParameterNamesClassAnnotationOnInterface() {
		assertThat(this.discoverer
				.getParameterNames(ReflectionUtils.findMethod(DaoImpl.class, "findMessageByTo", String.class)))
						.isEqualTo(new String[] { "to" });
		assertThat(this.discoverer
				.getParameterNames(ReflectionUtils.findMethod(Dao.class, "findMessageByTo", String.class)))
						.isEqualTo(new String[] { "to" });
	}

	@Test
	public void getParameterNamesClassAnnotationOnImpl() {
		assertThat(this.discoverer.getParameterNames(
				ReflectionUtils.findMethod(Dao.class, "findMessageByToAndFrom", String.class, String.class)))
						.isEqualTo(new String[] { "to", null });
		assertThat(this.discoverer.getParameterNames(
				ReflectionUtils.findMethod(DaoImpl.class, "findMessageByToAndFrom", String.class, String.class)))
						.isEqualTo(new String[] { "to", "from" });
	}

	@Test
	public void getParameterNamesClassAnnotationOnBaseClass() {
		assertThat(this.discoverer
				.getParameterNames(ReflectionUtils.findMethod(Dao.class, "findMessageByIdNoAnnotation", String.class)))
						.isNull();
		assertThat(this.discoverer.getParameterNames(
				ReflectionUtils.findMethod(DaoImpl.class, "findMessageByIdNoAnnotation", String.class)))
						.isEqualTo(new String[] { "id" });
	}

	interface Dao {

		String findMessageByTo(@P("to") String to);

		String findMessageByToAndFrom(@P("to") String to, String from);

		String findMessageByIdNoAnnotation(String id);

	}

	static class BaseDaoImpl {

		public String findMessageByIdNoAnnotation(@P("id") String id) {
			return null;
		}

	}

	static class DaoImpl extends BaseDaoImpl implements Dao {

		@Override
		public String findMessageByTo(String to) {
			return null;
		}

		@Override
		public String findMessageByToAndFrom(@P("to") String to, @P("from") String from) {
			return null;
		}

	}

	static class Impl {

		Impl(Long dataSourceId) {
		}

		Impl(@P("id") String dataSourceId) {
		}

		String findMessageByTo(@P("to") String to) {
			return null;
		}

		String findMessageByToAndFrom(@P("to") String to, String from) {
			return null;
		}

		String findMessageByIdNoAnnotation(String id) {
			return null;
		}

	}

}

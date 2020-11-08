package org.springframework.security.config.annotation;

import org.junit.Before;
import org.junit.Test;

import org.springframework.core.Ordered;

import static org.assertj.core.api.Assertions.assertThat;

public class SecurityConfigurerAdapterTests {

	ConcereteSecurityConfigurerAdapter adapter;

	@Before
	public void setup() {
		this.adapter = new ConcereteSecurityConfigurerAdapter();
	}

	@Test
	public void postProcessObjectPostProcessorsAreSorted() {
		this.adapter.addObjectPostProcessor(new OrderedObjectPostProcessor(Ordered.LOWEST_PRECEDENCE));
		this.adapter.addObjectPostProcessor(new OrderedObjectPostProcessor(Ordered.HIGHEST_PRECEDENCE));
		assertThat(this.adapter.postProcess("hi"))
				.isEqualTo("hi " + Ordered.HIGHEST_PRECEDENCE + " " + Ordered.LOWEST_PRECEDENCE);
	}

	static class OrderedObjectPostProcessor implements ObjectPostProcessor<String>, Ordered {

		private final int order;

		OrderedObjectPostProcessor(int order) {
			this.order = order;
		}

		@Override
		public int getOrder() {
			return this.order;
		}

		@Override
		@SuppressWarnings("unchecked")
		public String postProcess(String object) {
			return object + " " + this.order;
		}

	}

}

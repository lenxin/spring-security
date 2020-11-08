package org.springframework.security.config.annotation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author Rob Winch
 * @author Josh Cummings
 *
 */
public class SecurityConfigurerAdapterClosureTests {

	ConcereteSecurityConfigurerAdapter conf = new ConcereteSecurityConfigurerAdapter();

	@Test
	public void addPostProcessorClosureWhenPostProcessThenGetsApplied() throws Exception {
		SecurityBuilder<Object> builder = mock(SecurityBuilder.class);
		this.conf.addObjectPostProcessor(new ObjectPostProcessor<List<String>>() {
			@Override
			public <O extends List<String>> O postProcess(O l) {
				l.add("a");
				return l;
			}
		});
		this.conf.init(builder);
		this.conf.configure(builder);
		assertThat(this.conf.list).contains("a");
	}

	static class ConcereteSecurityConfigurerAdapter extends SecurityConfigurerAdapter<Object, SecurityBuilder<Object>> {

		private List<Object> list = new ArrayList<>();

		@Override
		public void configure(SecurityBuilder<Object> builder) throws Exception {
			this.list = postProcess(this.list);
		}

		ConcereteSecurityConfigurerAdapter list(List<Object> l) {
			this.list = l;
			return this;
		}

	}

}

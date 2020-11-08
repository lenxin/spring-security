package org.springframework.security.config.debug;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.security.config.BeanIds;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.debug.DebugFilter;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @author Josh Cummings
 */
public class SecurityDebugBeanFactoryPostProcessorTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Test
	public void contextRefreshWhenInDebugModeAndDependencyHasAutowiredConstructorThenDebugModeStillWorks() {
		// SEC-1885
		this.spring.configLocations(
				"classpath:org/springframework/security/config/debug/SecurityDebugBeanFactoryPostProcessorTests-context.xml")
				.autowire();
		assertThat(this.spring.getContext().getBean(BeanIds.SPRING_SECURITY_FILTER_CHAIN))
				.isInstanceOf(DebugFilter.class);
		assertThat(this.spring.getContext().getBean(BeanIds.FILTER_CHAIN_PROXY)).isInstanceOf(FilterChainProxy.class);
	}

}

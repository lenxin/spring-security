package org.springframework.security.performance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StopWatch;

import static org.assertj.core.api.Assertions.fail;

/**
 * @author Luke Taylor
 */
@ContextConfiguration(locations = { "/protect-pointcut-performance-app-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ProtectPointcutPerformanceTests implements ApplicationContextAware {

	ApplicationContext ctx;

	@Before
	public void clearContext() {
		SecurityContextHolder.clearContext();
	}

	// Method for use with profiler
	@Test
	public void usingPrototypeDoesNotParsePointcutOnEachCall() {
		StopWatch sw = new StopWatch();
		sw.start();
		for (int i = 0; i < 1000; i++) {
			try {
				SessionRegistry reg = (SessionRegistry) this.ctx.getBean("sessionRegistryPrototype");
				reg.getAllPrincipals();
				fail("Expected AuthenticationCredentialsNotFoundException");
			}
			catch (AuthenticationCredentialsNotFoundException expected) {
			}
		}
		sw.stop();
		// assertThat(sw.getTotalTimeMillis() < 1000).isTrue();

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

}

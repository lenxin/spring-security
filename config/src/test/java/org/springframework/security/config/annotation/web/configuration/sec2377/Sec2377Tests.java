package org.springframework.security.config.annotation.web.configuration.sec2377;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.config.annotation.web.configuration.sec2377.a.Sec2377AConfig;
import org.springframework.security.config.annotation.web.configuration.sec2377.b.Sec2377BConfig;
import org.springframework.security.config.test.SpringTestRule;

/**
 * @author Rob Winch
 * @author Josh Cummings
 */
public class Sec2377Tests {

	@Rule
	public final SpringTestRule parent = new SpringTestRule();

	@Rule
	public final SpringTestRule child = new SpringTestRule();

	@Test
	public void refreshContextWhenParentAndChildRegisteredThenNoException() {
		this.parent.register(Sec2377AConfig.class).autowire();
		ConfigurableApplicationContext context = this.child.register(Sec2377BConfig.class).getContext();
		context.setParent(this.parent.getContext());
		this.child.autowire();
	}

}

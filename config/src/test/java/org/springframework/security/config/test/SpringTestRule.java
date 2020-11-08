package org.springframework.security.config.test;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import org.springframework.security.test.context.TestSecurityContextHolder;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class SpringTestRule extends SpringTestContext implements MethodRule {

	@Override
	public Statement apply(Statement base, FrameworkMethod method, Object target) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				setTest(target);
				try {
					base.evaluate();
				}
				finally {
					TestSecurityContextHolder.clearContext();
					close();
				}
			}
		};
	}

}

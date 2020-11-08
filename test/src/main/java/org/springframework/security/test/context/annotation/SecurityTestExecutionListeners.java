package org.springframework.security.test.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.ReactorContextTestExecutionListener;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;

/**
 * There are many times a user may want to use Spring Security's test support (i.e.
 * WithMockUser) but have no need for any other {@link TestExecutionListeners} (i.e. no
 * need to setup an {@link ApplicationContext}). This annotation is a meta annotation that
 * only enables Spring Security's {@link TestExecutionListeners}.
 *
 * @author Rob Winch
 * @since 4.0.2
 * @see WithSecurityContextTestExecutionListener
 * @see ReactorContextTestExecutionListener
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestExecutionListeners(inheritListeners = false,
		listeners = { WithSecurityContextTestExecutionListener.class, ReactorContextTestExecutionListener.class })
public @interface SecurityTestExecutionListeners {

}

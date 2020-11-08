package org.springframework.security.test.context.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.TestContext;
import org.springframework.test.web.servlet.MockMvc;

/**
 * When used with {@link WithSecurityContextTestExecutionListener} this annotation can be
 * added to a test method to emulate running with a {@link UserDetails} returned from the
 * {@link UserDetailsService}. In order to work with {@link MockMvc} The
 * {@link SecurityContext} that is used will have the following properties:
 *
 * <ul>
 * <li>The {@link SecurityContext} created with be that of
 * {@link SecurityContextHolder#createEmptyContext()}</li>
 * <li>It will be populated with an {@link UsernamePasswordAuthenticationToken} that uses
 * the username of {@link #value()}.
 * </ul>
 *
 * @see WithMockUser
 *
 * @author Rob Winch
 * @since 4.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithUserDetailsSecurityContextFactory.class)
public @interface WithUserDetails {

	/**
	 * The username to look up in the {@link UserDetailsService}
	 * @return
	 */
	String value() default "user";

	/**
	 * The bean name for the {@link UserDetailsService} to use. If this is not provided,
	 * then the lookup is done by type and expects only a single
	 * {@link UserDetailsService} bean to be exposed.
	 * @return the bean name for the {@link UserDetailsService} to use.
	 * @since 4.1
	 */
	String userDetailsServiceBeanName() default "";

	/**
	 * Determines when the {@link SecurityContext} is setup. The default is before
	 * {@link TestExecutionEvent#TEST_METHOD} which occurs during
	 * {@link org.springframework.test.context.TestExecutionListener#beforeTestMethod(TestContext)}
	 * @return the {@link TestExecutionEvent} to initialize before
	 * @since 5.1
	 */
	@AliasFor(annotation = WithSecurityContext.class)
	TestExecutionEvent setupBefore() default TestExecutionEvent.TEST_METHOD;

}

package org.springframework.security.test.context.support;

import java.lang.annotation.Annotation;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.TestSecurityContextHolder;

/**
 * An API that works with WithUserTestExcecutionListener for creating a
 * {@link SecurityContext} that is populated in the {@link TestSecurityContextHolder}.
 *
 * @param <A>
 * @author Rob Winch
 * @since 4.0
 * @see WithSecurityContext
 * @see WithMockUser
 * @see WithUserDetails
 */
public interface WithSecurityContextFactory<A extends Annotation> {

	/**
	 * Create a {@link SecurityContext} given an Annotation.
	 * @param annotation the {@link Annotation} to create the {@link SecurityContext}
	 * from. Cannot be null.
	 * @return the {@link SecurityContext} to use. Cannot be null.
	 */
	SecurityContext createSecurityContext(A annotation);

}

package org.springframework.security.test.context.showcase;

import org.springframework.security.test.context.support.WithSecurityContext;

/**
 * @author Rob Winch
 */
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

	/**
	 * The username to be used. The default is rob
	 * @return
	 */
	String username() default "rob";

	/**
	 * The roles to use. The default is "USER". A
	 * {@link org.springframework.security.core.GrantedAuthority} will be created for each
	 * value within roles. Each value in roles will automatically be prefixed with
	 * "ROLE_". For example, the default will result in "ROLE_USER" being used.
	 * @return
	 */
	String[] roles() default { "USER" };

	/**
	 * The name of the user
	 * @return
	 */
	String name() default "Rob Winch";

}

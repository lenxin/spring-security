package org.springframework.security.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that is used to resolve the
 * {@link org.springframework.security.core.context.SecurityContext} as a method argument.
 *
 * @author Dan Zheng
 * @since 5.2
 *
 * <p>
 * See: <a href=
 * "{@docRoot}/org/springframework/security/web/bind/support/CurrentSecurityContextArgumentResolver.html"
 * > CurrentSecurityContextArgumentResolver</a> For Servlet
 * </p>
 *
 * <p>
 * See: <a href=
 * "{@docRoot}/org/springframework/security/web/reactive/result/method/annotation/CurrentSecurityContextArgumentResolver.html"
 * > CurrentSecurityContextArgumentResolver</a> For WebFlux
 * </p>
 */
@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentSecurityContext {

	/**
	 * True if a {@link ClassCastException} should be thrown when the current
	 * {@link org.springframework.security.core.context.SecurityContext} is the incorrect
	 * type. Default is false.
	 * @return whether or not to error on an invalid type
	 */
	boolean errorOnInvalidType() default false;

	/**
	 * If specified, will use the provided SpEL expression to resolve the security
	 * context. This is convenient if applications need to transform the result.
	 *
	 * For example, if an application needs to extract its custom {@code Authentication}
	 * implementation, then it could specify the appropriate SpEL like so:
	 *
	 * <pre>
	 * &#64;CurrentSecurityContext(expression = "authentication") CustomAuthentication authentication
	 * </pre>
	 * @return the expression to use
	 */
	String expression() default "";

}

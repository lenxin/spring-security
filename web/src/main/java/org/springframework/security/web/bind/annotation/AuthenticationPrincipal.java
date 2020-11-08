package org.springframework.security.web.bind.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.Authentication;

/**
 * Annotation that binds a method parameter or method return value to the
 * {@link Authentication#getPrincipal()}. This is necessary to signal that the argument
 * should be resolved to the current user rather than a user that might be edited on a
 * form.
 * @deprecated Use
 * {@link org.springframework.security.core.annotation.AuthenticationPrincipal} instead.
 *
 * @author Rob Winch
 * @since 3.2
 */
@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface AuthenticationPrincipal {

	/**
	 * True if a {@link ClassCastException} should be thrown when the current
	 * {@link Authentication#getPrincipal()} is the incorrect type. Default is false.
	 * @return
	 */
	boolean errorOnInvalidType() default false;

}

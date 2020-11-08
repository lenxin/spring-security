package org.springframework.security.core.parameters;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that can be used along with {@link AnnotationParameterNameDiscoverer} to
 * specify parameter names. This is useful for interfaces prior to JDK 8 which cannot
 * contain the parameter names.
 *
 * @see AnnotationParameterNameDiscoverer
 *
 * @author Rob Winch
 * @since 5.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface P {

	/**
	 * The parameter name
	 * @return
	 */
	String value();

}

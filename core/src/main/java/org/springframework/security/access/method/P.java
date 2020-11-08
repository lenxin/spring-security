package org.springframework.security.access.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.parameters.AnnotationParameterNameDiscoverer;

/**
 * An annotation that can be used along with {@link AnnotationParameterNameDiscoverer} to
 * specify parameter names. This is useful for interfaces prior to JDK 8 which cannot
 * contain the parameter names.
 *
 * @see AnnotationParameterNameDiscoverer
 *
 * @author Rob Winch
 * @since 3.2
 * @deprecated use @{code org.springframework.security.core.parameters.P}
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface P {

	/**
	 * The parameter name
	 * @return
	 */
	String value();

}

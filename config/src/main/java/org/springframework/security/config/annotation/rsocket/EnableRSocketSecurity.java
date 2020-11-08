package org.springframework.security.config.annotation.rsocket;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Add this annotation to a {@code Configuration} class to have Spring Security
 * {@link RSocketSecurity} support added.
 *
 * @author Rob Winch
 * @since 5.2
 * @see RSocketSecurity
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ RSocketSecurityConfiguration.class, SecuritySocketAcceptorInterceptorConfiguration.class })
public @interface EnableRSocketSecurity {

}

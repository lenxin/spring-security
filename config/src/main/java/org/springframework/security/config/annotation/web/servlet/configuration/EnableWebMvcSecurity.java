package org.springframework.security.config.annotation.web.servlet.configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;

/**
 * Add this annotation to an {@code @Configuration} class to have the Spring Security
 * configuration integrate with Spring MVC.
 * @deprecated Use EnableWebSecurity instead which will automatically add the Spring MVC
 * related Security items.
 * @author Rob Winch
 * @since 3.2
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(WebMvcSecurityConfiguration.class)
@EnableGlobalAuthentication
@Configuration
@Deprecated
public @interface EnableWebMvcSecurity {

}

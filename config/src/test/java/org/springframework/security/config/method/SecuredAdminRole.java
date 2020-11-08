package org.springframework.security.config.method;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.access.annotation.Secured;

/**
 * @author Rob Winch
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Secured("ROLE_ADMIN")
public @interface SecuredAdminRole {

}

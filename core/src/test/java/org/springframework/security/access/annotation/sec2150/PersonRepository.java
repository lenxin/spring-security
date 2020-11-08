package org.springframework.security.access.annotation.sec2150;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Note that JSR-256 states that annotations have no impact when placed on interfaces, so
 * SEC-2150 is not impacted by JSR-256 support.
 *
 * @author Rob Winch
 *
 */
@Secured("ROLE_PERSON")
@PreAuthorize("hasRole('ROLE_PERSON')")
public interface PersonRepository extends CrudRepository {

}

package org.springframework.security.config.annotation.issue50.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.issue50.domain.User;

/**
 * @author Rob Winch
 *
 */
public interface UserRepository extends CrudRepository<User, String> {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	User findByUsername(String username);

}

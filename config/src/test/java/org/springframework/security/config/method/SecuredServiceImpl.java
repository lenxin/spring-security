package org.springframework.security.config.method;

/**
 * @author Rob Winch
 *
 */
public class SecuredServiceImpl {

	@SecuredAdminRole
	public void securedAdminRole() {
	}

}

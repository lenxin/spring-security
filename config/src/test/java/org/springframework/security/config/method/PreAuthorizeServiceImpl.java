package org.springframework.security.config.method;

/**
 * @author Rob Winch
 *
 */
public class PreAuthorizeServiceImpl {

	@PreAuthorizeAdminRole
	public void preAuthorizeAdminRole() {
	}

	@ContactPermission
	public void contactPermission(Contact contact) {
	}

}

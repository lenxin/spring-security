package org.springframework.security.access.annotation;

import java.io.Serializable;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 */
@Secured({ "ROLE_USER" })
@PermitAll
public interface BusinessService extends Serializable {

	@Secured({ "ROLE_ADMIN" })
	@RolesAllowed({ "ROLE_ADMIN" })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void someAdminMethod();

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
	void someUserAndAdminMethod();

	@Secured({ "ROLE_USER" })
	@RolesAllowed({ "ROLE_USER" })
	void someUserMethod1();

	@Secured({ "ROLE_USER" })
	@RolesAllowed({ "ROLE_USER" })
	void someUserMethod2();

	@RolesAllowed({ "USER" })
	void rolesAllowedUser();

	int someOther(String s);

	int someOther(int input);

	List<?> methodReturningAList(List<?> someList);

	Object[] methodReturningAnArray(Object[] someArray);

	List<?> methodReturningAList(String userName, String extraParam);

}

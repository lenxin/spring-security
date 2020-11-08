package org.springframework.security.access.annotation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

/**
 * @author Luke Taylor
 */
@PermitAll
public class Jsr250BusinessServiceImpl implements BusinessService {

	@Override
	@RolesAllowed("ROLE_USER")
	public void someUserMethod1() {
	}

	@Override
	@RolesAllowed("ROLE_USER")
	public void someUserMethod2() {
	}

	@Override
	@RolesAllowed({ "ROLE_USER", "ROLE_ADMIN" })
	public void someUserAndAdminMethod() {
	}

	@Override
	@RolesAllowed("ROLE_ADMIN")
	public void someAdminMethod() {
	}

	@Override
	public int someOther(String input) {
		return 0;
	}

	@Override
	public int someOther(int input) {
		return input;
	}

	@Override
	public List<?> methodReturningAList(List<?> someList) {
		return someList;
	}

	@Override
	public List<?> methodReturningAList(String userName, String arg2) {
		return new ArrayList<>();
	}

	@Override
	public Object[] methodReturningAnArray(Object[] someArray) {
		return null;
	}

	@Override
	@RolesAllowed({ "USER" })
	public void rolesAllowedUser() {
	}

}

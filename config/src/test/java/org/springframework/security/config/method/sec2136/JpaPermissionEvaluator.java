package org.springframework.security.config.method.sec2136;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

/**
 * @author Rob Winch
 *
 */
public class JpaPermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private EntityManager entityManager;

	public JpaPermissionEvaluator() {
		System.out.println("initializing " + this);
	}

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		return true;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		return true;
	}

}

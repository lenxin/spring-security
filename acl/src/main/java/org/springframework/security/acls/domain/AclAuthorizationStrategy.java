package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.Acl;

/**
 * Strategy used by {@link AclImpl} to determine whether a principal is permitted to call
 * adminstrative methods on the <code>AclImpl</code>.
 *
 * @author Ben Alex
 */
public interface AclAuthorizationStrategy {

	int CHANGE_OWNERSHIP = 0;

	int CHANGE_AUDITING = 1;

	int CHANGE_GENERAL = 2;

	void securityCheck(Acl acl, int changeType);

}

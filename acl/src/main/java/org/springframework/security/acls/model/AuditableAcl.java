package org.springframework.security.acls.model;

/**
 * A mutable ACL that provides audit capabilities.
 *
 * @author Ben Alex
 */
public interface AuditableAcl extends MutableAcl {

	void updateAuditing(int aceIndex, boolean auditSuccess, boolean auditFailure);

}

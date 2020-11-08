package org.springframework.security.acls.model;

/**
 * Represents an ACE that provides auditing information.
 *
 * @author Ben Alex
 */
public interface AuditableAccessControlEntry extends AccessControlEntry {

	boolean isAuditFailure();

	boolean isAuditSuccess();

}

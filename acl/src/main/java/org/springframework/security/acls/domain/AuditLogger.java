package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.AccessControlEntry;

/**
 * Used by <code>AclImpl</code> to log audit events.
 *
 * @author Ben Alex
 */
public interface AuditLogger {

	void logIfNeeded(boolean granted, AccessControlEntry ace);

}

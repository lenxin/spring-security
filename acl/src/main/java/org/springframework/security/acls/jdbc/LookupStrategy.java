package org.springframework.security.acls.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;

/**
 * Performs lookups for {@link org.springframework.security.acls.model.AclService}.
 *
 * @author Ben Alex
 */
public interface LookupStrategy {

	/**
	 * Perform database-specific optimized lookup.
	 * @param objects the identities to lookup (required)
	 * @param sids the SIDs for which identities are required (may be <tt>null</tt> -
	 * implementations may elect not to provide SID optimisations)
	 * @return a <tt>Map</tt> where keys represent the {@link ObjectIdentity} of the
	 * located {@link Acl} and values are the located {@link Acl} (never <tt>null</tt>
	 * although some entries may be missing; this method should not throw
	 * {@link NotFoundException}, as a chain of {@link LookupStrategy}s may be used to
	 * automatically create entries if required)
	 */
	Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids);

}

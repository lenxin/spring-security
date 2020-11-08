package org.springframework.security.acls.model;

import java.io.Serializable;

/**
 * A security identity recognised by the ACL system.
 *
 * <p>
 * This interface provides indirection between actual security objects (eg principals,
 * roles, groups etc) and what is stored inside an <code>Acl</code>. This is because an
 * <code>Acl</code> will not store an entire security object, but only an abstraction of
 * it. This interface therefore provides a simple way to compare these abstracted security
 * identities with other security identities and actual security objects.
 * </p>
 *
 * @author Ben Alex
 */
public interface Sid extends Serializable {

	/**
	 * Refer to the <code>java.lang.Object</code> documentation for the interface
	 * contract.
	 * @param obj to be compared
	 * @return <code>true</code> if the objects are equal, <code>false</code> otherwise
	 */
	@Override
	boolean equals(Object obj);

	/**
	 * Refer to the <code>java.lang.Object</code> documentation for the interface
	 * contract.
	 * @return a hash code representation of this object
	 */
	@Override
	int hashCode();

}

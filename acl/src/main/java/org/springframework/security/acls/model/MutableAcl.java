package org.springframework.security.acls.model;

import java.io.Serializable;

/**
 * A mutable <tt>Acl</tt>.
 * <p>
 * A mutable ACL must ensure that appropriate security checks are performed before
 * allowing access to its methods.
 *
 * @author Ben Alex
 */
public interface MutableAcl extends Acl {

	void deleteAce(int aceIndex) throws NotFoundException;

	/**
	 * Obtains an identifier that represents this <tt>MutableAcl</tt>.
	 * @return the identifier, or <tt>null</tt> if unsaved
	 */
	Serializable getId();

	void insertAce(int atIndexLocation, Permission permission, Sid sid, boolean granting) throws NotFoundException;

	/**
	 * Changes the present owner to a different owner.
	 * @param newOwner the new owner (mandatory; cannot be null)
	 */
	void setOwner(Sid newOwner);

	/**
	 * Change the value returned by {@link Acl#isEntriesInheriting()}.
	 * @param entriesInheriting the new value
	 */
	void setEntriesInheriting(boolean entriesInheriting);

	/**
	 * Changes the parent of this ACL.
	 * @param newParent the new parent
	 */
	void setParent(Acl newParent);

	void updateAce(int aceIndex, Permission permission) throws NotFoundException;

}

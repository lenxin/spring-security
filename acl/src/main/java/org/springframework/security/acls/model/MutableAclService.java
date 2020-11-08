package org.springframework.security.acls.model;

/**
 * Provides support for creating and storing <code>Acl</code> instances.
 *
 * @author Ben Alex
 */
public interface MutableAclService extends AclService {

	/**
	 * Creates an empty <code>Acl</code> object in the database. It will have no entries.
	 * The returned object will then be used to add entries.
	 * @param objectIdentity the object identity to create
	 * @return an ACL object with its ID set
	 * @throws AlreadyExistsException if the passed object identity already has a record
	 */
	MutableAcl createAcl(ObjectIdentity objectIdentity) throws AlreadyExistsException;

	/**
	 * Removes the specified entry from the database.
	 * @param objectIdentity the object identity to remove
	 * @param deleteChildren whether to cascade the delete to children
	 * @throws ChildrenExistException if the deleteChildren argument was
	 * <code>false</code> but children exist
	 */
	void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren) throws ChildrenExistException;

	/**
	 * Changes an existing <code>Acl</code> in the database.
	 * @param acl to modify
	 * @throws NotFoundException if the relevant record could not be found (did you
	 * remember to use {@link #createAcl(ObjectIdentity)} to create the object, rather
	 * than creating it with the <code>new</code> keyword?)
	 */
	MutableAcl updateAcl(MutableAcl acl) throws NotFoundException;

}

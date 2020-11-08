package org.springframework.security.acls.model;

/**
 * A mutable ACL that provides ownership capabilities.
 *
 * <p>
 * Generally the owner of an ACL is able to call any ACL mutator method, as well as assign
 * a new owner.
 *
 * @author Ben Alex
 */
public interface OwnershipAcl extends MutableAcl {

	@Override
	void setOwner(Sid newOwner);

}

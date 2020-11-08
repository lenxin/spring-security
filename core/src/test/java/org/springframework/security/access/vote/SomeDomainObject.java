package org.springframework.security.access.vote;

/**
 * Simple domain object, used by {@link BasicAclEntryVoterTests}.
 *
 * @author Ben Alex
 */
public class SomeDomainObject {

	private String identity;

	public SomeDomainObject(String identity) {
		this.identity = identity;
	}

	public String getParent() {
		return "parentOf" + this.identity;
	}

}

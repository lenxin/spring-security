package org.springframework.security.access.vote;

/**
 * Used by {@link BasicAclEntryVoterTests} so it can create a
 * <code>MethodInvocation</code> contining <code>SomeDomainObject</code>.
 *
 * @author Ben Alex
 */
public class SomeDomainObjectManager {

	public void someServiceMethod(SomeDomainObject someDomainObject) {
	}

}

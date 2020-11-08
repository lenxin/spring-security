package org.springframework.security.authentication.jaas;

import java.security.Principal;
import java.util.Set;

/**
 * The AuthorityGranter interface is used to map a given principal to role names.
 * <p>
 * If a Windows NT login module were to be used from JAAS, an AuthrityGranter
 * implementation could be created to map a NT Group Principal to a ROLE_USER role for
 * instance.
 *
 * @author Ray Krueger
 */
public interface AuthorityGranter {

	/**
	 * The grant method is called for each principal returned from the LoginContext
	 * subject. If the AuthorityGranter wishes to grant any authorities, it should return
	 * a java.util.Set containing the role names it wishes to grant, such as ROLE_USER. If
	 * the AuthrityGranter does not wish to grant any authorities it should return null.
	 * <p>
	 * The set may contain any object as all objects in the returned set will be passed to
	 * the JaasGrantedAuthority constructor using toString().
	 * @param principal One of the principals from the
	 * LoginContext.getSubect().getPrincipals() method.
	 * @return the role names to grant, or null, meaning no roles should be granted to the
	 * principal.
	 */
	Set<String> grant(Principal principal);

}

package samples.jaas;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.authentication.jaas.AuthorityGranter;

/**
 * An AuthorityGranter that always grants "ROLE_USER".
 *
 * @author Rob Winch
 */
public class RoleUserAuthorityGranter implements AuthorityGranter {

	public Set<String> grant(Principal principal) {
		return Collections.singleton("ROLE_USER");
	}
}

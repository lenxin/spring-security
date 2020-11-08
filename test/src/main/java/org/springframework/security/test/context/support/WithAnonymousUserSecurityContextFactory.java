package org.springframework.security.test.context.support;

import java.util.List;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * A {@link WithAnonymousUserSecurityContextFactory} that runs with an
 * {@link AnonymousAuthenticationToken}. .
 *
 * @author Rob Winch
 * @since 4.1
 * @see WithUserDetails
 */
final class WithAnonymousUserSecurityContextFactory implements WithSecurityContextFactory<WithAnonymousUser> {

	@Override
	public SecurityContext createSecurityContext(WithAnonymousUser withUser) {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS");
		Authentication authentication = new AnonymousAuthenticationToken("key", "anonymous", authorities);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		return context;
	}

}

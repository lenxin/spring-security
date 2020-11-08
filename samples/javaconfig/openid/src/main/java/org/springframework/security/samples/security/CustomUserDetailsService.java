package org.springframework.security.samples.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

/**
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 *  <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to migrate</a>
 *  to <a href="https://openid.net/connect/">OpenID Connect</a>, which is supported by <code>spring-security-oauth2</code>.
 */
public class CustomUserDetailsService implements
		AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
			throws UsernameNotFoundException {
		return new User(token.getName(), "",
				AuthorityUtils.createAuthorityList("ROLE_USER"));
	}
}

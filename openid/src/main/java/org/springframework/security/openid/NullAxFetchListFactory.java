package org.springframework.security.openid;

import java.util.Collections;
import java.util.List;

/**
 * @author Luke Taylor
 * @since 3.1
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 * <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to
 * migrate</a> to <a href="https://openid.net/connect/">OpenID Connect</a>, which is
 * supported by <code>spring-security-oauth2</code>.
 */
@Deprecated
public class NullAxFetchListFactory implements AxFetchListFactory {

	@Override
	public List<OpenIDAttribute> createAttributeList(String identifier) {
		return Collections.emptyList();
	}

}

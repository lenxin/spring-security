package org.springframework.security.openid;

import java.util.List;

/**
 * A strategy which can be used by an OpenID consumer implementation, to dynamically
 * determine the attribute exchange information based on the OpenID identifier.
 * <p>
 * This allows the list of attributes for a fetch request to be tailored for different
 * OpenID providers, since they do not all support the same attributes.
 *
 * @author Luke Taylor
 * @since 3.1
 * @deprecated The OpenID 1.0 and 2.0 protocols have been deprecated and users are
 * <a href="https://openid.net/specs/openid-connect-migration-1_0.html">encouraged to
 * migrate</a> to <a href="https://openid.net/connect/">OpenID Connect</a>, which is
 * supported by <code>spring-security-oauth2</code>.
 */
@Deprecated
public interface AxFetchListFactory {

	/**
	 * Builds the list of attributes which should be added to the fetch request for the
	 * supplied OpenID identifier.
	 * @param identifier the claimed_identity
	 * @return the attributes to fetch for this identifier
	 */
	List<OpenIDAttribute> createAttributeList(String identifier);

}

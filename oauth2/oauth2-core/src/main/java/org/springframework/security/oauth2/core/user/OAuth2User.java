package org.springframework.security.oauth2.core.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

/**
 * A representation of a user {@code Principal} that is registered with an OAuth 2.0
 * Provider.
 *
 * <p>
 * An OAuth 2.0 user is composed of one or more attributes, for example, first name,
 * middle name, last name, email, phone number, address, etc. Each user attribute has a
 * &quot;name&quot; and &quot;value&quot; and is keyed by the &quot;name&quot; in
 * {@link #getAttributes()}.
 *
 * <p>
 * <b>NOTE:</b> Attribute names are <b>not</b> standardized between providers and
 * therefore will vary. Please consult the provider's API documentation for the set of
 * supported user attribute names.
 *
 * <p>
 * Implementation instances of this interface represent an
 * {@link OAuth2AuthenticatedPrincipal} which is associated to an {@link Authentication}
 * object and may be accessed via {@link Authentication#getPrincipal()}.
 *
 * @author Joe Grandja
 * @author Eddú Meléndez
 * @since 5.0
 * @see DefaultOAuth2User
 * @see OAuth2AuthenticatedPrincipal
 */
public interface OAuth2User extends OAuth2AuthenticatedPrincipal {

}

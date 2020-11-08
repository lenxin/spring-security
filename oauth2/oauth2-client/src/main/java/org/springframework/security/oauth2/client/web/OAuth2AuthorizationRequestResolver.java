package org.springframework.security.oauth2.client.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

/**
 * Implementations of this interface are capable of resolving an
 * {@link OAuth2AuthorizationRequest} from the provided {@code HttpServletRequest}. Used
 * by the {@link OAuth2AuthorizationRequestRedirectFilter} for resolving Authorization
 * Requests.
 *
 * @author Joe Grandja
 * @author Rob Winch
 * @since 5.1
 * @see OAuth2AuthorizationRequest
 * @see OAuth2AuthorizationRequestRedirectFilter
 */
public interface OAuth2AuthorizationRequestResolver {

	/**
	 * Returns the {@link OAuth2AuthorizationRequest} resolved from the provided
	 * {@code HttpServletRequest} or {@code null} if not available.
	 * @param request the {@code HttpServletRequest}
	 * @return the resolved {@link OAuth2AuthorizationRequest} or {@code null} if not
	 * available
	 */
	OAuth2AuthorizationRequest resolve(HttpServletRequest request);

	/**
	 * Returns the {@link OAuth2AuthorizationRequest} resolved from the provided
	 * {@code HttpServletRequest} or {@code null} if not available.
	 * @param request the {@code HttpServletRequest}
	 * @param clientRegistrationId the clientRegistrationId to use
	 * @return the resolved {@link OAuth2AuthorizationRequest} or {@code null} if not
	 * available
	 */
	OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId);

}

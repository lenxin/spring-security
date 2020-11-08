package org.springframework.security.web.authentication.preauth.j2ee;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * This AbstractPreAuthenticatedProcessingFilter implementation is based on the J2EE
 * container-based authentication mechanism. It will use the J2EE user principal name as
 * the pre-authenticated principal.
 *
 * @author Ruud Senden
 * @since 2.0
 */
public class J2eePreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

	/**
	 * Return the J2EE user name.
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
		Object principal = (httpRequest.getUserPrincipal() != null) ? httpRequest.getUserPrincipal().getName() : null;
		this.logger.debug(LogMessage.format("PreAuthenticated J2EE principal: %s", principal));
		return principal;
	}

	/**
	 * For J2EE container-based authentication there is no generic way to retrieve the
	 * credentials, as such this method returns a fixed dummy value.
	 */
	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest httpRequest) {
		return "N/A";
	}

}

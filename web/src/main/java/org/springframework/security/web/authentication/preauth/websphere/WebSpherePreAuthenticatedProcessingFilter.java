package org.springframework.security.web.authentication.preauth.websphere;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * This AbstractPreAuthenticatedProcessingFilter implementation is based on WebSphere
 * authentication. It will use the WebSphere RunAs user principal name as the
 * pre-authenticated principal.
 *
 * @author Ruud Senden
 * @since 2.0
 */
public class WebSpherePreAuthenticatedProcessingFilter extends AbstractPreAuthenticatedProcessingFilter {

	private final WASUsernameAndGroupsExtractor wasHelper;

	/**
	 * Public constructor which overrides the default AuthenticationDetails class to be
	 * used.
	 */
	public WebSpherePreAuthenticatedProcessingFilter() {
		this(new DefaultWASUsernameAndGroupsExtractor());
	}

	WebSpherePreAuthenticatedProcessingFilter(WASUsernameAndGroupsExtractor wasHelper) {
		this.wasHelper = wasHelper;
		setAuthenticationDetailsSource(new WebSpherePreAuthenticatedWebAuthenticationDetailsSource());
	}

	/**
	 * Return the WebSphere user name.
	 */
	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpRequest) {
		Object principal = this.wasHelper.getCurrentUserName();
		this.logger.debug(LogMessage.format("PreAuthenticated WebSphere principal: %s", principal));
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

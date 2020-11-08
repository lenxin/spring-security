package org.springframework.security.web.authentication.preauth.x509;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.log.LogMessage;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author Luke Taylor
 */
public class X509AuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	private X509PrincipalExtractor principalExtractor = new SubjectDnX509PrincipalExtractor();

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		X509Certificate cert = extractClientCertificate(request);
		return (cert != null) ? this.principalExtractor.extractPrincipal(cert) : null;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return extractClientCertificate(request);
	}

	private X509Certificate extractClientCertificate(HttpServletRequest request) {
		X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
		if (certs != null && certs.length > 0) {
			this.logger.debug(LogMessage.format("X.509 client authentication certificate:%s", certs[0]));
			return certs[0];
		}
		this.logger.debug("No client certificate found in request.");
		return null;
	}

	public void setPrincipalExtractor(X509PrincipalExtractor principalExtractor) {
		this.principalExtractor = principalExtractor;
	}

}

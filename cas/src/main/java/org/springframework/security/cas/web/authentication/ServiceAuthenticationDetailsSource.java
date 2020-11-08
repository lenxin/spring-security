package org.springframework.security.cas.web.authentication;

import java.net.MalformedURLException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.util.Assert;

/**
 * The {@code AuthenticationDetailsSource} that is set on the
 * {@code CasAuthenticationFilter} should return a value that implements
 * {@code ServiceAuthenticationDetails} if the application needs to authenticate dynamic
 * service urls. The
 * {@code ServiceAuthenticationDetailsSource#buildDetails(HttpServletRequest)} creates a
 * default {@code ServiceAuthenticationDetails}.
 *
 * @author Rob Winch
 */
public class ServiceAuthenticationDetailsSource
		implements AuthenticationDetailsSource<HttpServletRequest, ServiceAuthenticationDetails> {

	private final Pattern artifactPattern;

	private ServiceProperties serviceProperties;

	/**
	 * Creates an implementation that uses the specified ServiceProperties and the default
	 * CAS artifactParameterName.
	 * @param serviceProperties The ServiceProperties to use to construct the serviceUrl.
	 */
	public ServiceAuthenticationDetailsSource(ServiceProperties serviceProperties) {
		this(serviceProperties, ServiceProperties.DEFAULT_CAS_ARTIFACT_PARAMETER);
	}

	/**
	 * Creates an implementation that uses the specified artifactParameterName
	 * @param serviceProperties The ServiceProperties to use to construct the serviceUrl.
	 * @param artifactParameterName the artifactParameterName that is removed from the
	 * current URL. The result becomes the service url. Cannot be null and cannot be an
	 * empty String.
	 */
	public ServiceAuthenticationDetailsSource(ServiceProperties serviceProperties, String artifactParameterName) {
		Assert.notNull(serviceProperties, "serviceProperties cannot be null");
		this.serviceProperties = serviceProperties;
		this.artifactPattern = DefaultServiceAuthenticationDetails.createArtifactPattern(artifactParameterName);
	}

	/**
	 * @param context the {@code HttpServletRequest} object.
	 * @return the {@code ServiceAuthenticationDetails} containing information about the
	 * current request
	 */
	@Override
	public ServiceAuthenticationDetails buildDetails(HttpServletRequest context) {
		try {
			return new DefaultServiceAuthenticationDetails(this.serviceProperties.getService(), context,
					this.artifactPattern);
		}
		catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		}
	}

}

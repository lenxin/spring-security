package org.springframework.security.cas.web.authentication;

import java.io.Serializable;

import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.core.Authentication;

/**
 * In order for the {@link CasAuthenticationProvider} to provide the correct service url
 * to authenticate the ticket, the returned value of {@link Authentication#getDetails()}
 * should implement this interface when tickets can be sent to any URL rather than only
 * {@link ServiceProperties#getService()}.
 *
 * @author Rob Winch
 * @see ServiceAuthenticationDetailsSource
 */
public interface ServiceAuthenticationDetails extends Serializable {

	/**
	 * Gets the absolute service url (i.e. https://example.com/service/).
	 * @return the service url. Cannot be <code>null</code>.
	 */
	String getServiceUrl();

}

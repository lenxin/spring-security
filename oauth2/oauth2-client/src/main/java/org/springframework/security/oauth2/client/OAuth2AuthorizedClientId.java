package org.springframework.security.oauth2.client;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * The identifier for {@link OAuth2AuthorizedClient}.
 *
 * @author Vedran Pavic
 * @since 5.2
 * @see OAuth2AuthorizedClient
 * @see OAuth2AuthorizedClientService
 */
public final class OAuth2AuthorizedClientId implements Serializable {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final String clientRegistrationId;

	private final String principalName;

	/**
	 * Constructs an {@code OAuth2AuthorizedClientId} using the provided parameters.
	 * @param clientRegistrationId the identifier for the client's registration
	 * @param principalName the name of the End-User {@code Principal} (Resource Owner)
	 */
	public OAuth2AuthorizedClientId(String clientRegistrationId, String principalName) {
		Assert.hasText(clientRegistrationId, "clientRegistrationId cannot be empty");
		Assert.hasText(principalName, "principalName cannot be empty");
		this.clientRegistrationId = clientRegistrationId;
		this.principalName = principalName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		OAuth2AuthorizedClientId that = (OAuth2AuthorizedClientId) obj;
		return Objects.equals(this.clientRegistrationId, that.clientRegistrationId)
				&& Objects.equals(this.principalName, that.principalName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.clientRegistrationId, this.principalName);
	}

}

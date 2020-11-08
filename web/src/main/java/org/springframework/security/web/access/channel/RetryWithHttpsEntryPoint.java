package org.springframework.security.web.access.channel;

/**
 * Commences a secure channel by retrying the original request using HTTPS.
 * <p>
 * This entry point should suffice in most circumstances. However, it is not intended to
 * properly handle HTTP POSTs or other usage where a standard redirect would cause an
 * issue.
 * </p>
 *
 * @author Ben Alex
 */
public class RetryWithHttpsEntryPoint extends AbstractRetryEntryPoint {

	public RetryWithHttpsEntryPoint() {
		super("https://", 443);
	}

	@Override
	protected Integer getMappedPort(Integer mapFromPort) {
		return getPortMapper().lookupHttpsPort(mapFromPort);
	}

}

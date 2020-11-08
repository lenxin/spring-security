package org.springframework.security.web.access.channel;

/**
 * Commences an insecure channel by retrying the original request using HTTP.
 * <p>
 * This entry point should suffice in most circumstances. However, it is not intended to
 * properly handle HTTP POSTs or other usage where a standard redirect would cause an
 * issue.
 *
 * @author Ben Alex
 */
public class RetryWithHttpEntryPoint extends AbstractRetryEntryPoint {

	public RetryWithHttpEntryPoint() {
		super("http://", 80);
	}

	@Override
	protected Integer getMappedPort(Integer mapFromPort) {
		return getPortMapper().lookupHttpPort(mapFromPort);
	}

}

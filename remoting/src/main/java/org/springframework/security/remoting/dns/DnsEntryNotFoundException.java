package org.springframework.security.remoting.dns;

/**
 * This will be thrown if no entry matches the specified DNS query.
 *
 * @author Mike Wiesner
 * @since 3.0
 */
public class DnsEntryNotFoundException extends DnsLookupException {

	private static final long serialVersionUID = -947232730426775162L;

	public DnsEntryNotFoundException(String msg) {
		super(msg);
	}

	public DnsEntryNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}

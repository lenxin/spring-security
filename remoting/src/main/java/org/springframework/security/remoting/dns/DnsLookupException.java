package org.springframework.security.remoting.dns;

/**
 * This will be thrown for unknown DNS errors.
 *
 * @author Mike Wiesner
 * @since 3.0
 */
public class DnsLookupException extends RuntimeException {

	public DnsLookupException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public DnsLookupException(String msg) {
		super(msg);
	}

}

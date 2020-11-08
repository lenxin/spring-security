package org.springframework.security.web.header.writers;

/**
 * A {@link StaticHeadersWriter} that inserts headers to prevent content sniffing.
 * Specifically the following headers are set:
 * <ul>
 * <li>X-Content-Type-Options: nosniff</li>
 * </ul>
 *
 * @author Rob Winch
 * @since 3.2
 */
public final class XContentTypeOptionsHeaderWriter extends StaticHeadersWriter {

	/**
	 * Creates a new instance
	 */
	public XContentTypeOptionsHeaderWriter() {
		super("X-Content-Type-Options", "nosniff");
	}

}

package org.springframework.security.web.header.writers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.header.HeaderWriter;
import org.springframework.util.Assert;

/**
 * A {@link HeaderWriter} that delegates to several other {@link HeaderWriter}s.
 *
 * @author Ankur Pathak
 * @since 5.2
 */
public class CompositeHeaderWriter implements HeaderWriter {

	private final List<HeaderWriter> headerWriters;

	/**
	 * Creates a new instance.
	 * @param headerWriters the {@link HeaderWriter} instances to write out headers to the
	 * {@link HttpServletResponse}.
	 */
	public CompositeHeaderWriter(List<HeaderWriter> headerWriters) {
		Assert.notEmpty(headerWriters, "headerWriters cannot be empty");
		this.headerWriters = headerWriters;
	}

	@Override
	public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
		this.headerWriters.forEach((headerWriter) -> headerWriter.writeHeaders(request, response));
	}

}

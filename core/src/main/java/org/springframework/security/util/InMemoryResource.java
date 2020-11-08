package org.springframework.security.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import org.springframework.core.io.AbstractResource;
import org.springframework.util.Assert;

/**
 * An in memory implementation of Spring's {@link org.springframework.core.io.Resource}
 * interface.
 * <p>
 * Used to create a bean factory from an XML string, rather than a file.
 * </p>
 *
 * @author Luke Taylor
 */
public class InMemoryResource extends AbstractResource {

	private final byte[] source;

	private final String description;

	public InMemoryResource(String source) {
		this(source.getBytes());
	}

	public InMemoryResource(byte[] source) {
		this(source, null);
	}

	public InMemoryResource(byte[] source, String description) {
		Assert.notNull(source, "source cannot be null");
		this.source = source;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public InputStream getInputStream() {
		return new ByteArrayInputStream(this.source);
	}

	@Override
	public boolean equals(Object res) {
		if (!(res instanceof InMemoryResource)) {
			return false;
		}
		return Arrays.equals(this.source, ((InMemoryResource) res).source);
	}

	@Override
	public int hashCode() {
		return 1;
	}

}

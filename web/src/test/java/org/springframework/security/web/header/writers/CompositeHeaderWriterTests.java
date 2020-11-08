package org.springframework.security.web.header.writers;

import java.util.Arrays;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import org.springframework.security.web.header.HeaderWriter;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Tests for class {@link CompositeHeaderWriter}/.
 *
 * @author Ankur Pathak
 * @since 5.2
 */
public class CompositeHeaderWriterTests {

	@Test
	public void writeHeadersWhenConfiguredWithDelegatesThenInvokesEach() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		HeaderWriter one = mock(HeaderWriter.class);
		HeaderWriter two = mock(HeaderWriter.class);
		CompositeHeaderWriter headerWriter = new CompositeHeaderWriter(Arrays.asList(one, two));
		headerWriter.writeHeaders(request, response);
		verify(one).writeHeaders(request, response);
		verify(two).writeHeaders(request, response);
	}

	@Test
	public void constructorWhenPassingEmptyListThenThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CompositeHeaderWriter(Collections.emptyList()));
	}

}

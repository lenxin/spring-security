package org.springframework.security.web.util.matcher;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mike Wiesner
 * @since 3.0.2
 */
public class ELRequestMatcherTests {

	@Test
	public void testHasIpAddressTrue() {
		ELRequestMatcher requestMatcher = new ELRequestMatcher("hasIpAddress('1.1.1.1')");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("1.1.1.1");
		assertThat(requestMatcher.matches(request)).isTrue();
	}

	@Test
	public void testHasIpAddressFalse() {
		ELRequestMatcher requestMatcher = new ELRequestMatcher("hasIpAddress('1.1.1.1')");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRemoteAddr("1.1.1.2");
		assertThat(requestMatcher.matches(request)).isFalse();
	}

	@Test
	public void testHasHeaderTrue() {
		ELRequestMatcher requestMatcher = new ELRequestMatcher("hasHeader('User-Agent','MSIE')");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("User-Agent", "MSIE");
		assertThat(requestMatcher.matches(request)).isTrue();
	}

	@Test
	public void testHasHeaderTwoEntries() {
		ELRequestMatcher requestMatcher = new ELRequestMatcher(
				"hasHeader('User-Agent','MSIE') or hasHeader('User-Agent','Mozilla')");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("User-Agent", "MSIE");
		assertThat(requestMatcher.matches(request)).isTrue();
		request = new MockHttpServletRequest();
		request.addHeader("User-Agent", "Mozilla");
		assertThat(requestMatcher.matches(request)).isTrue();
	}

	@Test
	public void testHasHeaderFalse() {
		ELRequestMatcher requestMatcher = new ELRequestMatcher("hasHeader('User-Agent','MSIE')");
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("User-Agent", "wrong");
		assertThat(requestMatcher.matches(request)).isFalse();
	}

	@Test
	public void testHasHeaderNull() {
		ELRequestMatcher requestMatcher = new ELRequestMatcher("hasHeader('User-Agent','MSIE')");
		MockHttpServletRequest request = new MockHttpServletRequest();
		assertThat(requestMatcher.matches(request)).isFalse();
	}

	@Test
	public void toStringThenFormatted() {
		ELRequestMatcher requestMatcher = new ELRequestMatcher("hasHeader('User-Agent','MSIE')");
		assertThat(requestMatcher.toString()).isEqualTo("EL [el=\"hasHeader('User-Agent','MSIE')\"]");
	}

}

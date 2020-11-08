package org.springframework.security.web;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link PortResolverImpl}.
 *
 * @author Ben Alex
 */
public class PortResolverImplTests {

	@Test
	public void testDetectsBuggyIeHttpRequest() {
		PortResolverImpl pr = new PortResolverImpl();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerPort(8443);
		request.setScheme("HTtP"); // proves case insensitive handling
		assertThat(pr.getServerPort(request)).isEqualTo(8080);
	}

	@Test
	public void testDetectsBuggyIeHttpsRequest() {
		PortResolverImpl pr = new PortResolverImpl();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServerPort(8080);
		request.setScheme("HTtPs"); // proves case insensitive handling
		assertThat(pr.getServerPort(request)).isEqualTo(8443);
	}

	@Test
	public void testDetectsEmptyPortMapper() {
		PortResolverImpl pr = new PortResolverImpl();
		assertThatIllegalArgumentException().isThrownBy(() -> pr.setPortMapper(null));
	}

	@Test
	public void testGettersSetters() {
		PortResolverImpl pr = new PortResolverImpl();
		assertThat(pr.getPortMapper() != null).isTrue();
		pr.setPortMapper(new PortMapperImpl());
		assertThat(pr.getPortMapper() != null).isTrue();
	}

	@Test
	public void testNormalOperation() {
		PortResolverImpl pr = new PortResolverImpl();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setScheme("http");
		request.setServerPort(1021);
		assertThat(pr.getServerPort(request)).isEqualTo(1021);
	}

}

package org.springframework.security.remoting.httpinvoker;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Test;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link AuthenticationSimpleHttpInvokerRequestExecutor}.
 *
 * @author Ben Alex
 * @author Rob Winch
 */
public class AuthenticationSimpleHttpInvokerRequestExecutorTests {

	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testNormalOperation() throws Exception {
		// Setup client-side context
		Authentication clientSideAuthentication = new UsernamePasswordAuthenticationToken("Aladdin", "open sesame");
		SecurityContextHolder.getContext().setAuthentication(clientSideAuthentication);
		// Create a connection and ensure our executor sets its
		// properties correctly
		AuthenticationSimpleHttpInvokerRequestExecutor executor = new AuthenticationSimpleHttpInvokerRequestExecutor();
		HttpURLConnection conn = new MockHttpURLConnection(new URL("https://localhost/"));
		executor.prepareConnection(conn, 10);
		// Check connection properties
		// See https://tools.ietf.org/html/rfc1945 section 11.1 for example
		// we are comparing against
		assertThat(conn.getRequestProperty("Authorization")).isEqualTo("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
	}

	@Test
	public void testNullContextHolderIsNull() throws Exception {
		SecurityContextHolder.getContext().setAuthentication(null);
		// Create a connection and ensure our executor sets its
		// properties correctly
		AuthenticationSimpleHttpInvokerRequestExecutor executor = new AuthenticationSimpleHttpInvokerRequestExecutor();
		HttpURLConnection conn = new MockHttpURLConnection(new URL("https://localhost/"));
		executor.prepareConnection(conn, 10);
		// Check connection properties (shouldn't be an Authorization header)
		assertThat(conn.getRequestProperty("Authorization")).isNull();
	}

	// SEC-1975
	@Test
	public void testNullContextHolderWhenAnonymous() throws Exception {
		AnonymousAuthenticationToken anonymous = new AnonymousAuthenticationToken("key", "principal",
				AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
		SecurityContextHolder.getContext().setAuthentication(anonymous);
		// Create a connection and ensure our executor sets its
		// properties correctly
		AuthenticationSimpleHttpInvokerRequestExecutor executor = new AuthenticationSimpleHttpInvokerRequestExecutor();
		HttpURLConnection conn = new MockHttpURLConnection(new URL("https://localhost/"));
		executor.prepareConnection(conn, 10);
		// Check connection properties (shouldn't be an Authorization header)
		assertThat(conn.getRequestProperty("Authorization")).isNull();
	}

	private class MockHttpURLConnection extends HttpURLConnection {

		private Map<String, String> requestProperties = new HashMap<>();

		MockHttpURLConnection(URL u) {
			super(u);
		}

		@Override
		public void connect() {
			throw new UnsupportedOperationException("mock not implemented");
		}

		@Override
		public void disconnect() {
			throw new UnsupportedOperationException("mock not implemented");
		}

		@Override
		public String getRequestProperty(String key) {
			return this.requestProperties.get(key);
		}

		@Override
		public void setRequestProperty(String key, String value) {
			this.requestProperties.put(key, value);
		}

		@Override
		public boolean usingProxy() {
			throw new UnsupportedOperationException("mock not implemented");
		}

	}

}

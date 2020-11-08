package org.springframework.security.web;

import javax.servlet.ServletRequest;

import org.springframework.util.Assert;

/**
 * Concrete implementation of {@link PortResolver} that obtains the port from
 * <tt>ServletRequest.getServerPort()</tt>.
 * <p>
 * This class is capable of handling the IE bug which results in an incorrect URL being
 * presented in the header subsequent to a redirect to a different scheme and port where
 * the port is not a well-known number (ie 80 or 443). Handling involves detecting an
 * incorrect response from <code>ServletRequest.getServerPort()</code> for the scheme (eg
 * a HTTP request on 8443) and then determining the real server port (eg HTTP request is
 * really on 8080). The map of valid ports is obtained from the configured
 * {@link PortMapper}.
 *
 * @author Ben Alex
 */
public class PortResolverImpl implements PortResolver {

	private PortMapper portMapper = new PortMapperImpl();

	public PortMapper getPortMapper() {
		return this.portMapper;
	}

	@Override
	public int getServerPort(ServletRequest request) {
		int serverPort = request.getServerPort();
		String scheme = request.getScheme().toLowerCase();
		Integer mappedPort = getMappedPort(serverPort, scheme);
		return (mappedPort != null) ? mappedPort : serverPort;
	}

	private Integer getMappedPort(int serverPort, String scheme) {
		if ("http".equals(scheme)) {
			return this.portMapper.lookupHttpPort(serverPort);
		}
		if ("https".equals(scheme)) {
			return this.portMapper.lookupHttpsPort(serverPort);
		}
		return null;
	}

	public void setPortMapper(PortMapper portMapper) {
		Assert.notNull(portMapper, "portMapper cannot be null");
		this.portMapper = portMapper;
	}

}

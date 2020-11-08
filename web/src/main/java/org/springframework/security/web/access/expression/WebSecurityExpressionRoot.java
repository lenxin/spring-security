package org.springframework.security.web.access.expression;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

/**
 * @author Luke Taylor
 * @since 3.0
 */
public class WebSecurityExpressionRoot extends SecurityExpressionRoot {

	/**
	 * Allows direct access to the request object
	 */
	public final HttpServletRequest request;

	public WebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
		super(a);
		this.request = fi.getRequest();
	}

	/**
	 * Takes a specific IP address or a range using the IP/Netmask (e.g. 192.168.1.0/24 or
	 * 202.24.0.0/14).
	 * @param ipAddress the address or range of addresses from which the request must
	 * come.
	 * @return true if the IP address of the current request is in the required range.
	 */
	public boolean hasIpAddress(String ipAddress) {
		IpAddressMatcher matcher = new IpAddressMatcher(ipAddress);
		return matcher.matches(this.request);
	}

}

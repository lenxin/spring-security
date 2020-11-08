package org.springframework.security.web.access.channel;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.Assert;

/**
 * Ensures channel security is inactive by review of
 * <code>HttpServletRequest.isSecure()</code> responses.
 * <p>
 * The class responds to one case-sensitive keyword, {@link #getInsecureKeyword}. If this
 * keyword is detected, <code>HttpServletRequest.isSecure()</code> is used to determine
 * the channel security offered. If channel security is present, the configured
 * <code>ChannelEntryPoint</code> is called. By default the entry point is
 * {@link RetryWithHttpEntryPoint}.
 * <p>
 * The default <code>insecureKeyword</code> is <code>REQUIRES_INSECURE_CHANNEL</code>.
 *
 * @author Ben Alex
 */
public class InsecureChannelProcessor implements InitializingBean, ChannelProcessor {

	private ChannelEntryPoint entryPoint = new RetryWithHttpEntryPoint();

	private String insecureKeyword = "REQUIRES_INSECURE_CHANNEL";

	@Override
	public void afterPropertiesSet() {
		Assert.hasLength(this.insecureKeyword, "insecureKeyword required");
		Assert.notNull(this.entryPoint, "entryPoint required");
	}

	@Override
	public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config)
			throws IOException, ServletException {
		Assert.isTrue(invocation != null && config != null, "Nulls cannot be provided");
		for (ConfigAttribute attribute : config) {
			if (supports(attribute)) {
				if (invocation.getHttpRequest().isSecure()) {
					this.entryPoint.commence(invocation.getRequest(), invocation.getResponse());
				}
			}
		}
	}

	public ChannelEntryPoint getEntryPoint() {
		return this.entryPoint;
	}

	public String getInsecureKeyword() {
		return this.insecureKeyword;
	}

	public void setEntryPoint(ChannelEntryPoint entryPoint) {
		this.entryPoint = entryPoint;
	}

	public void setInsecureKeyword(String secureKeyword) {
		this.insecureKeyword = secureKeyword;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return (attribute != null) && (attribute.getAttribute() != null)
				&& attribute.getAttribute().equals(getInsecureKeyword());
	}

}

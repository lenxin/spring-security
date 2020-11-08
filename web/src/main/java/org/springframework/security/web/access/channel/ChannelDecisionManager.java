package org.springframework.security.web.access.channel;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;

/**
 * Decides whether a web channel provides sufficient security.
 *
 * @author Ben Alex
 */
public interface ChannelDecisionManager {

	/**
	 * Decided whether the presented {@link FilterInvocation} provides the appropriate
	 * level of channel security based on the requested list of <tt>ConfigAttribute</tt>s.
	 *
	 */
	void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException;

	/**
	 * Indicates whether this <code>ChannelDecisionManager</code> is able to process the
	 * passed <code>ConfigAttribute</code>.
	 * <p>
	 * This allows the <code>ChannelProcessingFilter</code> to check every configuration
	 * attribute can be consumed by the configured <code>ChannelDecisionManager</code>.
	 * </p>
	 * @param attribute a configuration attribute that has been configured against the
	 * <code>ChannelProcessingFilter</code>
	 * @return true if this <code>ChannelDecisionManager</code> can support the passed
	 * configuration attribute
	 */
	boolean supports(ConfigAttribute attribute);

}

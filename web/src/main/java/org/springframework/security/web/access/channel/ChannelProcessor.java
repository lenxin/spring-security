package org.springframework.security.web.access.channel;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;

/**
 * Decides whether a web channel meets a specific security condition.
 * <p>
 * <code>ChannelProcessor</code> implementations are iterated by the
 * {@link ChannelDecisionManagerImpl}.
 * <p>
 * If an implementation has an issue with the channel security, they should take action
 * themselves. The callers of the implementation do not take any action.
 *
 * @author Ben Alex
 */
public interface ChannelProcessor {

	/**
	 * Decided whether the presented {@link FilterInvocation} provides the appropriate
	 * level of channel security based on the requested list of <tt>ConfigAttribute</tt>s.
	 */
	void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException;

	/**
	 * Indicates whether this <code>ChannelProcessor</code> is able to process the passed
	 * <code>ConfigAttribute</code>.
	 * <p>
	 * This allows the <code>ChannelProcessingFilter</code> to check every configuration
	 * attribute can be consumed by the configured <code>ChannelDecisionManager</code>.
	 * @param attribute a configuration attribute that has been configured against the
	 * <tt>ChannelProcessingFilter</tt>.
	 * @return true if this <code>ChannelProcessor</code> can support the passed
	 * configuration attribute
	 */
	boolean supports(ConfigAttribute attribute);

}

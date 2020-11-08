package org.springframework.security.web.access.channel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * May be used by a {@link ChannelProcessor} to launch a web channel.
 *
 * <p>
 * <code>ChannelProcessor</code>s can elect to launch a new web channel directly, or they
 * can delegate to another class. The <code>ChannelEntryPoint</code> is a pluggable
 * interface to assist <code>ChannelProcessor</code>s in performing this delegation.
 *
 * @author Ben Alex
 */
public interface ChannelEntryPoint {

	/**
	 * Commences a secure channel.
	 * <p>
	 * Implementations should modify the headers on the <code>ServletResponse</code> as
	 * necessary to commence the user agent using the implementation's supported channel
	 * type.
	 * @param request that a <code>ChannelProcessor</code> has rejected
	 * @param response so that the user agent can begin using a new channel
	 *
	 */
	void commence(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

}

package org.springframework.security.messaging.context;

import java.util.Stack;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.ExecutorChannelInterceptor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * <p>
 * Creates a {@link ExecutorChannelInterceptor} that will obtain the
 * {@link Authentication} from the specified {@link Message#getHeaders()}.
 * </p>
 *
 * @author Rob Winch
 * @since 4.0
 */
public final class SecurityContextChannelInterceptor extends ChannelInterceptorAdapter
		implements ExecutorChannelInterceptor {

	private static final SecurityContext EMPTY_CONTEXT = SecurityContextHolder.createEmptyContext();

	private static final ThreadLocal<Stack<SecurityContext>> originalContext = new ThreadLocal<>();

	private final String authenticationHeaderName;

	private Authentication anonymous = new AnonymousAuthenticationToken("key", "anonymous",
			AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

	/**
	 * Creates a new instance using the header of the name
	 * {@link SimpMessageHeaderAccessor#USER_HEADER}.
	 */
	public SecurityContextChannelInterceptor() {
		this(SimpMessageHeaderAccessor.USER_HEADER);
	}

	/**
	 * Creates a new instance that uses the specified header to obtain the
	 * {@link Authentication}.
	 * @param authenticationHeaderName the header name to obtain the
	 * {@link Authentication}. Cannot be null.
	 */
	public SecurityContextChannelInterceptor(String authenticationHeaderName) {
		Assert.notNull(authenticationHeaderName, "authenticationHeaderName cannot be null");
		this.authenticationHeaderName = authenticationHeaderName;
	}

	/**
	 * Allows setting the Authentication used for anonymous authentication. Default is:
	 *
	 * <pre>
	 * new AnonymousAuthenticationToken(&quot;key&quot;, &quot;anonymous&quot;,
	 * 		AuthorityUtils.createAuthorityList(&quot;ROLE_ANONYMOUS&quot;));
	 * </pre>
	 * @param authentication the Authentication used for anonymous authentication. Cannot
	 * be null.
	 */
	public void setAnonymousAuthentication(Authentication authentication) {
		Assert.notNull(authentication, "authentication cannot be null");
		this.anonymous = authentication;
	}

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		setup(message);
		return message;
	}

	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
		cleanup();
	}

	@Override
	public Message<?> beforeHandle(Message<?> message, MessageChannel channel, MessageHandler handler) {
		setup(message);
		return message;
	}

	@Override
	public void afterMessageHandled(Message<?> message, MessageChannel channel, MessageHandler handler, Exception ex) {
		cleanup();
	}

	private void setup(Message<?> message) {
		SecurityContext currentContext = SecurityContextHolder.getContext();
		Stack<SecurityContext> contextStack = originalContext.get();
		if (contextStack == null) {
			contextStack = new Stack<>();
			originalContext.set(contextStack);
		}
		contextStack.push(currentContext);
		Object user = message.getHeaders().get(this.authenticationHeaderName);
		Authentication authentication = getAuthentication(user);
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
	}

	private Authentication getAuthentication(Object user) {
		if ((user instanceof Authentication)) {
			return (Authentication) user;
		}
		return this.anonymous;
	}

	private void cleanup() {
		Stack<SecurityContext> contextStack = originalContext.get();
		if (contextStack == null || contextStack.isEmpty()) {
			SecurityContextHolder.clearContext();
			originalContext.remove();
			return;
		}
		SecurityContext context = contextStack.pop();
		try {
			if (SecurityContextChannelInterceptor.EMPTY_CONTEXT.equals(context)) {
				SecurityContextHolder.clearContext();
				originalContext.remove();
			}
			else {
				SecurityContextHolder.setContext(context);
			}
		}
		catch (Throwable ex) {
			SecurityContextHolder.clearContext();
		}
	}

}

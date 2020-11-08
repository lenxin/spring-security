package org.springframework.security.messaging.web.csrf;

import java.util.Map;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.security.messaging.util.matcher.MessageMatcher;
import org.springframework.security.messaging.util.matcher.SimpMessageTypeMatcher;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

/**
 * {@link ChannelInterceptorAdapter} that validates that a valid CSRF is included in the
 * header of any {@link SimpMessageType#CONNECT} message. The expected {@link CsrfToken}
 * is populated by CsrfTokenHandshakeInterceptor.
 *
 * @author Rob Winch
 * @since 4.0
 */
public final class CsrfChannelInterceptor extends ChannelInterceptorAdapter {

	private final MessageMatcher<Object> matcher = new SimpMessageTypeMatcher(SimpMessageType.CONNECT);

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		if (!this.matcher.matches(message)) {
			return message;
		}
		Map<String, Object> sessionAttributes = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
		CsrfToken expectedToken = (sessionAttributes != null)
				? (CsrfToken) sessionAttributes.get(CsrfToken.class.getName()) : null;
		if (expectedToken == null) {
			throw new MissingCsrfTokenException(null);
		}
		String actualTokenValue = SimpMessageHeaderAccessor.wrap(message)
				.getFirstNativeHeader(expectedToken.getHeaderName());
		boolean csrfCheckPassed = expectedToken.getToken().equals(actualTokenValue);
		if (!csrfCheckPassed) {
			throw new InvalidCsrfTokenException(expectedToken, actualTokenValue);
		}
		return message;
	}

}

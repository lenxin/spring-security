package org.springframework.security.messaging.access.expression;

import org.springframework.messaging.Message;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;

/**
 * The {@link SecurityExpressionRoot} used for {@link Message} expressions.
 *
 * @author Rob Winch
 * @since 4.0
 */
public class MessageSecurityExpressionRoot extends SecurityExpressionRoot {

	public final Message<?> message;

	public MessageSecurityExpressionRoot(Authentication authentication, Message<?> message) {
		super(authentication);
		this.message = message;
	}

}

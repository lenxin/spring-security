package org.springframework.security.messaging.access.intercept;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.messaging.Message;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.messaging.access.expression.ExpressionBasedMessageSecurityMetadataSourceFactory;
import org.springframework.security.messaging.util.matcher.MessageMatcher;

/**
 * A default implementation of {@link MessageSecurityMetadataSource} that looks up the
 * {@link ConfigAttribute} instances using a {@link MessageMatcher}.
 *
 * <p>
 * Each entry is considered in order. The first entry that matches, the corresponding
 * {@code Collection<ConfigAttribute>} is returned.
 * </p>
 *
 * @author Rob Winch
 * @since 4.0
 * @see ChannelSecurityInterceptor
 * @see ExpressionBasedMessageSecurityMetadataSourceFactory
 */
public final class DefaultMessageSecurityMetadataSource implements MessageSecurityMetadataSource {

	private final Map<MessageMatcher<?>, Collection<ConfigAttribute>> messageMap;

	public DefaultMessageSecurityMetadataSource(
			LinkedHashMap<MessageMatcher<?>, Collection<ConfigAttribute>> messageMap) {
		this.messageMap = messageMap;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		final Message message = (Message) object;
		for (Map.Entry<MessageMatcher<?>, Collection<ConfigAttribute>> entry : this.messageMap.entrySet()) {
			if (entry.getKey().matches(message)) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<>();
		for (Collection<ConfigAttribute> entry : this.messageMap.values()) {
			allAttributes.addAll(entry);
		}
		return allAttributes;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Message.class.isAssignableFrom(clazz);
	}

}

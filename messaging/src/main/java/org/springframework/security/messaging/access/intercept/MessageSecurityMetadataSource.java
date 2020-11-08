package org.springframework.security.messaging.access.intercept;

import org.springframework.messaging.Message;
import org.springframework.security.access.SecurityMetadataSource;

/**
 * A {@link SecurityMetadataSource} that is used for securing {@link Message}
 *
 * @author Rob Winch
 * @since 4.0
 * @see ChannelSecurityInterceptor
 * @see DefaultMessageSecurityMetadataSource
 */
public interface MessageSecurityMetadataSource extends SecurityMetadataSource {

}

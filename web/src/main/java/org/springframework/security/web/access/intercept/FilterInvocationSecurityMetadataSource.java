package org.springframework.security.web.access.intercept;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;

/**
 * Marker interface for <code>SecurityMetadataSource</code> implementations that are
 * designed to perform lookups keyed on {@link FilterInvocation}s.
 *
 * @author Ben Alex
 */
public interface FilterInvocationSecurityMetadataSource extends SecurityMetadataSource {

}

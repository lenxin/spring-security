package org.springframework.security.access.method;

import java.lang.reflect.Method;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;

/**
 * Interface for <code>SecurityMetadataSource</code> implementations that are designed to
 * perform lookups keyed on <code>Method</code>s.
 *
 * @author Ben Alex
 */
public interface MethodSecurityMetadataSource extends SecurityMetadataSource {

	Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass);

}

package org.springframework.security.config.annotation.web.configuration;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;

/**
 * Used by {@link EnableWebSecurity} to conditionally import
 * {@link WebMvcSecurityConfiguration} when the DispatcherServlet is present on the
 * classpath.
 *
 * @author Rob Winch
 * @since 3.2
 */
class SpringWebMvcImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		if (!ClassUtils.isPresent("org.springframework.web.servlet.DispatcherServlet", getClass().getClassLoader())) {
			return new String[0];
		}
		return new String[] {
				"org.springframework.security.config.annotation.web.configuration.WebMvcSecurityConfiguration" };
	}

}

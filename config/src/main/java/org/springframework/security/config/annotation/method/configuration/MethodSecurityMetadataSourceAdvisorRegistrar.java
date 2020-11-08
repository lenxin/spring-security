package org.springframework.security.config.annotation.method.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityMetadataSourceAdvisor;
import org.springframework.util.MultiValueMap;

/**
 * Creates Spring Security's MethodSecurityMetadataSourceAdvisor only when using proxy
 * based method security (i.e. do not do it when using ASPECTJ). The conditional logic is
 * controlled through {@link GlobalMethodSecuritySelector}.
 *
 * @author Rob Winch
 * @since 4.0.2
 * @see GlobalMethodSecuritySelector
 */
class MethodSecurityMetadataSourceAdvisorRegistrar implements ImportBeanDefinitionRegistrar {

	/**
	 * Register, escalate, and configure the AspectJ auto proxy creator based on the value
	 * of the @{@link EnableGlobalMethodSecurity#proxyTargetClass()} attribute on the
	 * importing {@code @Configuration} class.
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		BeanDefinitionBuilder advisor = BeanDefinitionBuilder
				.rootBeanDefinition(MethodSecurityMetadataSourceAdvisor.class);
		advisor.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		advisor.addConstructorArgValue("methodSecurityInterceptor");
		advisor.addConstructorArgReference("methodSecurityMetadataSource");
		advisor.addConstructorArgValue("methodSecurityMetadataSource");
		MultiValueMap<String, Object> attributes = importingClassMetadata
				.getAllAnnotationAttributes(EnableGlobalMethodSecurity.class.getName());
		Integer order = (Integer) attributes.getFirst("order");
		if (order != null) {
			advisor.addPropertyValue("order", order);
		}
		registry.registerBeanDefinition("metaDataSourceAdvisor", advisor.getBeanDefinition());
	}

}

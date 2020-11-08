package org.springframework.security.config.annotation.method.configuration;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Jsr250MethodSecurityMetadataSource;

@Configuration(proxyBeanMethods = false)
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
class Jsr250MetadataSourceConfiguration {

	@Bean
	@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
	Jsr250MethodSecurityMetadataSource jsr250MethodSecurityMetadataSource() {
		return new Jsr250MethodSecurityMetadataSource();
	}

}

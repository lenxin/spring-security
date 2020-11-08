package org.springframework.security.config.http;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.method.annotation.OAuth2AuthorizedClientArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * @author Joe Grandja
 * @since 5.4
 */
final class OAuth2ClientWebMvcSecurityPostProcessor implements BeanDefinitionRegistryPostProcessor, BeanFactoryAware {

	private static final String CUSTOM_ARGUMENT_RESOLVERS_PROPERTY = "customArgumentResolvers";

	private BeanFactory beanFactory;

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		String[] clientRegistrationRepositoryBeanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
				(ListableBeanFactory) this.beanFactory, ClientRegistrationRepository.class, false, false);
		String[] authorizedClientRepositoryBeanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
				(ListableBeanFactory) this.beanFactory, OAuth2AuthorizedClientRepository.class, false, false);
		if (clientRegistrationRepositoryBeanNames.length != 1 || authorizedClientRepositoryBeanNames.length != 1) {
			return;
		}
		for (String beanName : registry.getBeanDefinitionNames()) {
			BeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
			if (RequestMappingHandlerAdapter.class.getName().equals(beanDefinition.getBeanClassName())) {
				PropertyValue currentArgumentResolvers = beanDefinition.getPropertyValues()
						.getPropertyValue(CUSTOM_ARGUMENT_RESOLVERS_PROPERTY);
				ManagedList<Object> argumentResolvers = new ManagedList<>();
				if (currentArgumentResolvers != null) {
					argumentResolvers.addAll((ManagedList<?>) currentArgumentResolvers.getValue());
				}
				String[] authorizedClientManagerBeanNames = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
						(ListableBeanFactory) this.beanFactory, OAuth2AuthorizedClientManager.class, false, false);
				BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
						.genericBeanDefinition(OAuth2AuthorizedClientArgumentResolver.class);
				if (authorizedClientManagerBeanNames.length == 1) {
					beanDefinitionBuilder.addConstructorArgReference(authorizedClientManagerBeanNames[0]);
				}
				else {
					beanDefinitionBuilder.addConstructorArgReference(clientRegistrationRepositoryBeanNames[0]);
					beanDefinitionBuilder.addConstructorArgReference(authorizedClientRepositoryBeanNames[0]);
				}
				argumentResolvers.add(beanDefinitionBuilder.getBeanDefinition());
				beanDefinition.getPropertyValues().add(CUSTOM_ARGUMENT_RESOLVERS_PROPERTY, argumentResolvers);
				break;
			}
		}
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}

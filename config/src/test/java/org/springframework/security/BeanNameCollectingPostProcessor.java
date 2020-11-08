package org.springframework.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * @author Luke Taylor
 */
public class BeanNameCollectingPostProcessor implements BeanPostProcessor {

	Set<String> beforeInitPostProcessedBeans = new HashSet<>();

	Set<String> afterInitPostProcessedBeans = new HashSet<>();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName != null) {
			this.beforeInitPostProcessedBeans.add(beanName);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (beanName != null) {
			this.afterInitPostProcessedBeans.add(beanName);
		}
		return bean;
	}

	public Set<String> getBeforeInitPostProcessedBeans() {
		return this.beforeInitPostProcessedBeans;
	}

	public Set<String> getAfterInitPostProcessedBeans() {
		return this.afterInitPostProcessedBeans;
	}

}

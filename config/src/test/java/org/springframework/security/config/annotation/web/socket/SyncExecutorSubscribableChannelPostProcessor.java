package org.springframework.security.config.annotation.web.socket;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.messaging.support.ExecutorSubscribableChannel;

/**
 * @author Rob Winch
 */
public class SyncExecutorSubscribableChannelPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof ExecutorSubscribableChannel) {
			ExecutorSubscribableChannel original = (ExecutorSubscribableChannel) bean;
			ExecutorSubscribableChannel channel = new ExecutorSubscribableChannel();
			channel.setInterceptors(original.getInterceptors());
			return channel;
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

}

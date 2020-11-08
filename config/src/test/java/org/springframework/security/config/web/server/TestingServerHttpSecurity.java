package org.springframework.security.config.web.server;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * @author Rob Winch
 * @since 5.1
 */
public class TestingServerHttpSecurity extends ServerHttpSecurity {

	public TestingServerHttpSecurity applicationContext(ApplicationContext applicationContext) throws BeansException {
		super.setApplicationContext(applicationContext);
		return this;
	}

}

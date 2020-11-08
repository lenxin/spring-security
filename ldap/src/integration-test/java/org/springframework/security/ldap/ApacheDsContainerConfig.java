package org.springframework.security.ldap;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.security.ldap.server.ApacheDSContainer;

/**
 * @author Eddú Meléndez
 */
@Configuration
public class ApacheDsContainerConfig {

	private ApacheDSContainer container;

	@Bean
	ApacheDSContainer ldapContainer() throws Exception {
		this.container = new ApacheDSContainer("dc=springframework,dc=org", "classpath:test-server.ldif");
		this.container.setPort(0);
		return this.container;
	}

	@Bean
	ContextSource contextSource(ApacheDSContainer ldapContainer) throws Exception {
		return new DefaultSpringSecurityContextSource(
				"ldap://127.0.0.1:" + ldapContainer.getLocalPort() + "/dc=springframework,dc=org");
	}

	@PreDestroy
	void shutdown() {
		this.container.stop();
	}

}

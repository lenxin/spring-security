package org.springframework.security;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.config.BeanIds;
import org.springframework.security.ldap.server.UnboundIdContainer;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Eddú Meléndez
 */
public class LdapServerBeanDefinitionParserTests {

	private ClassPathXmlApplicationContext context;

	@Before
	public void setup() {
		this.context = new ClassPathXmlApplicationContext("applicationContext-security.xml");
	}

	@After
	public void closeAppContext() {
		if (this.context != null) {
			this.context.close();
			this.context = null;
		}
	}

	@Test
	public void apacheDirectoryServerIsStartedByDefault() {
		String[] beanNames = this.context.getBeanNamesForType(UnboundIdContainer.class);
		assertThat(beanNames).hasSize(1);
		assertThat(beanNames[0]).isEqualTo(BeanIds.EMBEDDED_UNBOUNDID);
	}

}

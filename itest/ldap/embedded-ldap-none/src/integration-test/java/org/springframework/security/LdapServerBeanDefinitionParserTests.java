package org.springframework.security;

import org.junit.After;
import org.junit.Test;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Eddú Meléndez
 */
public class LdapServerBeanDefinitionParserTests {

	private ClassPathXmlApplicationContext context;

	@After
	public void closeAppContext() {
		if (this.context != null) {
			this.context.close();
			this.context = null;
		}
	}

	@Test
	public void apacheDirectoryServerIsStartedByDefault() {
		assertThatExceptionOfType(BeanDefinitionStoreException.class)
				.isThrownBy(() -> this.context = new ClassPathXmlApplicationContext("applicationContext-security.xml"))
				.withMessageContaining("Embedded LDAP server is not provided");
	}

}

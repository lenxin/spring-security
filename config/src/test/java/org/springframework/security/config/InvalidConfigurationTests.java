package org.springframework.security.config;

import org.junit.After;
import org.junit.Test;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionStoreException;
import org.springframework.security.config.authentication.AuthenticationManagerFactoryBean;
import org.springframework.security.config.util.InMemoryXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests which make sure invalid configurations are rejected by the namespace. In
 * particular invalid top-level elements. These are likely to fail after the namespace has
 * been updated using trang, but the spring-security.xsl transform has not been applied.
 *
 * @author Luke Taylor
 */
public class InvalidConfigurationTests {

	private InMemoryXmlApplicationContext appContext;

	@After
	public void closeAppContext() {
		if (this.appContext != null) {
			this.appContext.close();
		}
	}

	// Parser should throw a SAXParseException
	@Test
	public void passwordEncoderCannotAppearAtTopLevel() {
		assertThatExceptionOfType(XmlBeanDefinitionStoreException.class)
				.isThrownBy(() -> setContext("<password-encoder hash='md5'/>"));
	}

	@Test
	public void authenticationProviderCannotAppearAtTopLevel() {
		assertThatExceptionOfType(XmlBeanDefinitionStoreException.class)
				.isThrownBy(() -> setContext("<authentication-provider ref='blah'/>"));
	}

	@Test
	public void missingAuthenticationManagerGivesSensibleErrorMessage() {
		assertThatExceptionOfType(BeanCreationException.class)
				.isThrownBy(() -> setContext("<http auto-config='true' />")).satisfies((ex) -> {
					Throwable cause = ultimateCause(ex);
					assertThat(cause).isInstanceOf(NoSuchBeanDefinitionException.class);
					NoSuchBeanDefinitionException nsbe = (NoSuchBeanDefinitionException) cause;
					assertThat(nsbe.getBeanName()).isEqualTo(BeanIds.AUTHENTICATION_MANAGER);
					assertThat(nsbe.getMessage()).endsWith(AuthenticationManagerFactoryBean.MISSING_BEAN_ERROR_MESSAGE);
				});
	}

	private Throwable ultimateCause(Throwable ex) {
		if (ex.getCause() == null) {
			return ex;
		}
		return ultimateCause(ex.getCause());
	}

	private void setContext(String context) {
		this.appContext = new InMemoryXmlApplicationContext(context);
	}

}

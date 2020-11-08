package org.springframework.security.saml2.core;

import org.junit.Test;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.core.xml.config.XMLObjectProviderRegistry;

import org.springframework.security.saml2.Saml2Exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Tests for {@link OpenSamlInitializationService}
 *
 * @author Josh Cummings
 */
public class OpenSamlInitializationServiceTests {

	@Test
	public void initializeWhenInvokedMultipleTimesThenInitializesOnce() {
		OpenSamlInitializationService.initialize();
		XMLObjectProviderRegistry registry = ConfigurationService.get(XMLObjectProviderRegistry.class);
		assertThat(registry.getParserPool()).isNotNull();
		assertThatExceptionOfType(Saml2Exception.class)
				.isThrownBy(() -> OpenSamlInitializationService.requireInitialize((r) -> {
				})).withMessageContaining("OpenSAML was already initialized previously");
	}

}

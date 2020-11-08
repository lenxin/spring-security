package org.springframework.security.cas.web;

import org.junit.Test;

import org.springframework.security.cas.SamlServiceProperties;
import org.springframework.security.cas.ServiceProperties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link ServiceProperties}.
 *
 * @author Ben Alex
 */
public class ServicePropertiesTests {

	@Test
	public void detectsMissingService() throws Exception {
		ServiceProperties sp = new ServiceProperties();
		assertThatIllegalArgumentException().isThrownBy(sp::afterPropertiesSet);
	}

	@Test
	public void nullServiceWhenAuthenticateAllTokens() throws Exception {
		ServiceProperties sp = new ServiceProperties();
		sp.setAuthenticateAllArtifacts(true);
		assertThatIllegalArgumentException().isThrownBy(sp::afterPropertiesSet);
		sp.setAuthenticateAllArtifacts(false);
		assertThatIllegalArgumentException().isThrownBy(sp::afterPropertiesSet);
	}

	@Test
	public void testGettersSetters() throws Exception {
		ServiceProperties[] sps = { new ServiceProperties(), new SamlServiceProperties() };
		for (ServiceProperties sp : sps) {
			sp.setSendRenew(false);
			assertThat(sp.isSendRenew()).isFalse();
			sp.setSendRenew(true);
			assertThat(sp.isSendRenew()).isTrue();
			sp.setArtifactParameter("notticket");
			assertThat(sp.getArtifactParameter()).isEqualTo("notticket");
			sp.setServiceParameter("notservice");
			assertThat(sp.getServiceParameter()).isEqualTo("notservice");
			sp.setService("https://mycompany.com/service");
			assertThat(sp.getService()).isEqualTo("https://mycompany.com/service");
			sp.afterPropertiesSet();
		}
	}

}

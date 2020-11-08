package org.springframework.security.saml2.provider.service.web;

import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.TestRelyingPartyRegistrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests for {@link DefaultRelyingPartyRegistrationResolver}
 */
public class DefaultRelyingPartyRegistrationResolverTests {

	private final RelyingPartyRegistration registration = TestRelyingPartyRegistrations.relyingPartyRegistration()
			.build();

	private final RelyingPartyRegistrationRepository repository = new InMemoryRelyingPartyRegistrationRepository(
			this.registration);

	private final DefaultRelyingPartyRegistrationResolver resolver = new DefaultRelyingPartyRegistrationResolver(
			this.repository);

	@Test
	public void resolveWhenRequestContainsRegistrationIdThenResolves() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setPathInfo("/some/path/" + this.registration.getRegistrationId());
		RelyingPartyRegistration registration = this.resolver.convert(request);
		assertThat(registration).isNotNull();
		assertThat(registration.getRegistrationId()).isEqualTo(this.registration.getRegistrationId());
		assertThat(registration.getEntityId())
				.isEqualTo("http://localhost/saml2/service-provider-metadata/" + this.registration.getRegistrationId());
		assertThat(registration.getAssertionConsumerServiceLocation())
				.isEqualTo("http://localhost/login/saml2/sso/" + this.registration.getRegistrationId());
	}

	@Test
	public void resolveWhenRequestContainsInvalidRegistrationIdThenNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setPathInfo("/some/path/not-" + this.registration.getRegistrationId());
		RelyingPartyRegistration registration = this.resolver.convert(request);
		assertThat(registration).isNull();
	}

	@Test
	public void resolveWhenRequestIsMissingRegistrationIdThenNull() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RelyingPartyRegistration registration = this.resolver.convert(request);
		assertThat(registration).isNull();
	}

	@Test
	public void constructorWhenNullRelyingPartyRegistrationThenIllegalArgument() {
		assertThatIllegalArgumentException().isThrownBy(() -> new DefaultRelyingPartyRegistrationResolver(null));
	}

}

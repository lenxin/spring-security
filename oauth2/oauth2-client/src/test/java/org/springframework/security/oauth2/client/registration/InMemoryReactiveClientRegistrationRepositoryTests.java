package org.springframework.security.oauth2.client.registration;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

/**
 * @author Rob Winch
 * @since 5.1
 */
public class InMemoryReactiveClientRegistrationRepositoryTests {

	private ClientRegistration registration = TestClientRegistrations.clientRegistration().build();

	private InMemoryReactiveClientRegistrationRepository repository;

	@Before
	public void setup() {
		this.repository = new InMemoryReactiveClientRegistrationRepository(this.registration);
	}

	@Test
	public void constructorWhenZeroVarArgsThenIllegalArgumentException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new InMemoryReactiveClientRegistrationRepository());
	}

	@Test
	public void constructorWhenClientRegistrationArrayThenIllegalArgumentException() {
		ClientRegistration[] registrations = null;
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new InMemoryReactiveClientRegistrationRepository(registrations));
	}

	@Test
	public void constructorWhenClientRegistrationListThenIllegalArgumentException() {
		List<ClientRegistration> registrations = null;
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new InMemoryReactiveClientRegistrationRepository(registrations));
	}

	@Test
	public void constructorListClientRegistrationWhenDuplicateIdThenIllegalArgumentException() {
		List<ClientRegistration> registrations = Arrays.asList(this.registration, this.registration);
		assertThatIllegalStateException()
				.isThrownBy(() -> new InMemoryReactiveClientRegistrationRepository(registrations));
	}

	@Test
	public void constructorWhenClientRegistrationIsNullThenIllegalArgumentException() {
		ClientRegistration registration = null;
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new InMemoryReactiveClientRegistrationRepository(registration));
	}

	@Test
	public void findByRegistrationIdWhenValidIdThenFound() {
		// @formatter:off
		StepVerifier.create(this.repository.findByRegistrationId(this.registration.getRegistrationId()))
				.expectNext(this.registration)
				.verifyComplete();
		// @formatter:on
	}

	@Test
	public void findByRegistrationIdWhenNotValidIdThenEmpty() {
		StepVerifier.create(this.repository.findByRegistrationId(this.registration.getRegistrationId() + "invalid"))
				.verifyComplete();
	}

	@Test
	public void iteratorWhenContainsGithubThenContains() {
		assertThat(this.repository).containsOnly(this.registration);
	}

}

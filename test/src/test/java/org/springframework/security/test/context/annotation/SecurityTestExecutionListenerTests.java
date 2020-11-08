package org.springframework.security.test.context.annotation;

import java.security.Principal;

import org.junit.Test;
import org.junit.runner.RunWith;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SecurityTestExecutionListeners
public class SecurityTestExecutionListenerTests {

	@WithMockUser
	@Test
	public void withSecurityContextTestExecutionListenerIsRegistered() {
		assertThat(SecurityContextHolder.getContext().getAuthentication().getName()).isEqualTo("user");
	}

	@WithMockUser
	@Test
	public void reactorContextTestSecurityContextHolderExecutionListenerTestIsRegistered() {
		Mono<String> name = ReactiveSecurityContextHolder.getContext().map(SecurityContext::getAuthentication)
				.map(Principal::getName);
		StepVerifier.create(name).expectNext("user").verifyComplete();
	}

}

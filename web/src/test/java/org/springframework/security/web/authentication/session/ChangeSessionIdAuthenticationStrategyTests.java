package org.springframework.security.web.authentication.session;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 *
 */
@RunWith(PowerMockRunner.class)
public class ChangeSessionIdAuthenticationStrategyTests {

	@Test
	public void applySessionFixation() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		String id = request.getSession().getId();
		new ChangeSessionIdAuthenticationStrategy().applySessionFixation(request);
		assertThat(request.getSession().getId()).isNotEqualTo(id);
	}

}

package sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.test.StepVerifier;

/**
 * @author Rob Winch
 * @since 5.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldMessageServiceTests {
	@Autowired
	HelloWorldMessageService messages;

	@Test
	public void messagesWhenNotAuthenticatedThenDenied() {
		StepVerifier.create(this.messages.findMessage())
			.expectError(AccessDeniedException.class)
			.verify();
	}

	@Test
	@WithMockUser
	public void messagesWhenUserThenDenied() {
		StepVerifier.create(this.messages.findMessage())
			.expectError(AccessDeniedException.class)
			.verify();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	public void messagesWhenAdminThenOk() {
		StepVerifier.create(this.messages.findMessage())
			.expectNext("Hello World!")
			.verifyComplete();
	}
}

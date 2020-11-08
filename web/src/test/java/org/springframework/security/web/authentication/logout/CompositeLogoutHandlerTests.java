package org.springframework.security.web.authentication.logout;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.mockito.InOrder;

import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Eddú Meléndez
 * @author Rob Winch
 * @since 4.2.0
 */
public class CompositeLogoutHandlerTests {

	@Test
	public void buildEmptyCompositeLogoutHandlerThrowsException() {
		assertThatIllegalArgumentException().isThrownBy(() -> new CompositeLogoutHandler())
				.withMessage("LogoutHandlers are required");
	}

	@Test
	public void callLogoutHandlersSuccessfullyWithArray() {
		LogoutHandler securityContextLogoutHandler = mock(SecurityContextLogoutHandler.class);
		LogoutHandler csrfLogoutHandler = mock(SecurityContextLogoutHandler.class);
		LogoutHandler handler = new CompositeLogoutHandler(securityContextLogoutHandler, csrfLogoutHandler);
		handler.logout(mock(HttpServletRequest.class), mock(HttpServletResponse.class), mock(Authentication.class));
		verify(securityContextLogoutHandler, times(1)).logout(any(HttpServletRequest.class),
				any(HttpServletResponse.class), any(Authentication.class));
		verify(csrfLogoutHandler, times(1)).logout(any(HttpServletRequest.class), any(HttpServletResponse.class),
				any(Authentication.class));
	}

	@Test
	public void callLogoutHandlersSuccessfully() {
		LogoutHandler securityContextLogoutHandler = mock(SecurityContextLogoutHandler.class);
		LogoutHandler csrfLogoutHandler = mock(SecurityContextLogoutHandler.class);
		List<LogoutHandler> logoutHandlers = Arrays.asList(securityContextLogoutHandler, csrfLogoutHandler);
		LogoutHandler handler = new CompositeLogoutHandler(logoutHandlers);
		handler.logout(mock(HttpServletRequest.class), mock(HttpServletResponse.class), mock(Authentication.class));
		verify(securityContextLogoutHandler, times(1)).logout(any(HttpServletRequest.class),
				any(HttpServletResponse.class), any(Authentication.class));
		verify(csrfLogoutHandler, times(1)).logout(any(HttpServletRequest.class), any(HttpServletResponse.class),
				any(Authentication.class));
	}

	@Test
	public void callLogoutHandlersThrowException() {
		LogoutHandler firstLogoutHandler = mock(LogoutHandler.class);
		LogoutHandler secondLogoutHandler = mock(LogoutHandler.class);
		willThrow(new IllegalArgumentException()).given(firstLogoutHandler).logout(any(HttpServletRequest.class),
				any(HttpServletResponse.class), any(Authentication.class));
		List<LogoutHandler> logoutHandlers = Arrays.asList(firstLogoutHandler, secondLogoutHandler);
		LogoutHandler handler = new CompositeLogoutHandler(logoutHandlers);
		assertThatIllegalArgumentException().isThrownBy(() -> handler.logout(mock(HttpServletRequest.class),
				mock(HttpServletResponse.class), mock(Authentication.class)));
		InOrder logoutHandlersInOrder = inOrder(firstLogoutHandler, secondLogoutHandler);
		logoutHandlersInOrder.verify(firstLogoutHandler, times(1)).logout(any(HttpServletRequest.class),
				any(HttpServletResponse.class), any(Authentication.class));
		logoutHandlersInOrder.verify(secondLogoutHandler, never()).logout(any(HttpServletRequest.class),
				any(HttpServletResponse.class), any(Authentication.class));
	}

}

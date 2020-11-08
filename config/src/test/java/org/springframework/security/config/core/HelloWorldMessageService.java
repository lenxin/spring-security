package org.springframework.security.config.core;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Rob Winch
 */
public class HelloWorldMessageService implements MessageService {

	@Override
	@PreAuthorize("hasRole('USER')")
	public String getMessage() {
		return "Hello World";
	}

	@Override
	@RolesAllowed("USER")
	public String getJsrMessage() {
		return "Hello JSR";
	}

}

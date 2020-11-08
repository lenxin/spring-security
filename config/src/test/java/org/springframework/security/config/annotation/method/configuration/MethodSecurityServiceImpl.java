package org.springframework.security.config.annotation.method.configuration;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Rob Winch
 */
public class MethodSecurityServiceImpl implements MethodSecurityService {

	@Override
	public String preAuthorize() {
		return null;
	}

	@Override
	public String secured() {
		return null;
	}

	@Override
	public String securedUser() {
		return null;
	}

	@Override
	public String jsr250() {
		return null;
	}

	@Override
	public String jsr250PermitAll() {
		return null;
	}

	@Override
	public Authentication runAs() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@Override
	public void preAuthorizeNotAnonymous() {
	}

	@Override
	public void preAuthorizeBean(boolean b) {
	}

	@Override
	public void preAuthorizeAdmin() {
	}

	@Override
	public String preAuthorizePermitAll() {
		return null;
	}

	@Override
	public String hasPermission(String object) {
		return null;
	}

	@Override
	public String postHasPermission(String object) {
		return null;
	}

	@Override
	public String postAnnotation(String object) {
		return null;
	}

}

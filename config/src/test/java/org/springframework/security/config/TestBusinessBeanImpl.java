package org.springframework.security.config;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionCreationEvent;

/**
 * @author Luke Taylor
 */
public class TestBusinessBeanImpl implements TestBusinessBean, ApplicationListener<SessionCreationEvent> {

	@Override
	public void setInteger(int i) {
	}

	@Override
	public int getInteger() {
		return 1314;
	}

	@Override
	public void setString(String s) {
	}

	public String getString() {
		return "A string.";
	}

	@Override
	public void doSomething() {
	}

	@Override
	public void unprotected() {
	}

	@Override
	public void onApplicationEvent(SessionCreationEvent event) {
		System.out.println(event);
	}

}

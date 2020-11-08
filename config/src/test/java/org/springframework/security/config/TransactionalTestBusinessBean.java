package org.springframework.security.config;

import org.springframework.transaction.annotation.Transactional;

/**
 * @author Luke Taylor
 */
public class TransactionalTestBusinessBean implements TestBusinessBean {

	@Override
	public void setInteger(int i) {
	}

	@Override
	public int getInteger() {
		return 0;
	}

	@Override
	public void setString(String s) {
	}

	@Override
	@Transactional
	public void doSomething() {
	}

	@Override
	public void unprotected() {
	}

}

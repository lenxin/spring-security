package org.springframework.security.integration.python;

public class TestServiceImpl implements TestService {

	@Override
	public void someMethod() {
		System.out.print("Invoked someMethod()");
	}

}

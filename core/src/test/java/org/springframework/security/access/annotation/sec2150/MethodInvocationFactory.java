package org.springframework.security.access.annotation.sec2150;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.security.access.intercept.method.MockMethodInvocation;

public final class MethodInvocationFactory {

	private MethodInvocationFactory() {
	}

	/**
	 * In order to reproduce the bug for SEC-2150, we must have a proxy object that
	 * implements TargetSourceAware and implements our annotated interface.
	 * @return the mock method invocation
	 * @throws NoSuchMethodException
	 */
	public static MockMethodInvocation createSec2150MethodInvocation() throws NoSuchMethodException {
		ProxyFactory factory = new ProxyFactory(new Class[] { PersonRepository.class });
		factory.setTargetClass(CrudRepository.class);
		PersonRepository repository = (PersonRepository) factory.getProxy();
		return new MockMethodInvocation(repository, PersonRepository.class, "findAll");
	}

}

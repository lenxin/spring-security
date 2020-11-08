package org.springframework.security.remoting.rmi;

import org.aopalliance.intercept.MethodInvocation;

import org.springframework.remoting.support.RemoteInvocation;
import org.springframework.remoting.support.RemoteInvocationFactory;

/**
 * Called by a client-side instance of
 * <code>org.springframework.remoting.rmi.RmiProxyFactoryBean</code> when it wishes to
 * create a remote invocation.
 * <P>
 * Set an instance of this bean against the above class'
 * <code>remoteInvocationFactory</code> property.
 * </p>
 *
 * @author James Monaghan
 * @author Ben Alex
 */
public class ContextPropagatingRemoteInvocationFactory implements RemoteInvocationFactory {

	@Override
	public RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
		return new ContextPropagatingRemoteInvocation(methodInvocation);
	}

}

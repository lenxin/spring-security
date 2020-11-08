package org.springframework.security.remoting.rmi;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.After;
import org.junit.Test;

import org.springframework.security.TargetObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.SimpleMethodInvocation;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link ContextPropagatingRemoteInvocation} and
 * {@link ContextPropagatingRemoteInvocationFactory}.
 *
 * @author Ben Alex
 */
public class ContextPropagatingRemoteInvocationTests {

	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}

	private ContextPropagatingRemoteInvocation getRemoteInvocation() throws Exception {
		Class<TargetObject> clazz = TargetObject.class;
		Method method = clazz.getMethod("makeLowerCase", new Class[] { String.class });
		MethodInvocation mi = new SimpleMethodInvocation(new TargetObject(), method, "SOME_STRING");
		ContextPropagatingRemoteInvocationFactory factory = new ContextPropagatingRemoteInvocationFactory();
		return (ContextPropagatingRemoteInvocation) factory.createRemoteInvocation(mi);
	}

	@Test
	public void testContextIsResetEvenIfExceptionOccurs() throws Exception {
		// Setup client-side context
		Authentication clientSideAuthentication = new UsernamePasswordAuthenticationToken("rod", "koala");
		SecurityContextHolder.getContext().setAuthentication(clientSideAuthentication);
		ContextPropagatingRemoteInvocation remoteInvocation = getRemoteInvocation();
		// Set up the wrong arguments.
		remoteInvocation.setArguments(new Object[] {});
		assertThatIllegalArgumentException()
				.isThrownBy(() -> remoteInvocation.invoke(TargetObject.class.newInstance()));
		assertThat(SecurityContextHolder.getContext().getAuthentication())
				.withFailMessage("Authentication must be null").isNull();
	}

	@Test
	public void testNormalOperation() throws Exception {
		// Setup client-side context
		Authentication clientSideAuthentication = new UsernamePasswordAuthenticationToken("rod", "koala");
		SecurityContextHolder.getContext().setAuthentication(clientSideAuthentication);
		ContextPropagatingRemoteInvocation remoteInvocation = getRemoteInvocation();
		// Set to null, as ContextPropagatingRemoteInvocation already obtained
		// a copy and nulling is necessary to ensure the Context delivered by
		// ContextPropagatingRemoteInvocation is used on server-side
		SecurityContextHolder.clearContext();
		// The result from invoking the TargetObject should contain the
		// Authentication class delivered via the SecurityContextHolder
		assertThat(remoteInvocation.invoke(new TargetObject())).isEqualTo(
				"some_string org.springframework.security.authentication.UsernamePasswordAuthenticationToken false");
	}

	@Test
	public void testNullContextHolderDoesNotCauseInvocationProblems() throws Exception {
		SecurityContextHolder.clearContext(); // just to be explicit
		ContextPropagatingRemoteInvocation remoteInvocation = getRemoteInvocation();
		SecurityContextHolder.clearContext(); // unnecessary, but for
												// explicitness
		assertThat(remoteInvocation.invoke(new TargetObject())).isEqualTo("some_string Authentication empty");
	}

	// SEC-1867
	@Test
	public void testNullCredentials() throws Exception {
		Authentication clientSideAuthentication = new UsernamePasswordAuthenticationToken("rod", null);
		SecurityContextHolder.getContext().setAuthentication(clientSideAuthentication);
		ContextPropagatingRemoteInvocation remoteInvocation = getRemoteInvocation();
		assertThat(ReflectionTestUtils.getField(remoteInvocation, "credentials")).isNull();
	}

}

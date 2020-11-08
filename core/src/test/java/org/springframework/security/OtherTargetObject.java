package org.springframework.security;

/**
 * Simply extends {@link TargetObject} so we have a different object to put configuration
 * attributes against.
 * <P>
 * There is no different behaviour. We have to define each method so that
 * <code>Class.getMethod(methodName, args)</code> returns a <code>Method</code>
 * referencing this class rather than the parent class.
 * </p>
 * <P>
 * We need to implement <code>ITargetObject</code> again because the
 * <code>MethodDefinitionAttributes</code> only locates attributes on interfaces
 * explicitly defined by the intercepted class (not the interfaces defined by its parent
 * class or classes).
 * </p>
 *
 * @author Ben Alex
 */
public class OtherTargetObject extends TargetObject implements ITargetObject {

	@Override
	public String makeLowerCase(String input) {
		return super.makeLowerCase(input);
	}

	@Override
	public String makeUpperCase(String input) {
		return super.makeUpperCase(input);
	}

	@Override
	public String publicMakeLowerCase(String input) {
		return super.publicMakeLowerCase(input);
	}

}

package org.springframework.security.config.method.sec2499;

import org.junit.After;
import org.junit.Test;

import org.springframework.context.support.GenericXmlApplicationContext;

/**
 * @author Rob Winch
 *
 */
public class Sec2499Tests {

	private GenericXmlApplicationContext parent;

	private GenericXmlApplicationContext child;

	@After
	public void cleanup() {
		if (this.parent != null) {
			this.parent.close();
		}
		if (this.child != null) {
			this.child.close();
		}
	}

	@Test
	public void methodExpressionHandlerInParentContextLoads() {
		this.parent = new GenericXmlApplicationContext("org/springframework/security/config/method/sec2499/parent.xml");
		this.child = new GenericXmlApplicationContext();
		this.child.load("org/springframework/security/config/method/sec2499/child.xml");
		this.child.setParent(this.parent);
		this.child.refresh();
	}

}

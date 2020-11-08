package org.springframework.security.config.method;

/**
 * @author Rob Winch
 *
 */
public class Contact {

	private String name;

	/**
	 * @param name
	 */
	public Contact(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

}

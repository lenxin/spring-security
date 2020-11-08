package org.springframework.security.config.doc;

/**
 * Represents a Spring Security XSD Attribute. It is created when parsing the current xsd
 * to compare to the documented appendix.
 *
 * @author Rob Winch
 * @author Josh Cummings
 * @see SpringSecurityXsdParser
 * @see XsdDocumentedTests
 */
public class Attribute {

	private String name;

	private String desc;

	private Element elmt;

	public Attribute(String desc, String name) {
		this.desc = desc;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Element getElmt() {
		return this.elmt;
	}

	public void setElmt(Element elmt) {
		this.elmt = elmt;
	}

	public String getId() {
		return String.format("%s-%s", this.elmt.getId(), this.name);
	}

}

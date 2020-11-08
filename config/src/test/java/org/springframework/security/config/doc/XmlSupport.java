package org.springframework.security.config.doc;

import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;

/**
 * Support for ensuring preparing the givens in {@link XsdDocumentedTests}
 *
 * @author Josh Cummings
 */
public class XmlSupport {

	private XmlParser parser;

	public XmlNode parse(String location) throws IOException {
		ClassPathResource resource = new ClassPathResource(location);
		this.parser = new XmlParser(resource.getInputStream());
		return this.parser.parse();
	}

	public Map<String, Element> elementsByElementName(String location) throws IOException {
		XmlNode node = parse(location);
		return new SpringSecurityXsdParser(node).parse();
	}

	public void close() throws IOException {
		if (this.parser != null) {
			this.parser.close();
		}
	}

}

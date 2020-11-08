package org.springframework.security.config.doc;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * @author Josh Cummings
 */
public class XmlParser implements AutoCloseable {

	private InputStream xml;

	public XmlParser(InputStream xml) {
		this.xml = xml;
	}

	public XmlNode parse() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			return new XmlNode(dBuilder.parse(this.xml));
		}
		catch (IOException | ParserConfigurationException | SAXException ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public void close() throws IOException {
		this.xml.close();
	}

}

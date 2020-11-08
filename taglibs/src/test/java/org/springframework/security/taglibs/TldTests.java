package org.springframework.security.taglibs;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Test;
import org.w3c.dom.Document;

import static org.assertj.core.api.Assertions.assertThat;

public class TldTests {

	// SEC-2324
	@Test
	public void testTldVersionIsCorrect() throws Exception {
		String SPRING_SECURITY_VERSION = "springSecurityVersion";
		String version = System.getProperty(SPRING_SECURITY_VERSION);
		File securityTld = new File("src/main/resources/META-INF/security.tld");
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(securityTld);
		String tlibVersion = document.getElementsByTagName("tlib-version").item(0).getTextContent();
		assertThat(version).startsWith(tlibVersion);
	}

}

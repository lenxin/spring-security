package org.springframework.security.samples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.security.samples.pages.HomePage;

/**
 * @author Michael Simons
 */
public class HelloInsecureTests {

	private WebDriver driver;

	private int port;

	@Before
	public void setup() {
		this.port = Integer.parseInt(System.getProperty("app.httpPort"));
		this.driver = new HtmlUnitDriver();
	}

	@After
	public void tearDown() {
		this.driver.quit();
	}

	@Test
	public void theHomePageIsAccessible() {
		HomePage.to(this.driver, this.port)
			.assertAt()
			.andWeCanSeeTheMessage();
	}
}

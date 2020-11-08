package org.springframework.security.samples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.security.samples.pages.HomePage;
import org.springframework.security.samples.pages.LoginPage;

/**
 * @author Michael Simons
 */
public class FormJcTests {
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
	public void accessHomePageWithUnauthenticatedUserSendsToLoginPage() {
		final LoginPage loginPage = HomePage.to(this.driver, this.port);
		loginPage.assertAt();
	}

	@Test
	public void authenticatedUserIsSentToOriginalPage() {
		final String userName = "user";
		final HomePage homePage = HomePage.to(this.driver, this.port)
			.loginForm()
				.username(userName)
				.password("password")
			.submit();
		homePage
			.assertAt()
			.andTheUserNameDisplayedIs(userName);
	}

	@Test
	public void authenticatedUserLogsOut() {
		LoginPage loginPage = HomePage.to(this.driver, this.port)
			.loginForm()
				.username("user")
				.password("password")
			.submit()
			.logout();
		loginPage.assertAt();

		loginPage = HomePage.to(this.driver, this.port);
		loginPage.assertAt();
	}
}

package org.springframework.security.samples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.security.samples.pages.HomePage;
import org.springframework.security.samples.pages.LoginPage;
import org.springframework.security.samples.pages.LogoutPage;
import org.springframework.security.samples.pages.SecurePage;

/**
 * @author Michael Simons
 */
public class LdapXmlTests {
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
	public void accessHomepageWithUnauthenticatedUserSuccess() {
		final HomePage homePage = HomePage.to(this.driver, this.port);
		homePage.assertAt();
	}

	@Test
	public void accessManagePageWithUnauthenticatedUserSendsToLoginPage() {
		final LoginPage loginPage = SecurePage.to(this.driver, this.port);
		loginPage.assertAt();
	}

	@Test
	public void authenticatedUserIsSentToOriginalPage() {
		final SecurePage securePage = SecurePage.to(this.driver, this.port)
			.loginForm()
				.username("rod")
				.password("koala")
			.submit();
		securePage
			.assertAt();
	}

	@Test
	public void authenticatedUserLogsOut() {
		final LogoutPage logoutPage = SecurePage.to(this.driver, this.port)
			.loginForm()
				.username("rod")
				.password("koala")
			.submit()
			.logout();
		logoutPage.assertAt();

		final LoginPage loginPage = SecurePage.to(this.driver, this.port);
		loginPage.assertAt();
	}
}

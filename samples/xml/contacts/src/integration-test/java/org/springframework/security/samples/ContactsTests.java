package org.springframework.security.samples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.security.samples.pages.ContactsPage;
import org.springframework.security.samples.pages.HomePage;

/**
 * @author Michael Simons
 */
public class ContactsTests {
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
	public void accessHomePageWithUnauthenticatedUserSuccess() {
		final HomePage homePage = HomePage.to(this.driver, this.port);
		homePage.assertAt();
	}

	@Test
	public void authenticatedUserCanAddContacts() {
		final String name = "Rob Winch";
		final String email = "rob@example.com";

		// @formatter:off
		ContactsPage.accessManagePageWithUnauthenticatedUser(this.driver, this.port)
			.sendsToLoginPage()
				.username("rod")
				.password("koala")
			.submit()
			.isAtContactsPage()
			.addContact()
				.name(name)
				.email(email)
			.submit()
			.andHasContact(name, email)
			.delete()
			.andConfirmDeletion()
			.isAtContactsPage()
			.andConctactHasBeenRemoved(name, email);
		// @formatter:on
	}


	@Test
	public void authenticatedUserLogsOut() {
		// @formatter:off
		final HomePage homePage = ContactsPage.accessManagePageWithUnauthenticatedUser(this.driver, this.port)
			.sendsToLoginPage()
				.username("rod")
				.password("koala")
			.submit()
			.isAtContactsPage()
			.logout();
		// @formatter:on
		homePage.assertAt();

		ContactsPage.accessManagePageWithUnauthenticatedUser(this.driver, this.port)
			.sendsToLoginPage();
	}
}

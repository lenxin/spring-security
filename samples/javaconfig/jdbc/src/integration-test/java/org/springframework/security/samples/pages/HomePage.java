package org.springframework.security.samples.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michael Simons
 */
public class HomePage {
	private final WebDriver webDriver;

	@FindBy(css = "input[type=submit]")
	private WebElement logoutButton;

	public static LoginPage to(WebDriver driver, int port) {
		driver.get("http://localhost:" + port +"/");
		return PageFactory.initElements(driver, LoginPage.class);
	}

	public HomePage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public Content assertAt() {
		assertThat(this.webDriver.getTitle()).endsWith("View All");
		return PageFactory.initElements(this.webDriver, Content.class);
	}

	public LoginPage logout() {
		this.logoutButton.submit();
		return PageFactory.initElements(this.webDriver, LoginPage.class);
	}

	public static class Content {
		@FindBy(css = "p.navbar-text")
		private WebElement message;

		public Content andTheUserNameDisplayedIs(final String userName) {
			assertThat(message.getText()).isEqualTo(userName);
			return this;
		}
	}
}

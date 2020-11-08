package org.springframework.security.samples.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Logout Page is the same as login page with an additional message.
 *
 * @author Michael Simons
 */
public class LogoutPage extends LoginPage {
	@FindBy(css = "div[role=alert]")
	private WebElement alert;

	public LogoutPage(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	public LogoutPage assertAt() {
		super.assertAt();

		assertThat(this.alert.getText()).isEqualTo("You have been signed out");
		return this;
	}
}

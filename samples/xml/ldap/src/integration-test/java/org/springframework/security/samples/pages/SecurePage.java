package org.springframework.security.samples.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A secure page.
 *
 * @author Michael Simons
 */
public class SecurePage {

	public static LoginPage to(WebDriver driver, int port) {
		driver.get("http://localhost:" + port + "/secure");
		return PageFactory.initElements(driver, LoginPage.class);
	}

	private final WebDriver webDriver;

	@FindBy(css = "input[type=submit]")
	private WebElement submit;

	public SecurePage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public SecurePage assertAt() {
		assertThat(this.webDriver.getTitle()).isEqualTo("Secure Page");
		return this;
	}

	public LogoutPage logout() {
		this.submit.click();
		return PageFactory.initElements(this.webDriver, LogoutPage.class);
	}
}

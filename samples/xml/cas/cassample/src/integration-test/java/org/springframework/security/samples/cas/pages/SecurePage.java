package org.springframework.security.samples.cas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Represents the secure page within the CAS Sample application.
 *
 * @author Rob Winch
 * @author Josh Cummings
 */
public class SecurePage extends Page<SecurePage> {
	private final Content content;

	public SecurePage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl + "/secure");
		this.content = PageFactory.initElements(driver, Content.class);
	}

	@Override
	public SecurePage assertAt() {
		assertThat(this.content.header()).isEqualTo("Secure Page");
		return this;
	}

	public SecurePage logout() {
		this.content.logout();
		return this;
	}

	public static class Content {
		@FindBy(tagName="h1")
		WebElement header;

		WebElement logout;

		public String header() {
			return this.header.getText();
		}

		public void logout() {
			this.logout.click();
		}
	}

	// had nav
}

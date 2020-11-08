package org.springframework.security.samples.cas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Represents the access denied page
 *
 * @author Rob Winch
 * @author Josh Cummings
 */
public class AccessDeniedPage {

	private final Content content;

	public AccessDeniedPage(WebDriver driver) {
		this.content = PageFactory.initElements(driver, Content.class);
	}

	public AccessDeniedPage assertAt() {
		assertThat(this.content.header()).contains("403 - Access Denied");
		return this;
	}

	public static class Content {
		@FindBy(tagName="h1")
		WebElement header;

		public String header() {
			return this.header.getText();
		}
	}
}

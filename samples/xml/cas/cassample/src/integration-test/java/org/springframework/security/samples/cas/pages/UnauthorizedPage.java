package org.springframework.security.samples.cas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Represents the unauthorized page
 *
 * @author Josh Cummings
 */
public class UnauthorizedPage {

	private final Content content;

	public UnauthorizedPage(WebDriver driver) {
		this.content = PageFactory.initElements(driver, Content.class);
	}

	public UnauthorizedPage assertAt() {
		assertThat(this.content.header()).contains("401 - Unauthorized");
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

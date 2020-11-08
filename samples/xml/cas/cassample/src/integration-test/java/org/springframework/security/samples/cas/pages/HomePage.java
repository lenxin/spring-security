package org.springframework.security.samples.cas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Represents the Home page of the CAS sample application
 *
 * @author Rob Winch
 * @author Josh Cummings
 */
public class HomePage extends Page<HomePage> {
	private final Content content;

	public HomePage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl);
		this.content = PageFactory.initElements(driver, Content.class);
	}

	@Override
	public HomePage assertAt() {
		assertThat(this.content.header()).isEqualTo("Home Page");
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

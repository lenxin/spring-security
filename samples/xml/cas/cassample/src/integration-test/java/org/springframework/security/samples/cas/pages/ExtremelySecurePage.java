package org.springframework.security.samples.cas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Represents the extremely secure page of the CAS Sample application.
 *
 * @author Rob Winch
 * @author Josh Cummings
 */
public class ExtremelySecurePage extends Page<ExtremelySecurePage> {
	private final Content content;

	public ExtremelySecurePage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl + "/secure/extreme");
		this.content = PageFactory.initElements(driver, Content.class);
	}

	@Override
	public ExtremelySecurePage assertAt() {
		assertThat(this.content.getText()).isEqualTo("VERY Secure Page");
		return this;
	}

	public static class Content {
		@FindBy(tagName="h1")
		WebElement header;

		public String getText() {
			return this.header.getText();
		}
	}
}

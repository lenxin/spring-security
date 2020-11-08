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

	public HomePage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public static HomePage to(WebDriver driver, int port) {
		driver.get("http://localhost:" + port +"/");
		return PageFactory.initElements(driver, HomePage.class);
	}

	public Content assertAt() {
		assertThat(this.webDriver.getTitle()).isEqualTo("Hello World");
		return PageFactory.initElements(this.webDriver, Content.class);
	}

	public static class Content {
		@FindBy(css = "p")
		private WebElement message;

		public Content andWeCanSeeTheMessage() {
			assertThat(message.getText()).isEqualTo("We would like to secure this page");
			return this;
		}
	}
}

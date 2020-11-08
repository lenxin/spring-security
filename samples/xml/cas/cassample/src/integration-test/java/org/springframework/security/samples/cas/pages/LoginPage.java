package org.springframework.security.samples.cas.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * The CAS login page.
 *
 * @author Rob Winch
 * @author Josh Cummings
 */
public class LoginPage extends Page<LoginPage> {
	private final Content content;

	public LoginPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl + "/login");
		this.content = PageFactory.initElements(driver, Content.class);
	}

	public void login(String user) {
		login(user, user);
	}

	public void login(String user, String password) {
		this.content.username(user).password(password).submit();
	}

	public static class Content {
		private WebElement username;
		private WebElement password;
		@FindBy(css = "input[type=submit]")
		private WebElement submit;

		public Content username(String username) {
			this.username.sendKeys(username);
			return this;
		}

		public Content password(String password) {
			this.password.sendKeys(password);
			return this;
		}

		public void submit() {
			this.submit.click();
		}
	}
}

package sample.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class LoginPage {

	private WebDriver driver;
	@FindBy(css = "div[role=alert]")
	private WebElement alert;

	private LoginForm loginForm;

	public LoginPage(WebDriver webDriver) {
		this.driver = webDriver;
		this.loginForm = PageFactory.initElements(webDriver, LoginForm.class);
	}

	static LoginPage create(WebDriver driver) {
		return PageFactory.initElements(driver, LoginPage.class);
	}

	public LoginPage assertAt() {
		assertThat(this.driver.getTitle()).isEqualTo("Please Log In");
		return this;
	}

	public LoginPage assertError() {
		assertThat(this.alert.getText()).isEqualTo("Invalid username and password.");
		return this;
	}

	public LoginPage assertLogout() {
		assertThat(this.alert.getText()).isEqualTo("You have been logged out.");
		return this;
	}

	public LoginForm loginForm() {
		return this.loginForm;
	}

	public static class LoginForm {
		private WebDriver driver;
		private WebElement username;
		private WebElement password;
		@FindBy(css = "button[type=submit]")
		private WebElement submit;

		public LoginForm(WebDriver driver) {
			this.driver = driver;
		}

		public LoginForm username(String username) {
			this.username.sendKeys(username);
			return this;
		}

		public LoginForm password(String password) {
			this.password.sendKeys(password);
			return this;
		}

		public <T> T submit(Class<T> page) {
			this.submit.click();
			return PageFactory.initElements(this.driver, page);
		}
	}
}

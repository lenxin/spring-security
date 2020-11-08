package sample.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class IndexPage {

	private WebDriver driver;

	private WebElement logout;

	public IndexPage(WebDriver webDriver) {
		this.driver = webDriver;
	}

	public static <T> T to(WebDriver driver, int port, Class<T> page) {
		driver.get("http://localhost:" + port +"/");
		return PageFactory.initElements(driver, page);
	}

	public IndexPage assertAt() {
		assertThat(this.driver.getTitle()).isEqualTo("Secured");
		return this;
	}

	public LoginPage logout() {
		this.logout.click();
		return LoginPage.create(this.driver);
	}
}

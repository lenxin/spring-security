package org.springframework.security.htmlunit.server;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebConnection;
import org.openqa.selenium.WebDriver;

import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.htmlunit.DelegatingWebConnection;
import org.springframework.test.web.servlet.htmlunit.DelegatingWebConnection.DelegateWebConnection;
import org.springframework.test.web.servlet.htmlunit.HostRequestMatcher;
import org.springframework.test.web.servlet.htmlunit.webdriver.WebConnectionHtmlUnitDriver;

/**
 * @author Rob Winch
 * @since 5.0
 */
public final class WebTestClientHtmlUnitDriverBuilder {

	private final WebTestClient webTestClient;

	private WebTestClientHtmlUnitDriverBuilder(WebTestClient webTestClient) {
		this.webTestClient = webTestClient;
	}

	public WebDriver build() {
		WebConnectionHtmlUnitDriver driver = new WebConnectionHtmlUnitDriver();
		WebClient webClient = driver.getWebClient();
		WebTestClientWebConnection webClientConnection = new WebTestClientWebConnection(this.webTestClient, webClient);
		WebConnection connection = new DelegatingWebConnection(driver.getWebConnection(),
				new DelegateWebConnection(new HostRequestMatcher("localhost"), webClientConnection));
		driver.setWebConnection(connection);
		return driver;
	}

	public static WebTestClientHtmlUnitDriverBuilder webTestClientSetup(WebTestClient webTestClient) {
		return new WebTestClientHtmlUnitDriverBuilder(webTestClient);
	}

}

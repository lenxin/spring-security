package org.springframework.security.samples.cas.pages;

import org.openqa.selenium.WebDriver;

/**
 * This represents the local logout page. This page is where the user is logged out of the CAS Sample application, but
 * since the user is still logged into the CAS Server accessing a protected page within the CAS Sample application would result
 * in SSO occurring again. To fully logout, the user should click the cas server logout url which logs out of the cas server and performs
 * single logout on the other services.
 *
 * @author Rob Winch
 * @author Josh Cummings
 */
public class LocalLogoutPage extends Page<LocalLogoutPage> {
	public LocalLogoutPage(WebDriver driver, String baseUrl) {
		super(driver, baseUrl + "/cas-logout.jsp");
	}
}

package org.springframework.security.samples.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michael Simons
 */
public class AddPage {

	private final WebDriver webDriver;

	private final AddForm addForm;

	public AddPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.addForm = PageFactory.initElements(this.webDriver, AddForm.class);
	}

	AddForm addForm() {
		assertThat(this.webDriver.getTitle()).isEqualTo("Add New Contact");
		return this.addForm;
	}

	public static class AddForm {
		private WebDriver webDriver;
		private WebElement name;
		private WebElement email;
		@FindBy(css = "input[type=submit]")
		private WebElement submit;

		public AddForm(WebDriver webDriver) {
			this.webDriver = webDriver;
		}

		public AddForm name(String name) {
			this.name.sendKeys(name);
			return this;
		}

		public AddForm email(String email) {
			this.email.sendKeys(email);
			return this;
		}

		public ContactsPage submit() {
			this.submit.click();
			return PageFactory.initElements(this.webDriver, ContactsPage.class);
		}
	}
}

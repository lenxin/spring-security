package org.springframework.security.samples.cas.pages;

import java.util.function.Function;

import org.openqa.selenium.WebDriver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Josh Cummings
 */
public abstract class Page<T extends Page<T>> {
	private final WebDriver driver;
	private final String url;

	protected Page(WebDriver driver, String url) {
		this.driver = driver;
		this.url = url;
	}

	public T assertAt() {
		assertThat(this.driver.getCurrentUrl()).startsWith(this.url);
		return (T) this;
	}

	public T to() {
		this.driver.get(this.url);
		return (T) this;
	}

	public T to(Function<String, String> urlPostProcessor) {
		this.driver.get(urlPostProcessor.apply(this.url));
		return (T) this;
	}
}

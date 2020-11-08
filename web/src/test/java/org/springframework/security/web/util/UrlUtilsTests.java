package org.springframework.security.web.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Luke Taylor
 */
public class UrlUtilsTests {

	@Test
	public void absoluteUrlsAreMatchedAsAbsolute() {
		assertThat(UrlUtils.isAbsoluteUrl("https://something/")).isTrue();
		assertThat(UrlUtils.isAbsoluteUrl("http1://something/")).isTrue();
		assertThat(UrlUtils.isAbsoluteUrl("HTTP://something/")).isTrue();
		assertThat(UrlUtils.isAbsoluteUrl("https://something/")).isTrue();
		assertThat(UrlUtils.isAbsoluteUrl("a://something/")).isTrue();
		assertThat(UrlUtils.isAbsoluteUrl("zz+zz.zz-zz://something/")).isTrue();
	}

	@Test
	public void isAbsoluteUrlWhenNullThenFalse() {
		assertThat(UrlUtils.isAbsoluteUrl(null)).isFalse();
	}

	@Test
	public void isAbsoluteUrlWhenEmptyThenFalse() {
		assertThat(UrlUtils.isAbsoluteUrl("")).isFalse();
	}

	@Test
	public void isValidRedirectUrlWhenNullThenFalse() {
		assertThat(UrlUtils.isValidRedirectUrl(null)).isFalse();
	}

	@Test
	public void isValidRedirectUrlWhenEmptyThenFalse() {
		assertThat(UrlUtils.isValidRedirectUrl("")).isFalse();
	}

}

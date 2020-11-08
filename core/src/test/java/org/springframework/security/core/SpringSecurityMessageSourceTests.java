package org.springframework.security.core;

import java.util.Locale;

import org.junit.Test;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link org.springframework.security.core.SpringSecurityMessageSource}.
 */
public class SpringSecurityMessageSourceTests {

	@Test
	public void testOperation() {
		SpringSecurityMessageSource msgs = new SpringSecurityMessageSource();
		assertThat("\u4E0D\u5141\u8BB8\u8BBF\u95EE").isEqualTo(
				msgs.getMessage("AbstractAccessDecisionManager.accessDenied", null, Locale.SIMPLIFIED_CHINESE));
	}

	@Test
	public void testReplacableLookup() {
		// Change Locale to English
		Locale before = LocaleContextHolder.getLocale();
		LocaleContextHolder.setLocale(Locale.FRENCH);
		// Cause a message to be generated
		MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
		assertThat("Le jeton nonce est compromis FOOBAR").isEqualTo(messages.getMessage(
				"DigestAuthenticationFilter.nonceCompromised", new Object[] { "FOOBAR" }, "ERROR - FAILED TO LOOKUP"));
		// Revert to original Locale
		LocaleContextHolder.setLocale(before);
	}

	// SEC-3013
	@Test
	public void germanSystemLocaleWithEnglishLocaleContextHolder() {
		Locale beforeSystem = Locale.getDefault();
		Locale.setDefault(Locale.GERMAN);
		Locale beforeHolder = LocaleContextHolder.getLocale();
		LocaleContextHolder.setLocale(Locale.US);
		MessageSourceAccessor msgs = SpringSecurityMessageSource.getAccessor();
		assertThat("Access is denied")
				.isEqualTo(msgs.getMessage("AbstractAccessDecisionManager.accessDenied", "Ooops"));
		// Revert to original Locale
		Locale.setDefault(beforeSystem);
		LocaleContextHolder.setLocale(beforeHolder);
	}

}

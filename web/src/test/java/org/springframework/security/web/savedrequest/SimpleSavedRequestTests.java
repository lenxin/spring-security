package org.springframework.security.web.savedrequest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleSavedRequestTests {

	@Test
	public void constructorWhenGivenSavedRequestThenCopies() {
		SavedRequest savedRequest = new SimpleSavedRequest(prepareSavedRequest());
		assertThat(savedRequest.getMethod()).isEqualTo("POST");
		List<Cookie> cookies = savedRequest.getCookies();
		assertThat(cookies).hasSize(1);
		Cookie cookie = cookies.get(0);
		assertThat(cookie.getName()).isEqualTo("cookiename");
		assertThat(cookie.getValue()).isEqualTo("cookievalue");
		Collection<String> headerNames = savedRequest.getHeaderNames();
		assertThat(headerNames).hasSize(1);
		String headerName = headerNames.iterator().next();
		assertThat(headerName).isEqualTo("headername");
		List<String> headerValues = savedRequest.getHeaderValues("headername");
		assertThat(headerValues).hasSize(1);
		String headerValue = headerValues.get(0);
		assertThat(headerValue).isEqualTo("headervalue");
		List<Locale> locales = savedRequest.getLocales();
		assertThat(locales).hasSize(1);
		Locale locale = locales.get(0);
		assertThat(locale).isEqualTo(Locale.ENGLISH);
		Map<String, String[]> parameterMap = savedRequest.getParameterMap();
		assertThat(parameterMap).hasSize(1);
		String[] values = parameterMap.get("key");
		assertThat(values).hasSize(1);
		assertThat(values[0]).isEqualTo("value");
	}

	@Test
	public void constructorWhenGivenRedirectUrlThenDefaultValues() {
		SavedRequest savedRequest = new SimpleSavedRequest("redirectUrl");
		assertThat(savedRequest.getMethod()).isEqualTo("GET");
		assertThat(savedRequest.getCookies()).isEmpty();
		assertThat(savedRequest.getHeaderNames()).isEmpty();
		assertThat(savedRequest.getHeaderValues("headername")).isEmpty();
		assertThat(savedRequest.getLocales()).isEmpty();
		assertThat(savedRequest.getParameterMap()).isEmpty();
	}

	private SimpleSavedRequest prepareSavedRequest() {
		SimpleSavedRequest simpleSavedRequest = new SimpleSavedRequest("redirectUrl");
		simpleSavedRequest.setCookies(Collections.singletonList(new Cookie("cookiename", "cookievalue")));
		simpleSavedRequest.setMethod("POST");
		simpleSavedRequest.setHeaders(Collections.singletonMap("headername", Collections.singletonList("headervalue")));
		simpleSavedRequest.setLocales(Collections.singletonList(Locale.ENGLISH));
		simpleSavedRequest.setParameters(Collections.singletonMap("key", new String[] { "value" }));
		return simpleSavedRequest;
	}

}

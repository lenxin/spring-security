package org.springframework.security.web.server.util.matcher;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class MediaTypeServerWebExchangeMatcherTests {

	private MediaTypeServerWebExchangeMatcher matcher;

	@Test
	public void constructorMediaTypeArrayWhenNullThenThrowsIllegalArgumentException() {
		MediaType[] types = null;
		assertThatIllegalArgumentException().isThrownBy(() -> new MediaTypeServerWebExchangeMatcher(types));
	}

	@Test
	public void constructorMediaTypeArrayWhenContainsNullThenThrowsIllegalArgumentException() {
		MediaType[] types = { null };
		assertThatIllegalArgumentException().isThrownBy(() -> new MediaTypeServerWebExchangeMatcher(types));
	}

	@Test
	public void constructorMediaTypeListWhenNullThenThrowsIllegalArgumentException() {
		List<MediaType> types = null;
		assertThatIllegalArgumentException().isThrownBy(() -> new MediaTypeServerWebExchangeMatcher(types));
	}

	@Test
	public void constructorMediaTypeListWhenContainsNullThenThrowsIllegalArgumentException() {
		List<MediaType> types = Collections.singletonList(null);
		assertThatIllegalArgumentException().isThrownBy(() -> new MediaTypeServerWebExchangeMatcher(types));
	}

	@Test
	public void matchWhenDefaultResolverAndAcceptEqualThenMatch() {
		MediaType acceptType = MediaType.TEXT_HTML;
		MediaTypeServerWebExchangeMatcher matcher = new MediaTypeServerWebExchangeMatcher(acceptType);
		assertThat(matcher.matches(exchange(acceptType)).block().isMatch()).isTrue();
	}

	@Test
	public void matchWhenDefaultResolverAndAcceptEqualAndIgnoreThenMatch() {
		MediaType acceptType = MediaType.TEXT_HTML;
		MediaTypeServerWebExchangeMatcher matcher = new MediaTypeServerWebExchangeMatcher(acceptType);
		matcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
		assertThat(matcher.matches(exchange(acceptType)).block().isMatch()).isTrue();
	}

	@Test
	public void matchWhenDefaultResolverAndAcceptEqualAndIgnoreThenNotMatch() {
		MediaType acceptType = MediaType.TEXT_HTML;
		MediaTypeServerWebExchangeMatcher matcher = new MediaTypeServerWebExchangeMatcher(acceptType);
		matcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
		assertThat(matcher.matches(exchange(MediaType.ALL)).block().isMatch()).isFalse();
	}

	@Test
	public void matchWhenDefaultResolverAndAcceptImpliedThenMatch() {
		MediaTypeServerWebExchangeMatcher matcher = new MediaTypeServerWebExchangeMatcher(
				MediaType.parseMediaTypes("text/*"));
		assertThat(matcher.matches(exchange(MediaType.TEXT_HTML)).block().isMatch()).isTrue();
	}

	@Test
	public void matchWhenDefaultResolverAndAcceptImpliedAndUseEqualsThenNotMatch() {
		MediaTypeServerWebExchangeMatcher matcher = new MediaTypeServerWebExchangeMatcher(MediaType.ALL);
		matcher.setUseEquals(true);
		assertThat(matcher.matches(exchange(MediaType.TEXT_HTML)).block().isMatch()).isFalse();
	}

	private static ServerWebExchange exchange(MediaType... accept) {
		return MockServerWebExchange.from(MockServerHttpRequest.get("/").accept(accept).build());
	}

}

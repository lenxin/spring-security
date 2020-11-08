package org.springframework.security.web.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class TextEscapeUtilsTests {

	/**
	 * &amp;, &lt;, &gt;, &#34;, &#39 and&#32;(space) escaping
	 */
	@Test
	public void charactersAreEscapedCorrectly() {
		assertThat(TextEscapeUtils.escapeEntities("& a<script>\"'")).isEqualTo("&amp;&#32;a&lt;script&gt;&#34;&#39;");
	}

	@Test
	public void nullOrEmptyStringIsHandled() {
		assertThat(TextEscapeUtils.escapeEntities("")).isEqualTo("");
		assertThat(TextEscapeUtils.escapeEntities(null)).isNull();
	}

	@Test
	public void invalidLowSurrogateIsDetected() {
		assertThatIllegalArgumentException().isThrownBy(() -> TextEscapeUtils.escapeEntities("abc\uDCCCdef"));
	}

	@Test
	public void missingLowSurrogateIsDetected() {
		assertThatIllegalArgumentException().isThrownBy(() -> TextEscapeUtils.escapeEntities("abc\uD888a"));
	}

	@Test
	public void highSurrogateAtEndOfStringIsRejected() {
		assertThatIllegalArgumentException().isThrownBy(() -> TextEscapeUtils.escapeEntities("abc\uD888"));
	}

	/**
	 * Delta char: &#66560;
	 */
	@Test
	public void validSurrogatePairIsAccepted() {
		assertThat(TextEscapeUtils.escapeEntities("abc\uD801\uDC00a")).isEqualTo("abc&#66560;a");
	}

	@Test
	public void undefinedSurrogatePairIsIgnored() {
		assertThat(TextEscapeUtils.escapeEntities("abc\uD888\uDC00a")).isEqualTo("abca");
	}

}

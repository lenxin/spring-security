package org.springframework.security.web.access.intercept;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Luke Taylor
 *
 */
public class RequestKeyTests {

	@Test
	public void equalsWorksWithNullHttpMethod() {
		RequestKey key1 = new RequestKey("/someurl");
		RequestKey key2 = new RequestKey("/someurl");
		assertThat(key2).isEqualTo(key1);
		key1 = new RequestKey("/someurl", "GET");
		assertThat(key1.equals(key2)).isFalse();
		assertThat(key2.equals(key1)).isFalse();
	}

	@Test
	public void keysWithSameUrlAndHttpMethodAreEqual() {
		RequestKey key1 = new RequestKey("/someurl", "GET");
		RequestKey key2 = new RequestKey("/someurl", "GET");
		assertThat(key2).isEqualTo(key1);
	}

	@Test
	public void keysWithSameUrlAndDifferentHttpMethodAreNotEqual() {
		RequestKey key1 = new RequestKey("/someurl", "GET");
		RequestKey key2 = new RequestKey("/someurl", "POST");
		assertThat(key1.equals(key2)).isFalse();
		assertThat(key2.equals(key1)).isFalse();
	}

	@Test
	public void keysWithDifferentUrlsAreNotEquals() {
		RequestKey key1 = new RequestKey("/someurl", "GET");
		RequestKey key2 = new RequestKey("/anotherurl", "GET");
		assertThat(key1.equals(key2)).isFalse();
		assertThat(key2.equals(key1)).isFalse();
	}

	/**
	 */
	@Test
	public void keysWithNullUrlFailsAssertion() {
		assertThatIllegalArgumentException().isThrownBy(() -> new RequestKey(null, null))
				.withMessage("url cannot be null");
	}

}

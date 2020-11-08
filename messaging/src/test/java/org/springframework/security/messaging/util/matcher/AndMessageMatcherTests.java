package org.springframework.security.messaging.util.matcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.messaging.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class AndMessageMatcherTests {

	@Mock
	private MessageMatcher<Object> delegate;

	@Mock
	private MessageMatcher<Object> delegate2;

	@Mock
	private Message<Object> message;

	private MessageMatcher<Object> matcher;

	@Test
	public void constructorNullArray() {
		assertThatNullPointerException().isThrownBy(() -> new AndMessageMatcher<>((MessageMatcher<Object>[]) null));
	}

	@Test
	public void constructorArrayContainsNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AndMessageMatcher<>((MessageMatcher<Object>) null));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void constructorEmptyArray() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AndMessageMatcher<>(new MessageMatcher[0]));
	}

	@Test
	public void constructorNullList() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new AndMessageMatcher<>((List<MessageMatcher<Object>>) null));
	}

	@Test
	public void constructorListContainsNull() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> new AndMessageMatcher<>(Arrays.asList((MessageMatcher<Object>) null)));
	}

	@Test
	public void constructorEmptyList() {
		assertThatIllegalArgumentException().isThrownBy(() -> new AndMessageMatcher<>(Collections.emptyList()));
	}

	@Test
	public void matchesSingleTrue() {
		given(this.delegate.matches(this.message)).willReturn(true);
		this.matcher = new AndMessageMatcher<>(this.delegate);
		assertThat(this.matcher.matches(this.message)).isTrue();
	}

	@Test
	public void matchesMultiTrue() {
		given(this.delegate.matches(this.message)).willReturn(true);
		given(this.delegate2.matches(this.message)).willReturn(true);
		this.matcher = new AndMessageMatcher<>(this.delegate, this.delegate2);
		assertThat(this.matcher.matches(this.message)).isTrue();
	}

	@Test
	public void matchesSingleFalse() {
		given(this.delegate.matches(this.message)).willReturn(false);
		this.matcher = new AndMessageMatcher<>(this.delegate);
		assertThat(this.matcher.matches(this.message)).isFalse();
	}

	@Test
	public void matchesMultiBothFalse() {
		given(this.delegate.matches(this.message)).willReturn(false);
		this.matcher = new AndMessageMatcher<>(this.delegate, this.delegate2);
		assertThat(this.matcher.matches(this.message)).isFalse();
	}

	@Test
	public void matchesMultiSingleFalse() {
		given(this.delegate.matches(this.message)).willReturn(true);
		given(this.delegate2.matches(this.message)).willReturn(false);
		this.matcher = new AndMessageMatcher<>(this.delegate, this.delegate2);
		assertThat(this.matcher.matches(this.message)).isFalse();
	}

}

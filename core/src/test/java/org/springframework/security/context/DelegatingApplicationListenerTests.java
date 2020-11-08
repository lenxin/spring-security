package org.springframework.security.context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DelegatingApplicationListenerTests {

	@Mock
	SmartApplicationListener delegate;

	ApplicationEvent event;

	DelegatingApplicationListener listener;

	@Before
	public void setup() {
		this.event = new ApplicationEvent(this) {
		};
		this.listener = new DelegatingApplicationListener();
		this.listener.addListener(this.delegate);
	}

	@Test
	public void processEventNull() {
		this.listener.onApplicationEvent(null);
		verify(this.delegate, never()).onApplicationEvent(any(ApplicationEvent.class));
	}

	@Test
	public void processEventSuccess() {
		given(this.delegate.supportsEventType(this.event.getClass())).willReturn(true);
		given(this.delegate.supportsSourceType(this.event.getSource().getClass())).willReturn(true);
		this.listener.onApplicationEvent(this.event);
		verify(this.delegate).onApplicationEvent(this.event);
	}

	@Test
	public void processEventEventTypeNotSupported() {
		this.listener.onApplicationEvent(this.event);
		verify(this.delegate, never()).onApplicationEvent(any(ApplicationEvent.class));
	}

	@Test
	public void processEventSourceTypeNotSupported() {
		given(this.delegate.supportsEventType(this.event.getClass())).willReturn(true);
		this.listener.onApplicationEvent(this.event);
		verify(this.delegate, never()).onApplicationEvent(any(ApplicationEvent.class));
	}

	@Test
	public void addNull() {
		assertThatIllegalArgumentException().isThrownBy(() -> this.listener.addListener(null));
	}

}

package org.springframework.security.config.annotation.web.configurers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.security.web.debug.DebugFilter;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Tests to verify {@code EnableWebSecurity(debug)} functionality
 *
 * @author Rob Winch
 * @author Josh Cummings
 */
public class NamespaceDebugTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	MockMvc mvc;

	@Test
	public void requestWhenDebugSetToTrueThenLogsDebugInformation() throws Exception {
		Appender<ILoggingEvent> appender = mockAppenderFor("Spring Security Debugger");
		this.spring.register(DebugWebSecurity.class).autowire();
		this.mvc.perform(get("/"));
		assertThat(filterChainClass()).isEqualTo(DebugFilter.class);
		verify(appender, atLeastOnce()).doAppend(any(ILoggingEvent.class));
	}

	@Test
	public void requestWhenDebugSetToFalseThenDoesNotLogDebugInformation() throws Exception {
		Appender<ILoggingEvent> appender = mockAppenderFor("Spring Security Debugger");
		this.spring.register(NoDebugWebSecurity.class).autowire();
		this.mvc.perform(get("/"));
		assertThat(filterChainClass()).isNotEqualTo(DebugFilter.class);
		verify(appender, never()).doAppend(any(ILoggingEvent.class));
	}

	private Appender<ILoggingEvent> mockAppenderFor(String name) {
		Appender<ILoggingEvent> appender = mock(Appender.class);
		Logger logger = (Logger) LoggerFactory.getLogger(name);
		logger.setLevel(Level.DEBUG);
		logger.addAppender(appender);
		return appender;
	}

	private Class<?> filterChainClass() {
		return this.spring.getContext().getBean("springSecurityFilterChain").getClass();
	}

	@EnableWebSecurity(debug = true)
	static class DebugWebSecurity extends WebSecurityConfigurerAdapter {

	}

	@EnableWebSecurity
	static class NoDebugWebSecurity extends WebSecurityConfigurerAdapter {

	}

}

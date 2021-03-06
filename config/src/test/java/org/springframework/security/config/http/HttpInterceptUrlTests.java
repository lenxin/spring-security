package org.springframework.security.config.http;

import javax.servlet.Filter;

import org.junit.After;
import org.junit.Test;

import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HttpInterceptUrlTests {

	ConfigurableWebApplicationContext context;

	MockMvc mockMvc;

	@After
	public void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void interceptUrlWhenRequestMatcherRefThenWorks() throws Exception {
		loadConfig("interceptUrlWhenRequestMatcherRefThenWorks.xml");
		// @formatter:off
		this.mockMvc.perform(get("/foo"))
				.andExpect(status().isUnauthorized());
		this.mockMvc.perform(get("/FOO"))
				.andExpect(status().isUnauthorized());
		this.mockMvc.perform(get("/other"))
				.andExpect(status().isOk());
		// @formatter:on
	}

	private void loadConfig(String... configLocations) {
		for (int i = 0; i < configLocations.length; i++) {
			configLocations[i] = getClass().getName().replaceAll("\\.", "/") + "-" + configLocations[i];
		}
		XmlWebApplicationContext context = new XmlWebApplicationContext();
		context.setConfigLocations(configLocations);
		context.setServletContext(new MockServletContext());
		context.refresh();
		this.context = context;
		context.getAutowireCapableBeanFactory().autowireBean(this);
		Filter springSecurityFilterChain = context.getBean("springSecurityFilterChain", Filter.class);
		this.mockMvc = MockMvcBuilders.standaloneSetup(new FooController()).addFilters(springSecurityFilterChain)
				.build();
	}

	@RestController
	static class FooController {

		@GetMapping("/*")
		String foo() {
			return "foo";
		}

	}

}

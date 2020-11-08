package org.springframework.security.integration;

import org.junit.Test;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BasicAuthenticationTests extends AbstractWebServerIntegrationTests {

	@Test
	public void httpBasicWhenAuthenticationRequiredAndNotAuthenticatedThen401() throws Exception {
		MockMvc mockMvc = createMockMvc("classpath:/spring/http-security-basic.xml",
				"classpath:/spring/in-memory-provider.xml", "classpath:/spring/testapp-servlet.xml");
		// @formatter:off
		mockMvc.perform(get("/secure/index"))
				.andExpect(status().isUnauthorized());
		// @formatter:on
	}

	@Test
	public void httpBasicWhenProvidedThen200() throws Exception {
		MockMvc mockMvc = createMockMvc("classpath:/spring/http-security-basic.xml",
				"classpath:/spring/in-memory-provider.xml", "classpath:/spring/testapp-servlet.xml");
		// @formatter:off
		MockHttpServletRequestBuilder request = get("/secure/index")
				.with(httpBasic("johnc", "johncspassword"));
		// @formatter:on
		mockMvc.perform(request).andExpect(status().isOk());
	}

}

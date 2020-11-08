package org.springframework.security.config.authentication;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.test.SpringTestRule;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Rob Winch
 * @since 5.0
 */
public class PasswordEncoderParserTests {

	@Rule
	public final SpringTestRule spring = new SpringTestRule();

	@Autowired
	MockMvc mockMvc;

	@Test
	public void passwordEncoderDefaultsToDelegatingPasswordEncoder() throws Exception {
		this.spring.configLocations(
				"classpath:org/springframework/security/config/authentication/PasswordEncoderParserTests-default.xml")
				.mockMvcAfterSpringSecurityOk().autowire();
		// @formatter:off
		this.mockMvc.perform(get("/").with(httpBasic("user", "password")))
				.andExpect(status().isOk());
		// @formatter:on
	}

	@Test
	public void passwordEncoderDefaultsToPasswordEncoderBean() throws Exception {
		this.spring.configLocations(
				"classpath:org/springframework/security/config/authentication/PasswordEncoderParserTests-bean.xml")
				.mockMvcAfterSpringSecurityOk().autowire();
		// @formatter:off
		this.mockMvc.perform(get("/").with(httpBasic("user", "password")))
				.andExpect(status().isOk());
		// @formatter:on
	}

}

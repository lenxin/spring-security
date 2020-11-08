package org.springframework.security.samples.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author Rob Winch
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class SecurityConfigTests {
	@Configuration
	@ComponentScan(basePackages = "org.springframework.security.samples.config")
	public static class Config {
	}

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	@Test
	public void securityConfigurationLoads() {
	}
}

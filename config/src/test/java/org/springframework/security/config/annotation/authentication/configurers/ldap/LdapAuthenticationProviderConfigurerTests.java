package org.springframework.security.config.annotation.authentication.configurers.ldap;

import org.junit.Before;
import org.junit.Test;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;

import static org.assertj.core.api.Assertions.assertThat;

public class LdapAuthenticationProviderConfigurerTests {

	private LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> configurer;

	@Before
	public void setUp() {
		this.configurer = new LdapAuthenticationProviderConfigurer<>();
	}

	// SEC-2557
	@Test
	public void getAuthoritiesMapper() throws Exception {
		assertThat(this.configurer.getAuthoritiesMapper()).isInstanceOf(SimpleAuthorityMapper.class);
		this.configurer.authoritiesMapper(new NullAuthoritiesMapper());
		assertThat(this.configurer.getAuthoritiesMapper()).isInstanceOf(NullAuthoritiesMapper.class);
	}

}

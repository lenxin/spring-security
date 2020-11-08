package org.springframework.security.jackson2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * @author Jitenra Singh
 * @since 4.2
 */
public abstract class AbstractMixinTests {

	protected ObjectMapper mapper;

	@Before
	public void setup() {
		this.mapper = new ObjectMapper();
		ClassLoader loader = getClass().getClassLoader();
		this.mapper.registerModules(SecurityJackson2Modules.getModules(loader));
	}

	User createDefaultUser() {
		return createUser("admin", "1234", "ROLE_USER");
	}

	User createUser(String username, String password, String authority) {
		return new User(username, password, AuthorityUtils.createAuthorityList(authority));
	}

}

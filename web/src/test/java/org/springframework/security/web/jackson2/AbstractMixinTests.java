package org.springframework.security.web.jackson2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;

import org.springframework.security.jackson2.SecurityJackson2Modules;

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

}

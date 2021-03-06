package org.springframework.security.web;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * Tests {@link PortMapperImpl}.
 *
 * @author Ben Alex
 */
public class PortMapperImplTests {

	@Test
	public void testDefaultMappingsAreKnown() {
		PortMapperImpl portMapper = new PortMapperImpl();
		assertThat(portMapper.lookupHttpPort(443)).isEqualTo(Integer.valueOf(80));
		assertThat(Integer.valueOf(8080)).isEqualTo(portMapper.lookupHttpPort(8443));
		assertThat(Integer.valueOf(443)).isEqualTo(portMapper.lookupHttpsPort(80));
		assertThat(Integer.valueOf(8443)).isEqualTo(portMapper.lookupHttpsPort(8080));
	}

	@Test
	public void testDetectsEmptyMap() {
		PortMapperImpl portMapper = new PortMapperImpl();
		assertThatIllegalArgumentException().isThrownBy(() -> portMapper.setPortMappings(new HashMap<>()));
	}

	@Test
	public void testDetectsNullMap() {
		PortMapperImpl portMapper = new PortMapperImpl();
		assertThatIllegalArgumentException().isThrownBy(() -> portMapper.setPortMappings(null));
	}

	@Test
	public void testGetTranslatedPortMappings() {
		PortMapperImpl portMapper = new PortMapperImpl();
		assertThat(portMapper.getTranslatedPortMappings()).hasSize(2);
	}

	@Test
	public void testRejectsOutOfRangeMappings() {
		PortMapperImpl portMapper = new PortMapperImpl();
		Map<String, String> map = new HashMap<>();
		map.put("79", "80559");
		assertThatIllegalArgumentException().isThrownBy(() -> portMapper.setPortMappings(map));
	}

	@Test
	public void testReturnsNullIfHttpPortCannotBeFound() {
		PortMapperImpl portMapper = new PortMapperImpl();
		assertThat(portMapper.lookupHttpPort(Integer.valueOf("34343")) == null).isTrue();
	}

	@Test
	public void testSupportsCustomMappings() {
		PortMapperImpl portMapper = new PortMapperImpl();
		Map<String, String> map = new HashMap<>();
		map.put("79", "442");
		portMapper.setPortMappings(map);
		assertThat(portMapper.lookupHttpPort(442)).isEqualTo(Integer.valueOf(79));
		assertThat(Integer.valueOf(442)).isEqualTo(portMapper.lookupHttpsPort(79));
	}

}

package org.springframework.security.core.authority.mapping;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @author Luke Taylor
 */
public class SimpleAuthoritiesMapperTests {

	@Test
	public void rejectsInvalidCaseConversionFlags() {
		SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
		mapper.setConvertToLowerCase(true);
		mapper.setConvertToUpperCase(true);
		assertThatIllegalArgumentException().isThrownBy(mapper::afterPropertiesSet);
	}

	@Test
	public void defaultPrefixIsCorrectlyApplied() {
		SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
		Set<String> mapped = AuthorityUtils
				.authorityListToSet(mapper.mapAuthorities(AuthorityUtils.createAuthorityList("AaA", "ROLE_bbb")));
		assertThat(mapped.contains("ROLE_AaA")).isTrue();
		assertThat(mapped.contains("ROLE_bbb")).isTrue();
	}

	@Test
	public void caseIsConvertedCorrectly() {
		SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
		mapper.setPrefix("");
		List<GrantedAuthority> toMap = AuthorityUtils.createAuthorityList("AaA", "Bbb");
		Set<String> mapped = AuthorityUtils.authorityListToSet(mapper.mapAuthorities(toMap));
		assertThat(mapped).hasSize(2);
		assertThat(mapped.contains("AaA")).isTrue();
		assertThat(mapped.contains("Bbb")).isTrue();
		mapper.setConvertToLowerCase(true);
		mapped = AuthorityUtils.authorityListToSet(mapper.mapAuthorities(toMap));
		assertThat(mapped).hasSize(2);
		assertThat(mapped.contains("aaa")).isTrue();
		assertThat(mapped.contains("bbb")).isTrue();
		mapper.setConvertToLowerCase(false);
		mapper.setConvertToUpperCase(true);
		mapped = AuthorityUtils.authorityListToSet(mapper.mapAuthorities(toMap));
		assertThat(mapped).hasSize(2);
		assertThat(mapped.contains("AAA")).isTrue();
		assertThat(mapped.contains("BBB")).isTrue();
	}

	@Test
	public void duplicatesAreRemoved() {
		SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
		mapper.setConvertToUpperCase(true);
		Set<String> mapped = AuthorityUtils
				.authorityListToSet(mapper.mapAuthorities(AuthorityUtils.createAuthorityList("AaA", "AAA")));
		assertThat(mapped).hasSize(1);
	}

	@Test
	public void defaultAuthorityIsAssignedIfSet() {
		SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
		mapper.setDefaultAuthority("ROLE_USER");
		Set<String> mapped = AuthorityUtils.authorityListToSet(mapper.mapAuthorities(AuthorityUtils.NO_AUTHORITIES));
		assertThat(mapped).hasSize(1);
		assertThat(mapped.contains("ROLE_USER")).isTrue();
	}

}

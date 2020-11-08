package org.springframework.security.acls.sid;

import java.util.List;

import org.junit.Test;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.domain.SidRetrievalStrategyImpl;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link SidRetrievalStrategyImpl}
 *
 * @author Andrei Stefan
 * @author Luke Taylor
 */
@SuppressWarnings("unchecked")
public class SidRetrievalStrategyTests {

	Authentication authentication = new TestingAuthenticationToken("scott", "password", "A", "B", "C");

	@Test
	public void correctSidsAreRetrieved() {
		SidRetrievalStrategy retrStrategy = new SidRetrievalStrategyImpl();
		List<Sid> sids = retrStrategy.getSids(this.authentication);
		assertThat(sids).isNotNull();
		assertThat(sids).hasSize(4);
		assertThat(sids.get(0)).isNotNull();
		assertThat(sids.get(0) instanceof PrincipalSid).isTrue();
		for (int i = 1; i < sids.size(); i++) {
			assertThat(sids.get(i) instanceof GrantedAuthoritySid).isTrue();
		}
		assertThat(((PrincipalSid) sids.get(0)).getPrincipal()).isEqualTo("scott");
		assertThat(((GrantedAuthoritySid) sids.get(1)).getGrantedAuthority()).isEqualTo("A");
		assertThat(((GrantedAuthoritySid) sids.get(2)).getGrantedAuthority()).isEqualTo("B");
		assertThat(((GrantedAuthoritySid) sids.get(3)).getGrantedAuthority()).isEqualTo("C");
	}

	@Test
	public void roleHierarchyIsUsedWhenSet() {
		RoleHierarchy rh = mock(RoleHierarchy.class);
		List rhAuthorities = AuthorityUtils.createAuthorityList("D");
		given(rh.getReachableGrantedAuthorities(anyCollection())).willReturn(rhAuthorities);
		SidRetrievalStrategy strat = new SidRetrievalStrategyImpl(rh);
		List<Sid> sids = strat.getSids(this.authentication);
		assertThat(sids).hasSize(2);
		assertThat(sids.get(0)).isNotNull();
		assertThat(sids.get(0) instanceof PrincipalSid).isTrue();
		assertThat(((GrantedAuthoritySid) sids.get(1)).getGrantedAuthority()).isEqualTo("D");
	}

}

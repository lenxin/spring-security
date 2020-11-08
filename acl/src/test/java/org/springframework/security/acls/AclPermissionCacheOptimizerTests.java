package org.springframework.security.acls;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * @author Luke Taylor
 */
@SuppressWarnings({ "unchecked" })
public class AclPermissionCacheOptimizerTests {

	@Test
	public void eagerlyLoadsRequiredAcls() {
		AclService service = mock(AclService.class);
		AclPermissionCacheOptimizer pco = new AclPermissionCacheOptimizer(service);
		ObjectIdentityRetrievalStrategy oidStrat = mock(ObjectIdentityRetrievalStrategy.class);
		SidRetrievalStrategy sidStrat = mock(SidRetrievalStrategy.class);
		pco.setObjectIdentityRetrievalStrategy(oidStrat);
		pco.setSidRetrievalStrategy(sidStrat);
		Object[] dos = { new Object(), null, new Object() };
		ObjectIdentity[] oids = { new ObjectIdentityImpl("A", "1"), new ObjectIdentityImpl("A", "2") };
		given(oidStrat.getObjectIdentity(dos[0])).willReturn(oids[0]);
		given(oidStrat.getObjectIdentity(dos[2])).willReturn(oids[1]);
		pco.cachePermissionsFor(mock(Authentication.class), Arrays.asList(dos));
		// AclService should be invoked with the list of required Oids
		verify(service).readAclsById(eq(Arrays.asList(oids)), any(List.class));
	}

	@Test
	public void ignoresEmptyCollection() {
		AclService service = mock(AclService.class);
		AclPermissionCacheOptimizer pco = new AclPermissionCacheOptimizer(service);
		ObjectIdentityRetrievalStrategy oids = mock(ObjectIdentityRetrievalStrategy.class);
		SidRetrievalStrategy sids = mock(SidRetrievalStrategy.class);
		pco.setObjectIdentityRetrievalStrategy(oids);
		pco.setSidRetrievalStrategy(sids);
		pco.cachePermissionsFor(mock(Authentication.class), Collections.emptyList());
		verifyZeroInteractions(service, sids, oids);
	}

}

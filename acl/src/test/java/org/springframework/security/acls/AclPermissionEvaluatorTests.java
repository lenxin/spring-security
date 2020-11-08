package org.springframework.security.acls;

import java.util.Locale;

import org.junit.Test;

import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author Luke Taylor
 * @since 3.0
 */
public class AclPermissionEvaluatorTests {

	@Test
	public void hasPermissionReturnsTrueIfAclGrantsPermission() {
		AclService service = mock(AclService.class);
		AclPermissionEvaluator pe = new AclPermissionEvaluator(service);
		ObjectIdentity oid = mock(ObjectIdentity.class);
		ObjectIdentityRetrievalStrategy oidStrategy = mock(ObjectIdentityRetrievalStrategy.class);
		given(oidStrategy.getObjectIdentity(any(Object.class))).willReturn(oid);
		pe.setObjectIdentityRetrievalStrategy(oidStrategy);
		pe.setSidRetrievalStrategy(mock(SidRetrievalStrategy.class));
		Acl acl = mock(Acl.class);
		given(service.readAclById(any(ObjectIdentity.class), anyList())).willReturn(acl);
		given(acl.isGranted(anyList(), anyList(), eq(false))).willReturn(true);
		assertThat(pe.hasPermission(mock(Authentication.class), new Object(), "READ")).isTrue();
	}

	@Test
	public void resolvePermissionNonEnglishLocale() {
		Locale systemLocale = Locale.getDefault();
		Locale.setDefault(new Locale("tr"));
		AclService service = mock(AclService.class);
		AclPermissionEvaluator pe = new AclPermissionEvaluator(service);
		ObjectIdentity oid = mock(ObjectIdentity.class);
		ObjectIdentityRetrievalStrategy oidStrategy = mock(ObjectIdentityRetrievalStrategy.class);
		given(oidStrategy.getObjectIdentity(any(Object.class))).willReturn(oid);
		pe.setObjectIdentityRetrievalStrategy(oidStrategy);
		pe.setSidRetrievalStrategy(mock(SidRetrievalStrategy.class));
		Acl acl = mock(Acl.class);
		given(service.readAclById(any(ObjectIdentity.class), anyList())).willReturn(acl);
		given(acl.isGranted(anyList(), anyList(), eq(false))).willReturn(true);
		assertThat(pe.hasPermission(mock(Authentication.class), new Object(), "write")).isTrue();
		Locale.setDefault(systemLocale);
	}

}

package org.springframework.security.acls.afterinvocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Luke Taylor
 */
@SuppressWarnings({ "unchecked" })
public class AclEntryAfterInvocationCollectionFilteringProviderTests {

	@Test
	public void objectsAreRemovedIfPermissionDenied() {
		AclService service = mock(AclService.class);
		Acl acl = mock(Acl.class);
		given(acl.isGranted(any(), any(), anyBoolean())).willReturn(false);
		given(service.readAclById(any(), any())).willReturn(acl);
		AclEntryAfterInvocationCollectionFilteringProvider provider = new AclEntryAfterInvocationCollectionFilteringProvider(
				service, Arrays.asList(mock(Permission.class)));
		provider.setObjectIdentityRetrievalStrategy(mock(ObjectIdentityRetrievalStrategy.class));
		provider.setProcessDomainObjectClass(Object.class);
		provider.setSidRetrievalStrategy(mock(SidRetrievalStrategy.class));
		Object returned = provider.decide(mock(Authentication.class), new Object(),
				SecurityConfig.createList("AFTER_ACL_COLLECTION_READ"),
				new ArrayList(Arrays.asList(new Object(), new Object())));
		assertThat(returned).isInstanceOf(List.class);
		assertThat(((List) returned)).isEmpty();
		returned = provider.decide(mock(Authentication.class), new Object(),
				SecurityConfig.createList("UNSUPPORTED", "AFTER_ACL_COLLECTION_READ"),
				new Object[] { new Object(), new Object() });
		assertThat(returned instanceof Object[]).isTrue();
		assertThat(((Object[]) returned).length == 0).isTrue();
	}

	@Test
	public void accessIsGrantedIfNoAttributesDefined() {
		AclEntryAfterInvocationCollectionFilteringProvider provider = new AclEntryAfterInvocationCollectionFilteringProvider(
				mock(AclService.class), Arrays.asList(mock(Permission.class)));
		Object returned = new Object();
		assertThat(returned).isSameAs(provider.decide(mock(Authentication.class), new Object(),
				Collections.<ConfigAttribute>emptyList(), returned));
	}

	@Test
	public void nullReturnObjectIsIgnored() {
		AclService service = mock(AclService.class);
		AclEntryAfterInvocationCollectionFilteringProvider provider = new AclEntryAfterInvocationCollectionFilteringProvider(
				service, Arrays.asList(mock(Permission.class)));
		assertThat(provider.decide(mock(Authentication.class), new Object(),
				SecurityConfig.createList("AFTER_ACL_COLLECTION_READ"), null)).isNull();
		verify(service, never()).readAclById(any(ObjectIdentity.class), any(List.class));
	}

}

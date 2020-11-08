package org.springframework.security.acls.domain;

import org.junit.Test;

import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ObjectIdentityRetrievalStrategyImpl}
 *
 * @author Andrei Stefan
 */
public class ObjectIdentityRetrievalStrategyImplTests {

	@Test
	public void testObjectIdentityCreation() {
		MockIdDomainObject domain = new MockIdDomainObject();
		domain.setId(1);
		ObjectIdentityRetrievalStrategy retStrategy = new ObjectIdentityRetrievalStrategyImpl();
		ObjectIdentity identity = retStrategy.getObjectIdentity(domain);
		assertThat(identity).isNotNull();
		assertThat(new ObjectIdentityImpl(domain)).isEqualTo(identity);
	}

	@SuppressWarnings("unused")
	private class MockIdDomainObject {

		private Object id;

		public Object getId() {
			return this.id;
		}

		public void setId(Object id) {
			this.id = id;
		}

	}

}

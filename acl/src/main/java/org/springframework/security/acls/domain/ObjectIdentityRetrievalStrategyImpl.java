package org.springframework.security.acls.domain;

import java.io.Serializable;

import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.ObjectIdentityGenerator;
import org.springframework.security.acls.model.ObjectIdentityRetrievalStrategy;

/**
 * Basic implementation of {@link ObjectIdentityRetrievalStrategy} and
 * <tt>ObjectIdentityGenerator</tt> that uses the constructors of
 * {@link ObjectIdentityImpl} to create the {@link ObjectIdentity}.
 *
 * @author Ben Alex
 */
public class ObjectIdentityRetrievalStrategyImpl implements ObjectIdentityRetrievalStrategy, ObjectIdentityGenerator {

	@Override
	public ObjectIdentity getObjectIdentity(Object domainObject) {
		return new ObjectIdentityImpl(domainObject);
	}

	@Override
	public ObjectIdentity createObjectIdentity(Serializable id, String type) {
		return new ObjectIdentityImpl(type, id);
	}

}

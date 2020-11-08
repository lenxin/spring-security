package org.springframework.security.acls.model;

/**
 * Strategy interface that provides the ability to determine which {@link ObjectIdentity}
 * will be returned for a particular domain object
 *
 * @author Ben Alex
 */
public interface ObjectIdentityRetrievalStrategy {

	ObjectIdentity getObjectIdentity(Object domainObject);

}

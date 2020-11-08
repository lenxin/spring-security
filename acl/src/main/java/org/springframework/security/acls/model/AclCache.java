package org.springframework.security.acls.model;

import java.io.Serializable;

import org.springframework.security.acls.jdbc.JdbcAclService;

/**
 * A caching layer for {@link JdbcAclService}.
 *
 * @author Ben Alex
 */
public interface AclCache {

	void evictFromCache(Serializable pk);

	void evictFromCache(ObjectIdentity objectIdentity);

	MutableAcl getFromCache(ObjectIdentity objectIdentity);

	MutableAcl getFromCache(Serializable pk);

	void putInCache(MutableAcl acl);

	void clearCache();

}

package org.springframework.security.remoting.dns;

import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * This is used in JndiDnsResolver to get an InitialDirContext for DNS queries.
 *
 * @author Mike Wiesner
 * @since 3.0
 * @see InitialDirContext
 * @see DirContext
 * @see JndiDnsResolver
 */
public interface InitialContextFactory {

	/**
	 * Must return a DirContext which can be used for DNS queries
	 * @return JNDI DirContext
	 */
	DirContext getCtx();

}

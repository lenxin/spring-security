package org.springframework.security.cas.authentication;

/**
 * Implementation of @link {@link StatelessTicketCache} that has no backing cache. Useful
 * in instances where storing of tickets for stateless session management is not required.
 * <p>
 * This is the default StatelessTicketCache of the @link {@link CasAuthenticationProvider}
 * to eliminate the unnecessary dependency on EhCache that applications have even if they
 * are not using the stateless session management.
 *
 * @author Scott Battaglia
 * @see CasAuthenticationProvider
 */
public final class NullStatelessTicketCache implements StatelessTicketCache {

	/**
	 * @return null since we are not storing any tickets.
	 */
	@Override
	public CasAuthenticationToken getByTicketId(final String serviceTicket) {
		return null;
	}

	/**
	 * This is a no-op since we are not storing tickets.
	 */
	@Override
	public void putTicketInCache(final CasAuthenticationToken token) {
		// nothing to do
	}

	/**
	 * This is a no-op since we are not storing tickets.
	 */
	@Override
	public void removeTicketFromCache(final CasAuthenticationToken token) {
		// nothing to do
	}

	/**
	 * This is a no-op since we are not storing tickets.
	 */
	@Override
	public void removeTicketFromCache(final String serviceTicket) {
		// nothing to do
	}

}

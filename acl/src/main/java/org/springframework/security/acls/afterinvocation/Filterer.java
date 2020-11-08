package org.springframework.security.acls.afterinvocation;

import java.util.Iterator;

/**
 * Filterer strategy interface.
 *
 * @author Ben Alex
 * @author Paulo Neves
 */
interface Filterer<T> extends Iterable<T> {

	/**
	 * Gets the filtered collection or array.
	 * @return the filtered collection or array
	 */
	Object getFilteredObject();

	/**
	 * Returns an iterator over the filtered collection or array.
	 * @return an Iterator
	 */
	@Override
	Iterator<T> iterator();

	/**
	 * Removes the given object from the resulting list.
	 * @param object the object to be removed
	 */
	void remove(T object);

}

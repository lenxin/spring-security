package org.springframework.security.acls.afterinvocation;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.log.LogMessage;

/**
 * A filter used to filter arrays.
 *
 * @author Ben Alex
 * @author Paulo Neves
 */
class ArrayFilterer<T> implements Filterer<T> {

	protected static final Log logger = LogFactory.getLog(ArrayFilterer.class);

	private final Set<T> removeList;

	private final T[] list;

	ArrayFilterer(T[] list) {
		this.list = list;
		// Collect the removed objects to a HashSet so that
		// it is fast to lookup them when a filtered array
		// is constructed.
		this.removeList = new HashSet<>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public T[] getFilteredObject() {
		// Recreate an array of same type and filter the removed objects.
		int originalSize = this.list.length;
		int sizeOfResultingList = originalSize - this.removeList.size();
		T[] filtered = (T[]) Array.newInstance(this.list.getClass().getComponentType(), sizeOfResultingList);
		for (int i = 0, j = 0; i < this.list.length; i++) {
			T object = this.list[i];
			if (!this.removeList.contains(object)) {
				filtered[j] = object;
				j++;
			}
		}
		logger.debug(LogMessage.of(() -> "Original array contained " + originalSize + " elements; now contains "
				+ sizeOfResultingList + " elements"));
		return filtered;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayFiltererIterator();
	}

	@Override
	public void remove(T object) {
		this.removeList.add(object);
	}

	/**
	 * Iterator for {@link ArrayFilterer} elements.
	 */
	private class ArrayFiltererIterator implements Iterator<T> {

		private int index = 0;

		@Override
		public boolean hasNext() {
			return this.index < ArrayFilterer.this.list.length;
		}

		@Override
		public T next() {
			if (hasNext()) {
				return ArrayFilterer.this.list[this.index++];
			}
			throw new NoSuchElementException();
		}

	}

}

package com.bfds.saec.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionUtils {

	/**
	 * @param <T>
	 * @param list {@link List} any list object containing zero or more elements,can't be null.
	 * @param n {@link int} any int value,should be zero or greater than zero.
	 * @return {@link List} Which will contains the first n-elements added to list.
	 */
	public static final <T> List<T> firstN(List<T> list, int n) {
		List<T> ret = new ArrayList<T>();
		for (T t : list) {
			if (n-- == 0) {
				break;
			}
			ret.add(t);
		}
		return ret;
	}

	/**
	 * @param coll {@link Collection} any collection object containing zero or more elements,can't be null.
	 * @return {@link Number} the maximum value available in the collection.
	 */
	public static Number max(Collection<? extends Number> coll) {
		Iterator<? extends Number> i = coll.iterator();
		Number candidate = i.next();
		while (i.hasNext()) {
			Number next = i.next();
			if (next.doubleValue() - candidate.doubleValue() > 0)
				candidate = next;
		}
		return candidate;
	}

}

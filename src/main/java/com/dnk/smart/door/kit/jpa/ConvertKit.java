package com.dnk.smart.door.kit.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConvertKit {

	public static <T> T[] toArray(Collection<T> collection) {
		return collection.toArray((T[]) new Object[collection.size()]);
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);

		System.out.println(toArray(list));

	}
}

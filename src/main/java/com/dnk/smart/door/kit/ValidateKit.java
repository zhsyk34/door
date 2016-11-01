package com.dnk.smart.door.kit;

import java.util.Collection;

public class ValidateKit {

	public static boolean empty(String str) {
		return str == null || str.isEmpty();
	}

	public static boolean notEmpty(String str) {
		return !empty(str);
	}

	public static boolean empty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	}

	public static boolean notEmpty(Collection<?> collection) {
		return !empty(collection);
	}

	public static boolean empty(Object[] arrays) {
		return arrays == null || arrays.length == 0;
	}

	public static boolean notEmpty(Object[] arrays) {
		return !empty(arrays);
	}

	public static boolean invalid(Long key) {
		return key == null || key <= 0;
	}

	public static boolean valid(Long key) {
		return !invalid(key);
	}
}

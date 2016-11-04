package com.dnk.smart.door.kit.jpa;

import lombok.Getter;

@Getter
public class Page {
	private final int number;
	private final int size;

	public Page(int number, int size) {
		this.number = number;
		this.size = size;
	}

	public static Page of(int number, int size) {
		return number > 0 && size > 0 ? new Page(number, size) : null;
	}

	public int offset() {
		return (number - 1) * size;
	}
}
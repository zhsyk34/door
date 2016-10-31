package com.dnk.smart.door.kit;

import lombok.Getter;

@Getter
public class Page {
	private final int pageNo;
	private final int pageSize;

	private Page(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public static Page of(int pageNo, int pageSize) {
		return pageNo > 0 && pageSize > 0 ? new Page(pageNo, pageSize) : null;
	}

	public int start() {
		return (pageNo - 1) * pageSize;
	}
}
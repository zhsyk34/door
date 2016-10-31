package com.dnk.smart.door.service;

import com.dnk.smart.door.dao.impl.CommonDaoTest;
import org.junit.Test;

import javax.annotation.Resource;

public class BaseServiceTest extends CommonDaoTest {

	@Resource
	private BaseService baseService;

	@Test
	public void base() throws Exception {
		baseService.init();
	}
}
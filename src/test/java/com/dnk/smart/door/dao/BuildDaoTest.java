package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class BuildDaoTest extends CommonDaoTest {
	@Test
	public void find() throws Exception {
		Build build = buildDao.find("build77");
		System.out.println(build);
	}

	@Test
	public void count() throws Exception {
		System.out.println(buildDao.count(Arrays.asList(1L, 3L), "b"));
	}

	@Test
	public void findList() throws Exception {
		List<Build> list = buildDao.findList(null, "a", null, null);
		list.forEach(this::print);
	}

	@Test
	public void findList2() throws Exception {
		List<Build> list = buildDao.findList(Arrays.asList(1L, 3L), "b", Page.of(1, 3), Sort.of("createTime", Sort.Rule.DESC));
		list.forEach(this::print);
	}

}
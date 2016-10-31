package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.BuildDao;

import javax.annotation.Resource;

public class BuildDaoTest extends CommonDaoTest {

	@Resource
	private BuildDao buildDao;

	/*@Test
	public void save() throws Exception {
		Build Build = new Build();
		Build.setName("p1");//setCreateTime(LocalDateTime.of(2011, 11, 11, 18, 15, 23));

		BuildDao.save(Build);
	}

	@Test
	public void find() throws Exception {
		Build Build = BuildDao.findById(2L);
		print(Build);
	}

	@Test
	public void update() throws Exception {
		Build Build = BuildDao.findById(6L);
		Build.setName("abc" + Math.random());
		Build.setUpdateTime(LocalDateTime.now());
		Build.setBuilds(null);//无效
		BuildDao.update(Build);
	}

	//update 直接 update,merge 会先 select
	@Test
	public void merge1() throws Exception {
		Build Build = BuildDao.findById(6L);
		Build.setName("abc");
		Build.setUpdateTime(LocalDateTime.now());
		Build.setBuilds(null);//无效
		BuildDao.merge(Build);
	}

	@Test
	public void merge2() throws Exception {
		Build Build = new Build();
		Build.setId(6L);
		Build.setName("one");//setCreateTime(LocalDateTime.of(2011, 11, 11, 18, 15, 23));

		Set<Build> builds = new HashSet<>();
		for (int i = 0; i < 3; i++) {
			builds.add(new Build().setName("b" + i).setBuild(Build));
		}
		Build.setBuilds(builds);//不会删除原有数据
		BuildDao.merge(Build);
	}

	@Test
	public void count() throws Exception {
		System.out.println(BuildDao.count());
	}

	@Test
	public void findList() throws Exception {
		List<Build> list = BuildDao.findList();
		print(list);
	}*/

}
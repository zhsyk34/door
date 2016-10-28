package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.ProjectDao;
import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.entity.Project;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectDaoTest extends CommonDaoTest {

	@Resource
	private ProjectDao projectDao;

	@Test
	public void save() throws Exception {
		Project project = new Project();
		project.setName("p1");//setCreateTime(LocalDateTime.of(2011, 11, 11, 18, 15, 23));

		Set<Build> builds = new HashSet<>();
		for (int i = 0; i < 3; i++) {
			builds.add(new Build().setName("b" + i).setProject(project));
		}
		project.setBuilds(builds);
		projectDao.save(project);
	}

	@Test
	public void find() throws Exception {
		Project project = projectDao.findById(2L);
		print(project);
	}

	@Test
	public void update() throws Exception {
		Project project = projectDao.findById(6L);
		project.setName("abc" + Math.random());
		project.setUpdateTime(LocalDateTime.now());
		project.setBuilds(null);//无效
		projectDao.update(project);
	}

	//update 直接 update,merge 会先 select
	@Test
	public void merge1() throws Exception {
		Project project = projectDao.findById(6L);
		project.setName("abc");
		project.setUpdateTime(LocalDateTime.now());
		project.setBuilds(null);//无效
		projectDao.merge(project);
	}

	@Test
	public void merge2() throws Exception {
		Project project = new Project();
		project.setId(6L);
		project.setName("one");//setCreateTime(LocalDateTime.of(2011, 11, 11, 18, 15, 23));

		Set<Build> builds = new HashSet<>();
		for (int i = 0; i < 3; i++) {
			builds.add(new Build().setName("b" + i).setProject(project));
		}
		project.setBuilds(builds);//不会删除原有数据
		projectDao.merge(project);
	}

	@Test
	public void count() throws Exception {
		System.out.println(projectDao.count());
	}

	@Test
	public void findList() throws Exception {
		List<Project> list = projectDao.findList();
		print(list);
	}

}
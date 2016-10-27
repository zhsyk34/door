package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.ProjectDao;
import com.dnk.smart.door.entity.Project;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring-dao.xml", "classpath:/spring/spring-service.xml"})
public class ProjectDaoTest {

	@Resource
	private ProjectDao projectDao;

	@Test
	public void save() throws Exception {
		Project project = new Project();
		project.setName("p1");//setCreateTime(LocalDateTime.of(2011, 11, 11, 18, 15, 23));
		projectDao.save(project);
	}

	@Test
	public void find() throws Exception {
		Project project = projectDao.findById(1L);
		System.out.println(project);
//		print(project);
	}

	private void print(Object object) {
		System.out.println(new Gson().toJson(object));
	}
}
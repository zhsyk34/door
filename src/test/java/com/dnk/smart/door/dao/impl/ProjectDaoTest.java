package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.ProjectDao;
import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.entity.Project;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring-dao.xml", "classpath:/spring/spring-service.xml"})
public class ProjectDaoTest {

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

    private void print(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        System.out.println(mapper.writeValueAsString(object));
//        System.out.println(new Gson().toJson(object));
    }
}
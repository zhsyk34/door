package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring/spring-dao.xml", "classpath:/spring/spring-service.xml"})
public class CommonDaoTest {

	@Resource
	protected UserDao userDao;
	@Resource
	protected BuildDao buildDao;
	@Resource
	protected UnitDao unitDao;
	@Resource
	protected HouseDao houseDao;
	@Resource
	protected UserBuildDao userBuildDao;

	protected void print(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
		try {
			System.out.println(mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void init() throws Exception {

	}

}

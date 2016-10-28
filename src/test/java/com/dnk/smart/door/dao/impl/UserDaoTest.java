package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.UserDao;
import com.dnk.smart.door.entity.User;
import com.dnk.smart.door.entity.dict.Gender;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserDaoTest extends CommonDaoTest {

	@Resource
	private UserDao userDao;

	@Test
	public void save() throws Exception {
		User user = new User();
		user.setName("gm");
		userDao.save(user);

	}

	@Test
	public void saves() throws Exception {
		for (int i = 1; i < 5; i++) {
			User user = new User();
			user.setName("player" + i);

			Gender gender = Gender.of(1 + (new Random().nextInt(100)) % 3);
//			System.out.println(gender);
			user.setGender(gender);
			userDao.save(user);
		}
	}

	@Test
	public void find() throws Exception {
		User user = userDao.findById(1L);
		print(user);
	}

	@Test
	public void findList() throws Exception {
		List<User> list = userDao.findList();
		print(list);
		System.out.println(list.size());
	}

	@Test
	public void update() throws Exception {
		User user = userDao.findById(1L);
		user.setName("player");
		user.setUpdateTime(LocalDateTime.now());

		userDao.update(user);
	}

	@Test
	public void update2() throws Exception {
		User user = new User();
		user.setUid(1L);
		user.setName("player2");
		user.setUpdateTime(LocalDateTime.now());

		userDao.update(user);
	}

	@Test
	public void update3() throws Exception {
		User user = new User();
		//user.setId(3L);//没有id或者不存在指定id的对象 都会报错
		user.setName("player2");
		user.setUpdateTime(LocalDateTime.now());

		userDao.update(user);
	}

	@Test
	public void merge() throws Exception {
		User user = new User();
		user.setUid(1L);//update3()的情况均为Insert
		user.setName("super");
		user.setUpdateTime(LocalDateTime.now());

		userDao.merge(user);
	}

	@Test
	public void delete() throws Exception {
		System.out.println(userDao.deleteById(1L));
	}

	@Test
	public void delete1() throws Exception {
		User user = new User().setUid(2L);
		userDao.deleteByEntity(user);
	}

	@Test
	public void delete2() throws Exception {
		System.out.println(userDao.deleteByIds(Arrays.asList(new Long[]{1L, 2L})));
	}

	@Test
	public void delete3() throws Exception {
		System.out.println(userDao.deleteByIds(new Long[]{1L, 3L, 2L}));
	}

	@Test
	public void delete4() throws Exception {
		List<User> list = userDao.findList();
		userDao.deleteByEntities(list);
	}

}
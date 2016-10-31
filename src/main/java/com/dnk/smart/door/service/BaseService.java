package com.dnk.smart.door.service;

import com.dnk.smart.door.dao.*;
import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.entity.User;
import com.dnk.smart.door.entity.UserBuild;
import com.dnk.smart.door.entity.dict.Gender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Random;

@Service
public class BaseService {

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

	@Transactional
	public void init() throws Exception {
		for (int i = 1; i < 5; i++) {
			User user = new User();
			user.setName("player" + i).setPassword("pass" + i);
			user.setGender(Gender.of(1 + (new Random().nextInt(100)) % 3));
			userDao.save(user);
		}
		for (int i = 0; i < 5; i++) {
			Build build = new Build().setName("build" + i);
			buildDao.save(build);
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 1; j <= 5; j++) {
				for (int k = 1; k <= 5; k++) {
					UserBuild userBuild = new UserBuild();
					Build build = buildDao.findById((long) j);
					User user = userDao.findById((long) k);

					if (user != null && build != null) {
						userBuild.setBuild(build).setUser(user);
						userBuildDao.save(userBuild);
					}
				}
			}
		}
	}
}

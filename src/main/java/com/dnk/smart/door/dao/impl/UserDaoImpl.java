package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.UserDao;
import com.dnk.smart.door.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends CommonDaoImpl<User, Long> implements UserDao {
}

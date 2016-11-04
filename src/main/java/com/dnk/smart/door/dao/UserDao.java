package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.User;
import com.dnk.smart.door.entity.dict.Gender;
import com.dnk.smart.door.kit.jpa.Page;
import com.dnk.smart.door.kit.jpa.Sort;

import java.time.LocalDate;
import java.util.List;

public interface UserDao extends CommonDao<User, Long> {

	User find(String name, String password);

	List<User> findList(String name, Gender gender, LocalDate begin, LocalDate end, Page page, Sort sort);

	long count(String name, Gender gender, LocalDate begin, LocalDate end);

}

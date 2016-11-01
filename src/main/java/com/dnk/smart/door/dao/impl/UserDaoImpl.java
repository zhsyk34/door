package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.UserDao;
import com.dnk.smart.door.entity.User;
import com.dnk.smart.door.entity.dict.Gender;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.PredicateKit;
import com.dnk.smart.door.kit.Sort;
import com.dnk.smart.door.kit.ValidateKit;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class UserDaoImpl extends CommonDaoImpl<User, Long> implements UserDao {
	@Override
	public User find(String name, String password) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);

		query.select(root);
		query.where(builder.equal(root.get("name"), name), builder.equal(root.get("password"), password));
		return manager.createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<User> findList(String name, Gender gender, LocalDate begin, LocalDate end, Page page, Sort sort) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.select(root);
		query.where(conditions(builder, root, name, gender, begin, end));
		super.order(query, builder, root, sort);

		return super.page(manager.createQuery(query), page).getResultList();
	}

	@Override
	public long count(String name, Gender gender, LocalDate begin, LocalDate end) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<User> root = query.from(User.class);

		query.select(builder.count(root));
		query.where(conditions(builder, root, name, gender, begin, end));

		return manager.createQuery(query).getSingleResult();
	}

	private Predicate[] conditions(CriteriaBuilder builder, Root<User> root, String name, Gender gender, LocalDate begin, LocalDate end) {
		PredicateKit kit = PredicateKit.instance();
		if (ValidateKit.notEmpty(name)) {
			kit.append(builder.like(root.get("name"), "%" + name + "%"));
		}
		if (gender != null) {
			kit.append(builder.equal(root.get("gender"), gender));
		}
		Path<LocalDateTime> createTime = root.get("createTime");
		if (begin != null) {
			kit.append(builder.greaterThanOrEqualTo(createTime, LocalDateTime.of(begin, LocalTime.MIN)));
		}
		if (end != null) {
			kit.append(builder.lessThanOrEqualTo(createTime, LocalDateTime.of(end, LocalTime.MIN)));
		}
		return kit.get();
	}

}

package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.UserDao;
import com.dnk.smart.door.entity.User;
import com.dnk.smart.door.entity.dict.Gender;
import com.dnk.smart.door.kit.ValidateKit;
import com.dnk.smart.door.kit.jpa.OrderKit;
import com.dnk.smart.door.kit.jpa.Page;
import com.dnk.smart.door.kit.jpa.PredicateKit;
import com.dnk.smart.door.kit.jpa.Sort;
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
		CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.where(builder.equal(root.get("name"), name), builder.equal(root.get("password"), password));
		return super.find(manager.createQuery(query));
	}

	@Override
	public List<User> findList(String name, Gender gender, LocalDate begin, LocalDate end, Page page, Sort sort) {
		/*CriteriaQuery<User> query = builder.createQuery(User.class);
		Root<User> root = query.from(User.class);
		query.where(conditions(root, name, gender, begin, end));

		if (sort != null) {
			query.orderBy(order(root, sort));
		}

		return super.page(manager.createQuery(query), page).getResultList();*/
		Callback template = new Callback() {
			@Override
			public List<Order> orders(Root root) {
				OrderKit kit = OrderKit.instance();
				kit.append(order(root, sort));
				return kit.getOrders();
			}

			@Override
			public List<Predicate> conditions(Root<User> root) {
				return predicates(root, name, gender, begin, end);
			}
		};
		return template.findList(page, sort);
	}

	@Override
	public long count(String name, Gender gender, LocalDate begin, LocalDate end) {
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<User> root = query.from(User.class);

		query.select(builder.count(root));
		//query.where(predicates(root, name, gender, begin, end));

		return manager.createQuery(query).getSingleResult();
	}

	private List<Predicate> predicates(Root<User> root, String name, Gender gender, LocalDate begin, LocalDate end) {
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
		return kit.getPredicates();
	}

}

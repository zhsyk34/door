package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.ProjectDao;
import com.dnk.smart.door.entity.Project;
import com.dnk.smart.door.kit.jpa.PredicateKit;
import com.dnk.smart.door.kit.ValidateKit;
import com.dnk.smart.door.kit.jpa.Page;
import com.dnk.smart.door.kit.jpa.Sort;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ProjectDaoImpl extends CommonDaoImpl<Project, Long> implements ProjectDao {
	@Override
	public Project find(String name) {
		CriteriaQuery<Project> query = builder.createQuery(Project.class);
		Root<Project> root = query.from(Project.class);
		query.where(builder.equal(root.get("name"), name));
		return super.find(manager.createQuery(query));
	}

	@Override
	public List<Project> findList(String name, LocalDate begin, LocalDate end, Page page, Sort sort) {
		CriteriaQuery<Project> query = builder.createQuery(Project.class);
		Root<Project> root = query.from(Project.class);
		query.where(conditions(root, name, begin, end));

		if (sort != null) {
			query.orderBy(order(root, sort));
		}
		return super.page(manager.createQuery(query), page).getResultList();
	}

	@Override
	public long count(String name, LocalDate begin, LocalDate end) {
		return 0;
	}

	private Predicate[] conditions(Root<Project> root, String name, LocalDate begin, LocalDate end) {
		PredicateKit kit = PredicateKit.instance();
		if (ValidateKit.notEmpty(name)) {
			kit.append(builder.like(root.get("name"), "%" + name + "%"));
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

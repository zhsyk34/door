package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.BuildDao;
import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.kit.jpa.Page;
import com.dnk.smart.door.kit.jpa.PredicateKit;
import com.dnk.smart.door.kit.jpa.Sort;
import com.dnk.smart.door.kit.ValidateKit;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

@Repository
public class BuildDaoImpl extends CommonDaoImpl<Build, Long> implements BuildDao {
	@Override
	public Build find(String name) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Build> query = builder.createQuery(Build.class);
		query.where(builder.equal(query.from(Build.class).get("name"), name));

		return manager.createQuery(query).getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Build> findList(Collection<Long> ids, String name, Page page, Sort sort) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Build> query = builder.createQuery(Build.class);
		Root<Build> root = query.from(Build.class);

		query.where(conditions(builder, root, ids, name));
		//super.order(query, builder, root, sort);

		return super.page(manager.createQuery(query), page).getResultList();
	}

	@Override
	public long count(Collection<Long> ids, String name) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Build> root = query.from(Build.class);

		query.where(conditions(builder, root, ids, name));

		return manager.createQuery(query).getSingleResult();
	}

	private Predicate[] conditions(CriteriaBuilder builder, Root<Build> root, Collection<Long> ids, String name) {
		PredicateKit kit = PredicateKit.instance();
		if (ValidateKit.notEmpty(ids)) {
			kit.append(root.get("id").in(ids));
		}
		if (ValidateKit.notEmpty(name)) {
			kit.append(builder.like(root.get("name"), "%" + name + "%"));
		}
		return kit.get();
	}
}

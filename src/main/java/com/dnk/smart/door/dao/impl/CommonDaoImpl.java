package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.CommonDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class CommonDaoImpl<E, K extends Serializable> implements CommonDao<E, K> {

	@Resource
	private SessionFactory sessionFactory;

	private final Class<E> clazz;

	public CommonDaoImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if (type != null && type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			clazz = (Class<E>) parameterizedType.getActualTypeArguments()[0];
		} else {
			throw new RuntimeException();
		}
	}

	protected final Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public void save(E e) {
		session().persist(e);
	}

	@Override
	public void saves(Collection<E> es) {
		es.forEach(this::save);
	}

	@Override
	public void deleteById(K k) {
		session().delete(this.findById(k));
	}

	@Override
	public void deleteByIds(K[] ks) {
		for (K k : ks) {
			this.deleteById(k);
		}
	}

	@Override
	public void deleteByIds(Collection<K> ks) {
		ks.forEach(this::deleteById);
	}

	@Override
	public void deleteByEntity(E e) {
		session().delete(e);
	}

	@Override
	public void deleteByEntities(Collection<E> es) {
		es.forEach(this::deleteByEntity);
	}

	@Override
	public void update(E e) {
		session().update(e);
	}

	@Override
	public void merge(E e) {
		session().merge(e);
	}

	@Override
	public boolean contains(E e) {
		return session().contains(e);
	}

	@Override
	public E findById(K k) {
		return session().get(clazz, k);
	}

	@Override
	public List<E> findList() {
		Session session = session();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(clazz);
		query.from(clazz);

		return session.createQuery(query).getResultList();
	}

	@Override
	public long count() {
		Session session = session();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<E> root = query.from(clazz);
		query.select(builder.count(root));

		return session.createQuery(query).uniqueResult();
	}
}

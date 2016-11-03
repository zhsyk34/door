package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.CommonDao;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.ReflectKit;
import com.dnk.smart.door.kit.Sort;
import com.dnk.smart.door.kit.ValidateKit;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

class CommonDaoImpl<E, K extends Serializable> implements CommonDao<E, K> {

	private static String keyColumn;
	private final Class<E> entityClass;
	private final Class<K> keyClass;

	@SuppressWarnings("WeakerAccess")
	@PersistenceContext
	protected EntityManager manager;

	@SuppressWarnings("WeakerAccess")
	protected CommonDaoImpl() {
		entityClass = ReflectKit.getGenericType(this.getClass(), 0);
		keyClass = ReflectKit.getGenericType(this.getClass(), 1);
		assert entityClass != null && keyClass != null;
	}

	@Override
	public final EntityManager manager() {
		return manager;
	}

	@PostConstruct
	protected final void init() {
		Metamodel metamodel = manager.getMetamodel();
		EntityType<E> entityType = metamodel.entity(entityClass);
		assert entityType.getIdType().getJavaType() == keyClass;
		keyColumn = entityType.getId(keyClass).getName();
		assert ValidateKit.notEmpty(keyColumn);
	}

	@SuppressWarnings("WeakerAccess")
	protected final K id(E e) {
		EntityManagerFactory factory = manager.getEntityManagerFactory();
		@SuppressWarnings("unchecked")
		K id = (K) factory.getPersistenceUnitUtil().getIdentifier(e);
		return id;
	}

	@Override
	public void save(E e) {
		assert e != null;
		manager.persist(e);
	}

	@Override
	public void saves(Collection<E> es) {
		assert ValidateKit.notEmpty(es);
		es.forEach(this::save);
	}

	@Override
	public int deleteById(K k) {
		assert k != null;
		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaDelete<E> query = builder.createCriteriaDelete(entityClass);
		query.where(builder.equal(query.from(entityClass).get(keyColumn), k));

		return manager.createQuery(query).executeUpdate();
	}

	@Override
	public int deleteByIds(K[] ks) {
		if (ValidateKit.empty(ks)) {
			return 0;
		}
		return this.deleteByIds(Arrays.asList(ks));
	}

	@Override
	public int deleteByIds(Collection<K> ks) {
		if (ValidateKit.empty(ks)) {
			return 0;
		}
		CriteriaDelete<E> query = manager.getCriteriaBuilder().createCriteriaDelete(entityClass);
		query.where(query.from(entityClass).get(keyColumn).in(ks));
		return manager.createQuery(query).executeUpdate();
	}

	@Override
	public int deleteByEntity(E e) {
		assert e != null;
		return this.deleteById(id(e));
	}

	@Override
	public int deleteByEntities(Collection<E> es) {
		assert ValidateKit.notEmpty(es);
		Collection<K> ks = new ArrayList<>();
		es.forEach(e -> ks.add(id(e)));
		return this.deleteByIds(ks);
	}

	@Override
	public long deleteAll() {
		CriteriaDelete<E> query = manager.getCriteriaBuilder().createCriteriaDelete(entityClass);
		query.from(entityClass);
		return manager.createQuery(query).executeUpdate();
	}

	@Override
	public void update(E e) {
		assert e != null;
		K k = id(e);
		//TODO:k<=0
		assert k != null;
		this.merge(e);
	}

	@Override
	public void merge(E e) {
		assert e != null;
		manager.merge(e);
	}

	@Override
	public boolean contains(E e) {
		return e != null && manager.contains(e);
	}

	@Override
	public E findById(K k) {
		assert k != null;
		return manager.find(entityClass, k);
	}

	@Override
	public List<E> findList() {
		CriteriaQuery<E> query = manager.getCriteriaBuilder().createQuery(entityClass);
		query.from(entityClass);
		//query.select(query.from(entityClass));//default,can omit

		return manager.createQuery(query).getResultList();
	}

	@Override
	public long count() {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		query.select(builder.count(query.from(entityClass)));

		return manager.createQuery(query).getSingleResult();
	}

	protected final <T> TypedQuery<T> page(TypedQuery<T> query, Page page) {
		if (page == null) {
			return query;
		}
		return query.setFirstResult(page.start()).setMaxResults(page.getPageSize());
	}

	/**
	 * T is query type,E is entity
	 * sort.column must be in entity otherwise root.get() will throw exception
	 */
	@SuppressWarnings("WeakerAccess")
	protected final <T> CriteriaQuery<T> order(CriteriaQuery<T> query, CriteriaBuilder builder, Root<E> root, Sort sort) {
		if (sort == null) {
			return query;
		}
		Sort.Rule rule = sort.getRule();
		Path<Object> path = root.get(sort.getColumn());
		Order order = (rule == Sort.Rule.DESC) ? builder.desc(path) : builder.asc(path);
		return query.orderBy(order);
	}

}

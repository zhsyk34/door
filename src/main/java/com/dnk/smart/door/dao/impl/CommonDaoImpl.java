package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.CommonDao;
import com.dnk.smart.door.kit.ReflectKit;
import com.dnk.smart.door.kit.ValidateKit;
import com.dnk.smart.door.kit.jpa.ManagerCallback;
import com.dnk.smart.door.kit.jpa.Page;
import com.dnk.smart.door.kit.jpa.Rule;
import com.dnk.smart.door.kit.jpa.Sort;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings("WeakerAccess")
class CommonDaoImpl<E, K extends Serializable> implements CommonDao<E, K> {

	private final Class<E> entityClass;
	private final Class<K> idClass;

	@PersistenceContext
	protected EntityManager manager;

	protected static CriteriaBuilder builder;

	protected static String idColumn;

	protected CommonDaoImpl() {
		entityClass = ReflectKit.getGenericType(this.getClass(), 0);
		idClass = ReflectKit.getGenericType(this.getClass(), 1);
		assert entityClass != null && idClass != null;
	}

	@PostConstruct
	protected final void init() {
		builder = manager.getCriteriaBuilder();

		Metamodel metamodel = manager.getMetamodel();
		EntityType<E> entityType = metamodel.entity(entityClass);
		assert entityType.getIdType().getJavaType() == idClass;
		idColumn = entityType.getId(idClass).getName();
		assert ValidateKit.notEmpty(idColumn);
	}

	@Override
	public final EntityManager manager() {
		return manager;
	}

	@Override
	public final CriteriaBuilder builder() {
		return builder;
	}

	@Override
	public final K id(E e) {
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

		CriteriaDelete<E> query = builder.createCriteriaDelete(entityClass);
		query.where(builder.equal(query.from(entityClass).get(idColumn), k));

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
		CriteriaDelete<E> query = builder.createCriteriaDelete(entityClass);
		query.where(query.from(entityClass).get(idColumn).in(ks));
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
		CriteriaDelete<E> query = builder.createCriteriaDelete(entityClass);
		query.from(entityClass);
		return manager.createQuery(query).executeUpdate();
	}

	@Override
	public void update(E e) {
		assert e != null;
		K k = id(e);
		assert k != null;
		//TODO
		if (k instanceof Long) {
			assert (Long) k > 0;
		}
		if (k instanceof Integer) {
			assert (Integer) k > 0;
		}
		this.merge(e);
	}

	//更新前先查找
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
		CriteriaQuery<E> query = builder().createQuery(entityClass);
		query.from(entityClass);
		//query.select(query.from(entityClass));//default,can omit
		return manager.createQuery(query).getResultList();
	}

	/*@Override
	public List<E> findList(Page page, Sort sort, Collection<Predicate> predicates) {
		return this.findList(page, sort, PredicateKit.get(predicates));
	}

	@Override
	public List<E> findList(Page page, Sort sort, Predicate... predicates) {
		CriteriaQuery<E> query = builder.createQuery(entityClass);
		Root<E> root = query.from(entityClass);

		query.where(predicates);

		if (sort != null) {
			query.orderBy(order(root, sort));
		}

		return this.page(manager.createQuery(query), page).getResultList();
	}*/

	@Override
	public long count() {
		CriteriaBuilder builder = builder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		query.select(builder.count(query.from(entityClass)));
		return manager.createQuery(query).getSingleResult();
	}

	/*------------------tools-----------------------*/
	protected final E find(TypedQuery<E> query) {
		return query.getResultList().stream().findFirst().orElse(null);
	}

	protected final <T> TypedQuery<T> page(TypedQuery<T> query, Page page) {
		if (page == null) {
			return query;
		}
		return query.setFirstResult(page.offset()).setMaxResults(page.getSize());
	}

	protected final Order order(Root<E> root, Sort sort) {
		if (sort == null) {
			return null;//sort = Sort.of(idColumn, null);
		}
		Rule rule = sort.getRule();
		Path<?> path = root.get(sort.getColumn());
		return (rule == Rule.DESC) ? builder.desc(path) : builder.asc(path);
	}

	protected <T> T execute(ManagerCallback<T> callback) {
		return callback.doExecute(manager);
	}

	/**
	 * template callback for find single entity list
	 */

	public abstract class Callback {
		public final List<E> findList(Page page, Sort sort) {
			return this.findList(page, Collections.singletonList(sort));
		}

		public final List<E> findList(Page page, List<Sort> sorts) {
			CriteriaQuery<E> query = builder.createQuery(entityClass);
			Root<E> root = query.from(entityClass);
			Collection<Predicate> predicates = conditions(root);
			query.where(predicates.toArray(new Predicate[predicates.size()]));

			if (ValidateKit.notEmpty(sorts)) {
				List<Order> orders = orders(root);
				if (ValidateKit.notEmpty(orders)) {
					query.orderBy(orders.toArray(new Order[orders.size()]));
				}
			}

			TypedQuery<E> typedQuery = manager.createQuery(query);
			if (page != null) {
				typedQuery.setFirstResult(page.offset()).setMaxResults(page.getSize());
			}

			return typedQuery.getResultList();
		}

		public abstract List<Order> orders(Root<E> root);

		public abstract List<Predicate> conditions(Root<E> root);
	}

}

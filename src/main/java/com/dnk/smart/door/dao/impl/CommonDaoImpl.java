package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.CommonDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class CommonDaoImpl<E, K extends Serializable> implements CommonDao<E, K> {

	@Resource
	private SessionFactory sessionFactory;

	private final Class<E> clazz;
	private final Class<E> key;
	private static String id;

	public CommonDaoImpl() {
		Type type = this.getClass().getGenericSuperclass();
		if (type != null && type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			clazz = (Class<E>) parameterizedType.getActualTypeArguments()[0];
			key = (Class<E>) parameterizedType.getActualTypeArguments()[1];
		} else {
			throw new RuntimeException();
		}
	}

	protected final Session session() {
		return sessionFactory.getCurrentSession();
	}

	//TODO:init twice?
	@PostConstruct
	protected final void id() {
//		System.out.println("init:" + clazz);
		/*sessionFactory.getAllClassMetadata();
		ClassMetadata metadata = session().getSessionFactory().getClassMetadata(clazz);
		System.out.println(metadata.getIdentifierPropertyName());
		String pk = metadata.getIdentifierPropertyName();
		System.out.println(pk);*/

		Metamodel metamodel = sessionFactory.getMetamodel();
		for (EntityType<?> entityType : metamodel.getEntities()) {
			Class<?> entityClass = entityType.getJavaType();//entity class
			Class<?> idClass = entityType.getIdType().getJavaType();

			if (entityClass == clazz && idClass == key) {
				id = entityType.getId(key).getName();
//				System.out.println(id);
				//id = entityType.getDeclaredId(idClass).getName();
				return;
			}
		}
		throw new RuntimeException("can't find the key in " + clazz.getSimpleName());
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
	public int deleteById(K k) {
		/*E e = this.findById(k);
		if (e != null) {
			session().delete(e);
		}*/

		Session session = session();

		CriteriaBuilder builder = session.getCriteriaBuilder();

		CriteriaDelete<E> query = builder.createCriteriaDelete(clazz);
		query.where(builder.equal(query.from(clazz).get(id), k));

		return session.createQuery(query).executeUpdate();
	}

	@Override
	public int deleteByIds(K[] ks) {
		/*for (K k : ks) {
			this.deleteById(k);
		}*/
		Session session = session();
		CriteriaDelete<E> query = session.getCriteriaBuilder().createCriteriaDelete(clazz);
		query.where(query.from(clazz).get(id).in(ks));
		return session.createQuery(query).executeUpdate();
	}

	@Override
	public int deleteByIds(Collection<K> ks) {
		Session session = session();
		CriteriaDelete<E> query = session.getCriteriaBuilder().createCriteriaDelete(clazz);
		query.where(query.from(clazz).get(id).in(ks));
		return session.createQuery(query).executeUpdate();
	}

	@Override
	public void deleteByEntity(E e) {
		session().remove(e);
	}

	@Override
	public void deleteByEntities(Collection<E> es) {
//		session().remove(es);
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

		CriteriaQuery<E> query = session.getCriteriaBuilder().createQuery(clazz);
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

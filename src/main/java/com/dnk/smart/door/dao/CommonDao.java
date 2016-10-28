package com.dnk.smart.door.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface CommonDao<E, K extends Serializable> {

	void save(E e);

	void saves(Collection<E> es);

	int deleteById(K k);

	int deleteByIds(K[] ks);

	int deleteByIds(Collection<K> ks);

	void deleteByEntity(E e);

	void deleteByEntities(Collection<E> es);

	void update(E e);

	void merge(E e);

	boolean contains(E e);

	E findById(K k);

	List<E> findList();

	long count();

}

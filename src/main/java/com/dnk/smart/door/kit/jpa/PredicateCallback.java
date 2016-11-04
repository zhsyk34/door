package com.dnk.smart.door.kit.jpa;

import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class PredicateCallback<E> implements ManagerCallback<E> {

	public abstract List<Order> orders(Root<E> root);

	public abstract List<Predicate> conditions(Root<E> root);
}

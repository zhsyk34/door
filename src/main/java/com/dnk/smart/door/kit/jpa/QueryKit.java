package com.dnk.smart.door.kit.jpa;

public  abstract class QueryKit<E> {

	/*private static EntityManager manager;

	private static CriteriaBuilder builder;

	private Class<E> clazz;

	public final List<E> findList(Page page, List<Sort> sorts, Object... params) {
		CriteriaQuery<E> query = builder.createQuery(clazz);
		Root<E> root = query.from(clazz);

		Collection<Predicate> predicates = conditions(root, params);
		query.where(predicates.toArray(new Predicate[predicates.size()]));

		List<Order> orders = orders(root, sorts);

		if (orders != null && orders.size() > 0) {
			query.orderBy(orders.toArray(new Order[orders.size()]));
		}

		TypedQuery<E> typedQuery = manager.createQuery(query);
		if (page != null) {
			typedQuery.setFirstResult(page.offset()).setMaxResults(page.getSize());
		}

		return typedQuery.getResultList();
	}

	public abstract List<Order> orders(Root root, List<Sort> sorts);

	public abstract List<Predicate> conditions(Root root, Object... params);*/
}

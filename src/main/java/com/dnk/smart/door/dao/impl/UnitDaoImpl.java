package com.dnk.smart.door.dao.impl;

import com.dnk.smart.door.dao.UnitDao;
import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.entity.Unit;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.PredicateKit;
import com.dnk.smart.door.kit.Sort;
import com.dnk.smart.door.kit.ValidateKit;
import com.dnk.smart.door.vo.UnitVO;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class UnitDaoImpl extends CommonDaoImpl<Unit, Long> implements UnitDao {

	@Override
	public List<Unit> findList(Long buildId, String name) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Unit> query = builder.createQuery(Unit.class);
		Root<Unit> unitRoot = query.from(Unit.class);
		Join<Unit, Build> buildJoin = unitRoot.join("build");
		query.select(unitRoot);

		PredicateKit kit = PredicateKit.instance();
		if (ValidateKit.valid(buildId)) {
			kit.append(builder.equal(buildJoin.get("id"), buildId));
		}
		if (ValidateKit.notEmpty(name)) {
			kit.append(builder.equal(unitRoot.get("name"), name));
		}
		query.where(kit.get());

		return manager.createQuery(query).getResultList();
	}

	@Override
	public List<Unit> findList(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName, Page page, Sort sort) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Unit> query = builder.createQuery(Unit.class);
		Root<Unit> unitRoot = query.from(Unit.class);
		//TODO ==> Fetch<Unit, Build> buildFetch = unit.fetch("build");

		Join<Unit, Build> buildJoin = unitRoot.join("build");
		query.select(unitRoot);

		query.where(conditions(builder, buildJoin, unitRoot, buildIds, buildName, unitIds, unitName));
		super.order(query, builder, unitRoot, sort);

		return super.page(manager.createQuery(query), page).getResultList();
	}

	@Override
	public long count(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<Unit> unitRoot = query.from(Unit.class);

		Join<Unit, Build> buildJoin = unitRoot.join("build");
		query.select(builder.count(unitRoot));

		query.where(conditions(builder, buildJoin, unitRoot, buildIds, buildName, unitIds, unitName));

		return manager.createQuery(query).getSingleResult();
	}

	//TODO:transform
	@Override
	public List<Tuple> findTuple(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName, Page page, Sort sort) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = builder.createTupleQuery();
		Root<Unit> unitRoot = query.from(Unit.class);

		Join<Unit, Build> buildJoin = unitRoot.join("build");

		query.where(conditions(builder, buildJoin, unitRoot, buildIds, buildName, unitIds, unitName));
//		Set<Attribute<? super Unit, ?>> attributes = unitRoot.getModel().getAttributes();
//		attributes.forEach(attribute -> {
//			System.out.println(attribute.getName());
//		});
		//multi
		List<Selection<?>> selections = new ArrayList<>();
		selections.add(unitRoot.get("id").alias("id"));
		selections.add(unitRoot.get("name"));
		selections.add(buildJoin.get("id").alias("buildId"));
		selections.add(buildJoin.get("name").alias("buildName"));
		query.multiselect(selections);
		//construct
		//CompoundSelection<Map> construct = builder.construct(Map.class, unitRoot.get("id"));
		//query.select(construct);

		super.order(query, builder, unitRoot, sort);
		return super.page(manager.createQuery(query), page).getResultList();
//		TypedQuery<Map> typedQuery =
//		return typedQuery.getResultList();
//		typedQuery.setHint(QueryHints.READ_ONLY, Transformers.ALIAS_TO_ENTITY_MAP);
//		return typedQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
//		List<Tuple> list = manager().createQuery(query).getResultList();
//		list.forEach(tuple -> {
////			System.out.println(tuple);
//			List<TupleElement<?>> elements = tuple.getElements();
//			System.out.println(elements.size());
//			/*elements.forEach(tupleElement -> {
//
//				String key = tupleElement.getAlias();
//				System.out.println(key);
//			});*/
//		});

//		return null;
	}

	@Override
	public List<UnitVO> findVOList(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName, Page page, Sort sort) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<UnitVO> query = builder.createQuery(UnitVO.class);
		Root<Unit> unitRoot = query.from(Unit.class);

		Join<Unit, Build> buildJoin = unitRoot.join("build");

		query.where(conditions(builder, buildJoin, unitRoot, buildIds, buildName, unitIds, unitName));
		//multi
		List<Selection<?>> selections = new ArrayList<>();
		//根据主键发出多余查询,需要有构造方法?
		selections.add(unitRoot);
		//直接指定
		/*selections.add(unitRoot.get("id"));
		selections.add(unitRoot.get("name"));
		selections.add(unitRoot.get("createTime"));
		selections.add(unitRoot.get("updateTime"));*/
		selections.add(buildJoin.get("id").alias("buildId"));
		selections.add(buildJoin.get("name").alias("buildName"));
		query.multiselect(selections);

		super.order(query, builder, unitRoot, sort);
		return super.page(manager.createQuery(query), page).getResultList();
	}

	@Override
	public List<UnitVO> findVOList2(Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName, Page page, Sort sort) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<UnitVO> query = builder.createQuery(UnitVO.class);
		Root<Unit> unitRoot = query.from(Unit.class);

		Join<Unit, Build> buildJoin = unitRoot.join("build");

		query.where(conditions(builder, buildJoin, unitRoot, buildIds, buildName, unitIds, unitName));

		//test function
		//Expression<String> function = builder.function("CURRENT_USER", String.class);

		//base select,not multi
		query.select(
				builder.construct(
						UnitVO.class,
						unitRoot.get("id").alias("id"),
						unitRoot.get("name").alias("name"),
						unitRoot.get("createTime").alias("createTime"),
						unitRoot.get("updateTime").alias("updateTime"),
						buildJoin.get("id").alias("buildId"),
						buildJoin.get("name").alias("buildName")
				)
		);
		//example for tuple
//		TupleElement<Long> element = unitRoot.get("id").alias("id");
//		query.select(builder.tuple(element));

		super.order(query, builder, unitRoot, sort);

		//test append/modify original order
//		Order order2 = builder.desc(buildJoin.get("id"));
//		List<Order> orderList = query.getOrderList();
//		List<Order> orderList2 = new ArrayList<>(orderList);
//		orderList2.add(order2);
//		query.orderBy(orderList2);
		return super.page(manager.createQuery(query), page).getResultList();
	}

	private Predicate[] conditions(CriteriaBuilder builder, Join<Unit, Build> buildJoin, Root<Unit> unitRoot, Collection<Long> buildIds, String buildName, Collection<Long> unitIds, String unitName) {
		PredicateKit kit = PredicateKit.instance();
		if (ValidateKit.notEmpty(buildIds)) {
			kit.append(buildJoin.get("id").in(buildIds));
		}
		if (ValidateKit.notEmpty(buildName)) {
			kit.append(builder.like(buildJoin.get("name"), "%" + buildName + "%"));
		}
		if (ValidateKit.notEmpty(unitIds)) {
			kit.append(unitRoot.get("id").in(unitIds));
		}
		if (ValidateKit.notEmpty(unitName)) {
			kit.append(builder.like(unitRoot.get("name"), "%" + unitName + "%"));
		}
		return kit.get();
	}
}

package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.Build;
import com.dnk.smart.door.entity.Unit;
import com.dnk.smart.door.entity.User;
import com.dnk.smart.door.kit.jpa.Page;
import com.dnk.smart.door.kit.jpa.Rule;
import com.dnk.smart.door.kit.jpa.Sort;
import com.dnk.smart.door.vo.UnitVO;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UnitDaoTest extends CommonDaoTest {
	@Test
	public void findList() throws Exception {
		List<Unit> list = unitDao.findList();
		//list.forEach(super::print);
		System.out.println(list.size());
	}

	@Test
	public void findList2() throws Exception {
		List<Unit> list = unitDao.findList(Arrays.asList(1L, 3L), "bu", null, "u", Page.of(1, 3), Sort.of("name", Rule.DESC));
		list.forEach(super::print);
	}

	@Test
	public void findList3() throws Exception {
		EntityManager manager = userDao.manager();

		@SuppressWarnings("JpaQlInspection")
		TypedQuery<Map> query = manager.createQuery("SELECT b FROM Build b WHERE 1=1", Map.class);
		List<Map> list = query.getResultList();
		System.out.println(list.size());
	}

	@Test
	public void count() throws Exception {

	}

	@Test
	public void findMap() throws Exception {
		List<Tuple> list = unitDao.findTuple(Arrays.asList(1L, 3L), "bu", null, "u", Page.of(1, 3), Sort.of("name", Rule.DESC));

		//TODO:alias would be null.error。Warning!!!
		list.forEach(tuple -> {
			List<TupleElement<?>> elements = tuple.getElements();
			elements.forEach(tupleElement -> {
				String alias = tupleElement.getAlias();
				Object value = tuple.get(tupleElement);
				System.out.println(alias + " " + value);
			});
			System.out.println("-----------------");
		});
	}

	@Test
	public void findVO() throws Exception {
		List<UnitVO> list = unitDao.findVOList(Arrays.asList(1L, 3L), "bu", null, "u", Page.of(1, 3), Sort.of("name", Rule.DESC));
		super.print(list);
	}

	@Test
	public void findVO2() throws Exception {
		List<UnitVO> list = unitDao.findVOList2(Arrays.asList(2L, 3L), "bu", null, "u", Page.of(1, 5), Sort.of("id", Rule.DESC));
		super.print(list);
	}

	@Test
	public void findName() throws Exception {
		List<Unit> list = unitDao.findList(2L, "unit57");
		list.forEach(super::print);
	}

	@Test
	public void api2() throws Exception {
		EntityManager manager = unitDao.manager();
		CriteriaBuilder b1 = manager.getCriteriaBuilder();
		CriteriaBuilder b2 = manager.getCriteriaBuilder();

		System.out.println(b1.hashCode());
		System.out.println(b2.hashCode());
		System.out.println(b1 == b2);
	}

	@Test
	public void api() throws Exception {
		EntityManager manager = unitDao.manager();
		User user = manager.find(User.class, 1L);
		System.out.println(user.getName());
		user.setName("yes");
		manager.getTransaction().commit();
		manager.close();
//		Metamodel metamodel = manager.getMetamodel();
//		metamodel.entity(User.class);
//		Set<EntityType<?>> entities = metamodel.getEntities();
//		entities.forEach(entityType -> {
////			System.out.println(entityType.getName());
////			System.out.println(entityType.getBindableType());
////			System.out.println(entityType.getBindableJavaType());
//			System.out.println(entityType.getPersistenceType());
//			System.out.println(entityType.getJavaType());
//
//			System.out.println("-------------");
//			System.out.println(entityType.getIdType().getJavaType());
//		});

		/*PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
		System.out.println(persistenceUtil);
		System.out.println(Persistence.createEntityManagerFactory("hibernate"));*/

	}

	@Test
	public void tree() throws Exception {
		SingularAttribute sa;
		PluralAttribute pa;

		Expression expression = null;

		Predicate p;

		EntityManager manager = unitDao.manager();

		CriteriaBuilder builder = manager.getCriteriaBuilder();

		CriteriaQuery<Object> query = builder.createQuery();
		Selection selection = null;
		query.select(selection);

		EntityType entityType = null;
		Root<Build> from = query.from(Build.class);
		SetJoin<Build, Unit> setJoin = from.joinSet("unit");
		Path path = from.get("");
		Bindable model = path.getModel();

		CriteriaBuilder.In in1 = builder.in(path).value(1).value(2);
		Predicate in11 = in1;
		Predicate in2 = path.in(1, 2);

		ParameterExpression<Integer> id = builder.parameter(Integer.class);
		Predicate condition = builder.gt(from.get("id"), id);
		query.where(condition);
		manager.createQuery(query).setParameter(id, 10);

	}
}
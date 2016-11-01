package com.dnk.smart.door.dao;

import com.dnk.smart.door.entity.Unit;
import com.dnk.smart.door.kit.Page;
import com.dnk.smart.door.kit.Sort;
import com.dnk.smart.door.vo.UnitVO;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
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
		List<Unit> list = unitDao.findList(Arrays.asList(1L, 3L), "bu", null, "u", Page.of(1, 3), Sort.of("name", Sort.Rule.DESC));
		list.forEach(super::print);
	}

	@Test
	public void findList3() throws Exception {
		EntityManager manager = userDao.manager();

		TypedQuery<Map> query = manager.createQuery("SELECT b FROM Build b WHERE 1=1", Map.class);
		List<Map> list = query.getResultList();
		System.out.println(list.size());
	}

	@Test
	public void count() throws Exception {

	}

	@Test
	public void findMap() throws Exception {
		List<Tuple> list = unitDao.findTuple(Arrays.asList(1L, 3L), "bu", null, "u", Page.of(1, 3), Sort.of("name", Sort.Rule.DESC));

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
		List<UnitVO> list = unitDao.findVOList(Arrays.asList(1L, 3L), "bu", null, "u", Page.of(1, 3), Sort.of("name", Sort.Rule.DESC));
		super.print(list);
	}

	@Test
	public void findName() throws Exception {
		List<Unit> list = unitDao.findList(2L, "unit57");
		list.forEach(super::print);
	}
}